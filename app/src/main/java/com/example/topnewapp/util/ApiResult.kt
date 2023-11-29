package com.example.topnewapp.util

sealed class ApiResult<out T> {
    data class Success<out T>(val data: T) : ApiResult<T>()
    data class Error(val exception: Exception? = null,val error: String = "Error") : ApiResult<Nothing>()
    object Loading : ApiResult<Nothing>()
}
