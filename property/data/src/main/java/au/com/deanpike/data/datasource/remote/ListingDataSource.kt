package au.com.deanpike.data.datasource.remote

import au.com.deanpike.client.util.ResponseWrapper
import au.com.deanpike.data.datasource.remote.base.DataSourceBase
import au.com.deanpike.network.api.ListingApi
import au.com.deanpike.network.model.external.ListingResponse
import au.com.deanpike.network.model.internal.ListingSearchRequest
import javax.inject.Inject

internal interface ListingDataSource {
    suspend fun getListings(request: ListingSearchRequest): ResponseWrapper<ListingResponse>
}

internal class ListingDataSourceImpl @Inject constructor(
    private val api: ListingApi
) : ListingDataSource, DataSourceBase(
) {
    override suspend fun getListings(request: ListingSearchRequest): ResponseWrapper<ListingResponse> {
        return safeApiCall {
            api.getListings(
                contentType = "application/json",
                body = request
            )
        }
    }
}