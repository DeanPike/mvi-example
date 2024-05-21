package au.com.deanpike.detail.data.datasource.remote

import au.com.deanpike.commonshared.util.ResponseWrapper
import au.com.deanpike.network.api.ListingDetailApi
import au.com.deanpike.network.model.external.detail.ListingDetail
import au.com.deanpike.network.util.DataSourceBase
import javax.inject.Inject

internal interface DetailDataSource {
    suspend fun getListingDetails(id: Int): ResponseWrapper<ListingDetail>
}

internal class DetailDataSourceImpl @Inject constructor(
    private val api: ListingDetailApi
) : DetailDataSource, DataSourceBase() {
    override suspend fun getListingDetails(id: Int): ResponseWrapper<ListingDetail> {
        return safeApiCall {
            api.getPropertyDetails(
                contentType = "application/json",
                id = id.toString()
            )
        }
    }
}