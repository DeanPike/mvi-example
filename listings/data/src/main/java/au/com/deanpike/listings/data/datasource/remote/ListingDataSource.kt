package au.com.deanpike.listings.data.datasource.remote

import au.com.deanpike.commonshared.util.ResponseWrapper
import au.com.deanpike.network.api.ListingApi
import au.com.deanpike.network.model.external.listing.ListingResponse
import au.com.deanpike.network.model.internal.ListingSearchRequest
import au.com.deanpike.network.util.DataSourceBase
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