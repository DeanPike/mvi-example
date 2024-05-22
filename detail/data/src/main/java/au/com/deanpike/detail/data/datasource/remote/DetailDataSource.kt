package au.com.deanpike.detail.data.datasource.remote

import au.com.deanpike.commonshared.util.ResponseWrapper
import au.com.deanpike.network.api.PropertyDetailApi
import au.com.deanpike.network.model.external.detail.PropertyDetail
import au.com.deanpike.network.util.DataSourceBase
import javax.inject.Inject

internal interface DetailDataSource {
    suspend fun getListingDetails(id: Int): ResponseWrapper<PropertyDetail>
}

internal class DetailDataSourceImpl @Inject constructor(
    private val api: PropertyDetailApi
) : DetailDataSource, DataSourceBase() {
    override suspend fun getListingDetails(id: Int): ResponseWrapper<PropertyDetail> {
        return safeApiCall {
            api.getPropertyDetails(
                contentType = "application/json",
                id = id.toString()
            )
        }
    }
}