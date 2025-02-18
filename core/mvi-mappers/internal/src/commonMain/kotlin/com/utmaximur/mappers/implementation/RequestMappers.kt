package com.utmaximur.mappers.implementation

import com.utmaximur.core.mvi_mapper.ErrorHandler
import com.utmaximur.core.mvi_mapper.RequestDataMapper
import com.utmaximur.core.mvi_mapper.RequestErrorHandler
import com.utmaximur.core.mvi_mapper.RequestLoadingMapper
import com.utmaximur.core.mvi_mapper.SimpleLoading
import com.utmaximur.remote.errors.NetworkResponseError
import com.utmaximur.remote.errors.isNetworkConnectionError

/**
 * Singleton-фабрика мапперов запросов.
 *
 * Используется для хранения переиспользуемых мапперов внутри проекта.
 *
 */
object RequestMappers {

    /**
     * Мапперы данных запроса.
     * */
    val data = DataMappers

    /**
     * Мапперы состояния загрузки запроса.
     * */
    val loading = LoadingMappers

    /**
     * Обработчики ошибок запроса.
     * */
    val error = ErrorHandlers

    /**
     * Мапперы данных запроса.
     * */
    object DataMappers {

        /**
         * ## Маппер данных одиночного запроса.
         *
         * Маппер каждый новый запрос "очищает" данные в стейте.
         *
         * Следует использовать этот маппер в тех случаях, когда нам необходимо:
         *
         * * Сделать одиночный запрос и узнать результат его выполнения,
         * при этом нам не важно предыдущее его состояние.
         *
         * @return только что полученные данные из запроса.
         * */
        fun <T> single(): RequestDataMapper<T, T, T> = { request, _ ->
            when {
                request.isError -> null
                else -> request.getDataOrNull()
            }
        }

        /**
         * ## Маппер данных стандартного запроса.
         *
         * Следует использовать этот маппер в тех случаях, когда нам необходимо:
         *
         * * При первом запросе получить данные и сохранить их в стейт;
         * * При последующих запросах, если они удачные - обновить данные в стейте.
         *
         * @return только что полученные данные из запроса, либо данные из стейта.
         * */
        fun <T> default(): RequestDataMapper<T, T, T> = { request, data ->
            when {
                request.isError && request.getError() is NetworkResponseError.Unauthorized -> null
                else -> request.getDataOrNull() ?: data
            }
        }

        /**
         * ## Маппер данных списка
         *
         * Следует использовать этот маппер в тех случаях, когда нам необходимо:
         *
         * При получении данных сохранять их только в том случае если они не пустые.
         * При первом запросе получить данные и сохранить их в стейт;
         * При последующих запросах если не получили данные вернуть старые данные, если получили пустое значение
         * то удалить сохранные данные
         *
         * @return только что полученные данные из запроса (если придет пустой список то null), либо данные из стейта.
         */
        fun <T> emptyListToNull(): RequestDataMapper<List<T>, List<T>, List<T>> = { request, data ->
            when {
                request.isError && request.getError() is NetworkResponseError.Unauthorized -> null
                else -> {
                    if (request.getDataOrNull() == null) {
                        data
                    } else {
                        request.getData().takeIf { it.isNotEmpty() }
                    }
                }
            }
        }

        /**
         * ## Маппер данных списка ключ - значение
         *
         * @return только что полученные данные из запроса (если придет пустой список то null), либо данные из стейта.
         */

        fun <T, C> emptyMapToNull(): RequestDataMapper<Map<T, C>, Map<T, C>, Map<T, C>> =
            { request, data ->
                when {
                    request.isError && request.getError() is NetworkResponseError.Unauthorized -> null
                    else -> {
                        if (request.getDataOrNull() == null) {
                            data
                        } else {
                            request.getData().takeIf { it.isNotEmpty() }
                        }
                    }
                }
            }
    }

    /**
     * Мапперы состояния загрузки запроса.
     * */
    object LoadingMappers {

        /**
         * ## Маппер состояния загрузки простого запроса.
         *
         * Следует использовать этот маппер в тех случаях, когда нам необходимо:
         *
         * * Получить состояние загрузки запроса в простом формате (загружается/не загружается).
         * * На экране осуществляется несколько запросов, на основании состояния загрузки которых
         * вычисляется все состояние экрана.
         *
         * @return актуальное, простое, состояние загрузки.
         * */
        fun <T1, T2> simple(): RequestLoadingMapper<T1, T2> =
            { request, _ -> SimpleLoading(request.isLoading) }

