/*
 * Created by Nitheesh AG on 2022/8/13
 */

package com.heirarchylist.util

import retrofit2.HttpException
import java.io.IOException

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T) : ResultWrapper<T>()
    data class GenericError(
        val code: Int = -2,
        val error: String = "Unknown error",
    ) : ResultWrapper<Nothing>()

    object NetworkError : ResultWrapper<Nothing>()
}

suspend fun <T> safeApiCall(apiCall: suspend () -> T): ResultWrapper<T> {
    return try {
        ResultWrapper.Success(apiCall.invoke())
    } catch (throwable: Throwable) {
        when (throwable) {
            is IOException -> ResultWrapper.NetworkError
            is HttpException -> {
                ResultWrapper.GenericError(
                    throwable.code(),
                    throwable.message()
                )
            }
            else -> ResultWrapper.GenericError(
                error = throwable.message ?: throwable.localizedMessage
            )
        }
    }
}