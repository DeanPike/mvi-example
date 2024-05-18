package au.com.deanpike.listings.client.util

sealed class ResponseWrapper<out T> {
    data class Success<out T>(val data: T) : ResponseWrapper<T>()
    data class Error<out T>(val exception: java.lang.Exception) : ResponseWrapper<T>()
}