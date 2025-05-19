package com.utmaximur.message.integration

import androidx.compose.material3.SnackbarDuration
import com.utmaximur.message.models.MessageContainer
import com.utmaximur.message.store.MessageStore
import features.message.message_.Res
import features.message.message_.successful_save_message
import features.message.message_.successful_update_message
import features.message.message_.unknown_error_message
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.getString

/**
 * Общий интерфейс для преобразования сообщений
 */
internal interface MessageToSnackbarConverter {
    suspend fun toSnackbarMessageUi(): MessageStore.Label.SnackbarMessage
}

/**
 * Базовый класс для сообщений с текстом
 */
internal sealed class TextMessageContainer : MessageToSnackbarConverter {
    abstract val text: String?

    protected suspend fun getFallbackText(defaultRes: StringResource): String {
        return text ?: getString(defaultRes)
    }
}

// Реализации для каждого типа сообщения
internal class SimpleMessageConverter(
    message: MessageContainer.SimpleMessage
) : TextMessageContainer() {
    override val text: String = message.text

    override suspend fun toSnackbarMessageUi() = MessageStore.Label.SnackbarMessage(
        userMessage = text
    )
}

internal class ErrorMessageConverter(
    message: MessageContainer.ErrorMessage
) : TextMessageContainer() {
    override val text: String? = message.text

    override suspend fun toSnackbarMessageUi() = MessageStore.Label.SnackbarMessage(
        userMessage = getFallbackText(Res.string.unknown_error_message),
        duration = SnackbarDuration.Long
    )
}

internal class SuccessMessageConverter(
    private val resId: StringResource
) : MessageToSnackbarConverter {
    override suspend fun toSnackbarMessageUi() = MessageStore.Label.SnackbarMessage(
        userMessage = getString(resId),
        duration = SnackbarDuration.Short
    )
}

// Фабрика преобразователей
internal fun MessageContainer.toConverter(): MessageToSnackbarConverter = when (this) {
    is MessageContainer.SnackbarMessage -> object : MessageToSnackbarConverter {
        override suspend fun toSnackbarMessageUi() = this@toConverter.run {
            MessageStore.Label.SnackbarMessage(
                userMessage = userMessage,
                actionLabelMessage = actionLabelMessage,
                duration = duration.toUiDuration(),
                withDismissAction = withDismissAction
            )
        }
    }

    is MessageContainer.SimpleMessage -> SimpleMessageConverter(this)
    is MessageContainer.ErrorMessage -> ErrorMessageConverter(this)
    MessageContainer.SuccessfulSaveMessage -> SuccessMessageConverter(Res.string.successful_save_message)
    MessageContainer.SuccessfulUpdateMessage -> SuccessMessageConverter(Res.string.successful_update_message)
}

private fun MessageContainer.SnackbarMessage.SnackbarDuration.toUiDuration() =
    when (this) {
        MessageContainer.SnackbarMessage.SnackbarDuration.Short -> SnackbarDuration.Short
        MessageContainer.SnackbarMessage.SnackbarDuration.Long -> SnackbarDuration.Long
        MessageContainer.SnackbarMessage.SnackbarDuration.Indefinite -> SnackbarDuration.Indefinite
    }