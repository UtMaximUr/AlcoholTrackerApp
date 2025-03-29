package com.utmaximur.data.kandinsky.generate_image.worker.exeption

internal class ServiceUnavailable(
    message: String = "Service unavailable"
): Exception(message)

internal class GenerationResultNotFoundException(
    message: String = "Base64 generation image not found"
) : Exception(message)

internal class GenerationFailedException(
    message: String = "Generation result failed"
) : Exception(message)

internal class TimeoutException(
    message: String = "Image generation timeout"
) : Exception(message)