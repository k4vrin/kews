package dev.kavrin.kews.core.network

sealed class ApiResponse<out T, out E> {
    data class Success<T>(val data: T) : ApiResponse<T, Nothing>()

    sealed class Failure<E> : ApiResponse<Nothing, E>() {

        /**
         * Represents HTTP errors that occur during API calls,
         * regardless of status code.
         */
        data class HttpError<E>(
            val errorBody: E,
            val errorCode: Int,
        ) : Failure<E>() {
            val errorMessage: String = errorBody.toString()
        }

        /**
         * Represents an unexpected failure due to an exception.
         */
        data class Unexpected(val throwable: Throwable) : Failure<Nothing>() {
            val errorMessage: String? = throwable.message
        }
    }
}