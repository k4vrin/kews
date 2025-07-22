package dev.kavrin.kews.core.network

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import kotlinx.serialization.json.Json

// Define the success code range.
val successCodeRange: IntRange = 200..299

// Extension function to convert an HttpResponse's status to a StatusCode.
fun HttpResponse.getStatusCode(): StatusCode {
    return StatusCode.from(this.status.value)
}

// Extension property to get the StatusCode from the HTTP response payload.
val ApiResponse.Failure.HttpError<HttpResponse>.statusCode: StatusCode
    get() = this.errorBody.getStatusCode()

/**
 * Maps the error type E of ApiResponse<T, E> to another type R using the provided transform function.
 * This is useful for converting raw HTTP responses to domain-specific error types.
 *
 * @param transform A function that transforms the error of type E to type R
 * @return ApiResponse<T, R> with the same structure but with transformed error type
 */
inline fun <T, E, R> ApiResponse<T, E>.mapError(transform: (E) -> R): ApiResponse<T, R> {
    return when (this) {
        is ApiResponse.Success -> ApiResponse.Success(this.data)
        is ApiResponse.Failure.HttpError -> ApiResponse.Failure.HttpError(
            errorBody = transform(this.errorBody),
            errorCode = this.errorCode
        )
        is ApiResponse.Failure.Unexpected -> ApiResponse.Failure.Unexpected(this.throwable)
    }
}

/**
 * A helper suspend function to wrap an HTTP call in an ApiResponse.
 * If the response status falls within successCodeRange (200-299), it returns Success.
 * Otherwise, it returns an HttpError with the response and status code.
 */
suspend inline fun <reified T> apiResponseOf(
    crossinline f: suspend () -> HttpResponse,
): ApiResponse<T, HttpResponse> = try {
    val response = f()
    if (response.status.value in successCodeRange) {
        ApiResponse.Success(
            data = response.body() ?: Unit as T
        )
    } else {
        ApiResponse.Failure.HttpError(
            errorBody = response,
            errorCode = response.status.value
        )
    }
} catch (ex: Exception) {
    ApiResponse.Failure.Unexpected(ex)
}

/**
 * Extension function to parse the response body into a custom error DTO.
 * Uses exception handling to provide fallback parsing in case of errors.
 */
suspend inline fun <reified E> HttpResponse.parseErrorBody(
    json: Json = Json {
        ignoreUnknownKeys = true
    },
): E {
    val responseText: String = this.body()
    return try {
        json.decodeFromString<E>(responseText)
    } catch (ex: Exception) {
        throw IllegalStateException("Failed to parse error response: $responseText", ex)
    }
}