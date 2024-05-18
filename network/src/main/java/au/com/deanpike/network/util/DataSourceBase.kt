package au.com.deanpike.network.util

import au.com.deanpike.commonshared.util.ResponseWrapper

abstract class DataSourceBase {
    suspend fun <T> safeApiCall(apiCall: suspend () -> T): ResponseWrapper<T> {
        return try {
            ResponseWrapper.Success(apiCall.invoke())
        } catch (exception: Exception) {
            ResponseWrapper.Error(exception)
        }
    }
}