        /**
         * ## Маппер состояния загрузки стандартного запроса.
         *
         * Следует использовать этот маппер в тех случаях, когда нам необходимо:
         *
         * * На основании одного запроса - вычислить состояние всего экрана.
         *
         * @param isSwr является ли загрузка посредством PTR
         * @param showEmptyState нужно ли показывать [LoadStateType.Empty] при отсутствии данных
         *
         * @return актуальное состояние загрузки.
         */
        fun <T1, T2> default(
            isSwr: Boolean = false,
            showEmptyState: Boolean = true
        ): RequestLoadingMapper<T1, T2> = { request, data ->
            val hasData = when (data) {
                is List<*> -> data.isNotEmpty()
                null -> false
                else -> true
            }
            when {
                request.isLoading && hasData && isSwr -> LoadStateType.SwipeRefreshLoading
                request.isLoading && hasData -> LoadStateType.TransparentLoading
                request.isError && hasData -> LoadStateType.None
                request.isLoading && !hasData -> LoadStateType.Main
                request.isError && !hasData -> {
                    if (request.getError().isNetworkConnectionError()) {
                        LoadStateType.NoInternet
                    } else {
                        LoadStateType.Error
                    }
                }

                request.isSuccess && !hasData && showEmptyState -> LoadStateType.Empty
                else -> LoadStateType.None
            }
        }

        /**
         * ## Маппер состояния загрузки запроса.
         *
         * Подробное поведение:
         * * Запрос загружается с `isSwr`-флагом -- будет применен [LoadStateType.SwipeRefreshLoading];
         * * Запрос загружается -- будет применен [LoadStateType.Main];
         * * иначе [LoadStateType.None].
         *
         * @return актуальное состояние загрузки.
         */
        fun <T1, T2> mainOrNone(isSwr: Boolean = false): RequestLoadingMapper<T1, T2> =
            { request, _ ->
                when {
                    request.isLoading && isSwr -> LoadStateType.SwipeRefreshLoading
                    request.isLoading -> LoadStateType.Main
                    else -> LoadStateType.None
                }
            }

        /**
         * ## Маппер состояния загрузки запроса.
         *
         * Подробное поведение:
         * * Запрос загружается с `isSwr`-флагом -- будет применен [LoadStateType.SwipeRefreshLoading];
         * * Запрос загружается -- будет применен [LoadStateType.TransparentLoading];
         * * иначе [LoadStateType.None].
         *
         * @return актуальное состояние загрузки.
         */
        fun <T1, T2> transparentOrNone(isSwr: Boolean = false): RequestLoadingMapper<T1, T2> =
            { request, _ ->
                when {
                    request.isLoading && isSwr -> LoadStateType.SwipeRefreshLoading
                    request.isLoading -> LoadStateType.TransparentLoading
                    else -> LoadStateType.None
                }
            }
    }

    /**
     * Обработчики ошибок запроса.
     * */
    object ErrorHandlers {

        /**
         * ## Обработчик ошибок. Форсированный.
         *
         * Каждую из ошибок отправляет в [ErrorHandler] и завершает обработку ошибок для запроса.
         *
         * Следует использовать этот маппер в тех случая, когда нам необходимо:
         *
         * * Обработать все возникающие ошибки в [ErrorHandler]'е (например,
         * для одиночного запроса, у которого нету UI репрезентации).
         *
         * @return Была ли обработана ошибка? (Всегда -- true)
         * */
        fun <T> forced(errorHandler: ErrorHandler): RequestErrorHandler<T> = { error, _, _ ->
            error?.also(errorHandler::handleError)
            true
        }

        /**
         * ## Обработчик ошибок. Обработка в зависимости от текущего [LoadStateType].
         *
         * Обрабатывает ошибки только в том случае, когда экран не находится в состоянии ошибки.
         *
         * **Ошибки [NonAuthorizedException] и [NoInternetException] -- обрабатываются всегда!**
         *
         * @return Была ли обработана ошибка? (Всегда -- true)
         * */
        fun <T> loadingBased(errorHandler: ErrorHandler): RequestErrorHandler<T> =
            { error, _, loading ->
                val isSecurityProblem = error?.isNetworkConnectionError() == true
                val isNonAuthorized = error is NetworkResponseError.Unauthorized
                val isErrorStateOnDisplay = loading is LoadStateType.Error ||
                        loading is LoadStateType.NoInternet
                if (isSecurityProblem || isNonAuthorized || !isErrorStateOnDisplay) {
                    error?.also(
                        errorHandler::handleError
                    )
                }
                true
            }
    }
}