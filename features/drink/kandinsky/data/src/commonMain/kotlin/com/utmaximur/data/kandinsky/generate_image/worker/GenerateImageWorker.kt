package com.utmaximur.data.kandinsky.generate_image.worker

import com.utmaximur.core.logging.Logger
import com.utmaximur.data.kandinsky.generate_image.FileStorage
import com.utmaximur.data.kandinsky.generate_image.GenerateResultDataSource
import com.utmaximur.data.kandinsky.generate_image.decodeBase64ToByteArray
import com.utmaximur.data.kandinsky.generate_image.worker.exeption.GenerationFailedException
import com.utmaximur.data.kandinsky.generate_image.worker.exeption.GenerationResultNotFoundException
import com.utmaximur.data.kandinsky.generate_image.worker.exeption.ServiceUnavailable
import com.utmaximur.data.kandinsky.generate_image.worker.exeption.TimeoutException
import com.utmaximur.data.kandinsky.network.ApiConstants
import com.utmaximur.data.kandinsky.network.FusionBrainApi
import com.utmaximur.data.kandinsky.network.models.GenerateStatus
import com.utmaximur.data.kandinsky.network.models.GenerationRequest
import com.utmaximur.workmanager.SimpleWorkManager
import com.utmaximur.workmanager.dsl.CommonConstraints
import com.utmaximur.workmanager.models.OneTimeOperation
import com.utmaximur.workmanager.work.Worker
import com.utmaximur.workmanager.work.WorkerData
import com.utmaximur.workmanager.work.WorkerResult
import com.utmaximur.workmanager.work.runCatchingWorker
import com.utmaximur.workmanager.work.workData
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.headersOf
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Factory
import org.koin.core.component.KoinComponent

