package au.com.deanpike.data.datasource.remote

import au.com.deanpike.data.api.PropertyListingApi
import au.com.deanpike.data.datasource.remote.base.DataSourceBase
import au.com.deanpike.data.model.external.ListingResponse
import au.com.deanpike.data.model.internal.ListingSearchRequest
import au.com.deanpike.datashared.util.ResponseWrapper
import javax.inject.Inject

internal interface ListingDataSource {
    suspend fun getListings(request: ListingSearchRequest): ResponseWrapper<ListingResponse>
}

internal class ListingDataSourceImpl @Inject constructor(
    private val api: PropertyListingApi
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