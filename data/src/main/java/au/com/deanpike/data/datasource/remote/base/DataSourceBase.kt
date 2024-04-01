package au.com.deanpike.data.datasource.remote.base

import au.com.deanpike.datashared.util.ResponseWrapper

abstract class DataSourceBase {
    suspend fun <T> safeApiCall(apiCall: suspend () -> T): ResponseWrapper<T> {
        return try {
            ResponseWrapper.Success(apiCall.invoke())
        } catch (exception: Exception) {
            ResponseWrapper.Error(exception)
        }
    }
}