@Factory
internal class GenerateImageWorker(
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val fusionBrainApi: FusionBrainApi,
    private val generateResultDataSource: GenerateResultDataSource,
    private val fileStorage: FileStorage,
    private val logger: Logger
) : Worker, KoinComponent {

    override suspend fun doWork(inputData: WorkerData): WorkerResult = withContext(ioDispatcher) {
        logger.i { "GenerateImageWorker started" }
        runCatchingWorker {
            val prompt = inputData.getDataValue<String>(PROMPT_KEY)
            val styleName = inputData.getDataValue<String>(STYLE_NAME_KEY)
            val modelId = fetchLatestModelVersionId()
            val params = GenerationRequest.createDefaultRequest(styleName, prompt)
            val uuid = submitGenerationRequest(modelId, params)
            pollGenerationStatusWithBackoff(uuid)
        }
    }

    /**
     * Получает идентификатор (ID) последней доступной версии модели из сервиса FusionBrain.
     *
     * @return [Int] — ID модели с наибольшей версией.
     *
     * ### Исключения:
     * - `Exception` с сообщением `"Service unavailable"`, если:
     *   - API не вернуло ни одной модели
     *   - Произошла ошибка сети или сервера (обрабатывается внутри `fusionBrainApi.getModels()`)
     */
    private suspend fun fetchLatestModelVersionId(): Int {
        val models = fusionBrainApi.getModels()
        models.ifEmpty { throw ServiceUnavailable() }
        logger.i { "GenerateImageWorker get latest model id" }
        return models.maxBy { it.version }.id
    }

    /**
     * Отправляет асинхронный запрос на генерацию изображения в сервис FusionBrain.
     *
     * ### Описание:
     * Метод выполняет следующие действия:
     * 1. Формирует тело запроса в формате `multipart/form-data`
     * 2. Отправляет запрос через API сервиса генерации
     * 3. Возвращает UUID задачи для дальнейшего отслеживания статуса
     *
     * ### Параметры:
     * @param modelId Идентификатор модели ИИ (должен быть получен через `getLatestModelId()`)
     * @param params Объект с параметрами генерации (промпт, стиль, размеры и т.д.)
     *
     * ### Возвращаемое значение:
     * @return [String] - UUID задачи, необходимый для проверки статуса генерации
     */
    private suspend fun submitGenerationRequest(modelId: Int, params: GenerationRequest): String {
        val formData = createRequestFormData(modelId, params)
        return fusionBrainApi.postGenerateImageRequest(body = formData).uuid
    }

    /**
     * Создает контент типа `multipart/form-data` для запроса генерации изображения.
     *
     * ### Описание:
     * Формирует тело HTTP-запроса, содержащее два поля:
     * 1. **model_id** - идентификатор модели генерации (передается как текст)
     * 2. **params** - параметры генерации в формате JSON
     *
     * ### Параметры:
     * @param modelId Числовой идентификатор модели AI (пример: 1)
     * @param params Объект с параметрами запроса генерации, реализующий сериализацию
     *
     * ### Возвращаемое значение:
     * @return [MultiPartFormDataContent] - готовый контент для отправки в теле запроса
     *
     * ### Особенности реализации:
     * - Для поля [modelId] устанавливается Content-Type: `text/plain`
     * - Для поля [params] используется Content-Type: `application/json`
     */
    private fun createRequestFormData(modelId: Int, params: GenerationRequest) =
        MultiPartFormDataContent(formData {
            append(
                ApiConstants.MODEL_ID, modelId, headersOf(
                    HttpHeaders.ContentType, ContentType.Text.Plain.toString()
                )
            )
            append(
                ApiConstants.PARAMS, Json.encodeToString(params), headersOf(
                    HttpHeaders.ContentType, ContentType.Application.Json.toString()
                )
            )
        })

    /**
     * Периодически опрашивает статус генерации изображения с экспоненциальной задержкой между попытками.
     *
     * ### Алгоритм работы:
     * 1. Отправляет запрос на проверку статуса генерации по UUID
     * 2. Обрабатывает возможные состояния:
     *    - **Успешная генерация**: Возвращает [WorkerResult.success]
     *    - **Ошибка генерации**: Возвращает [WorkerResult.failure] с [GenerationFailedException]
     *    - **В процессе**: Увеличивает задержку и повторяет проверку
     * 3. При превышении лимита попыток возвращает [TimeoutException]
     *
     * ### Параметры:
     * @param uuid Уникальный идентификатор задачи генерации
     * @param maxAttempts Максимальное количество попыток (по умолчанию 15 ~30 сек)
     * @param initialDelay Начальная задержка между попытками в миллисекундах (по умолчанию 2000 мс)
     * @param maxDelay Максимальная задержка между попытками в миллисекундах (по умолчанию 10000 мс)
     *
     * ### Возвращаемое значение:
     * [WorkerResult] с одним из исходов:
     * - Успех с результатом генерации
     * - Ошибка с [GenerationFailedException] или [TimeoutException]
     *
     * ### Особенности реализации:
     * - Использует стратегию экспоненциальной задержки (backoff)
     * - Автоматически отменяется при отмене родительской корутины
     */
    private suspend fun CoroutineScope.pollGenerationStatusWithBackoff(
        uuid: String,
        maxAttempts: Int = 15, // ~30 секунд максимум
        initialDelay: Long = 2_000L,
        maxDelay: Long = 10_000L
    ): WorkerResult {
        var currentDelay = initialDelay
        var attempts = 0

        while (isActive && attempts++ < maxAttempts) {
            val periodicResult = fusionBrainApi.getRequestStatusOrImage(id = uuid)
            generateResultDataSource.sendData(periodicResult)
            when (periodicResult.status) {
                GenerateStatus.DONE -> {
                    val base64String = periodicResult.images.firstOrNull() ?: return WorkerResult.failure(
                        throwable = GenerationResultNotFoundException()
                    )
                    val data = base64String.decodeBase64ToByteArray()
                    val path = fileStorage.saveFileToCache(periodicResult.uuid, data)
                    generateResultDataSource.sendData(
                        periodicResult.copy(images = listOf(path.orEmpty()))
                    )
                    logger.i { "GenerateImageWorker generation result is successful" }
                    return WorkerResult.success()
                }

                GenerateStatus.FAIL -> {
                    logger.e { "GenerateImageWorker generation failed" }
                    return WorkerResult.failure(throwable = GenerationFailedException())
                }

                else -> {
                    logger.i { "GenerateImageWorker generation processing" }
                    delay(currentDelay)
                    currentDelay = (currentDelay * 1.5).toLong().coerceAtMost(maxDelay)
                }
            }
        }

        return WorkerResult.failure(throwable = TimeoutException())
    }

    companion object {
        private const val PROMPT_KEY = "PROMPT_KEY"
        private const val STYLE_NAME_KEY = "STYLE_NAME_KEY"

        suspend fun sendRequestGeneration(prompt: String, styleName: String) =
            SimpleWorkManager.enqueue(
                request = OneTimeOperation.Builder()
                    .addIdentifier<GenerateImageWorker>()
                    .constraints(CommonConstraints.NETWORK)
                    .data(
                        workData {
                            put(PROMPT_KEY, prompt)
                            put(STYLE_NAME_KEY, styleName)
                        }
                    )
                    .build()
            )

        suspend fun cancelAllWork() = SimpleWorkManager.cancelAllWork()
    }
}