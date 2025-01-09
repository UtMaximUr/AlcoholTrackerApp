package com.utmaximur.remote.errors

import io.ktor.http.HttpStatusCode

/**
 * Errors related to Network
 */
sealed class NetworkResponseError : NetworkError() {

    /**
     * Bad request
     * 400 error
     */
    data object BadRequest : NetworkResponseError()

    /**
     * Request is forbidden
     * 403 error
     */
    data object Forbidden : NetworkResponseError()

    /**
     * Requested url cannot be found.
     * 404 error
     */
    data object NotFound : NetworkResponseError()

    /**
     * Server has encountered an error.
     * 500 error
     */
    data object Internal : NetworkResponseError()

    /**
     * Request is not authorized
     * 401 error
     */
    data object Unauthorized : NetworkResponseError()

    /**
     * Requested host is not reachable
     */
    data object Unreachable : NetworkResponseError()

    /**
     * Unknown error
     * 520 error
     */
    data object Unknown : NetworkResponseError()

    companion object {
        fun create(httpStatusCode: HttpStatusCode): NetworkResponseError? = when (httpStatusCode) {
            HttpStatusCode.BadRequest -> BadRequest
            HttpStatusCode.Unauthorized -> Unauthorized
            HttpStatusCode.Forbidden -> Forbidden
            HttpStatusCode.NotFound -> NotFound
            HttpStatusCode.InternalServerError -> Internal
            HttpStatusCode.BadGateway, HttpStatusCode.ServiceUnavailable,
            HttpStatusCode.GatewayTimeout -> Unreachable
            /*
             * provide other http status
             */
            else -> null
        }
    }
}