package au.com.deanpike.listings.data.datasource.remote

import au.com.deanpike.commonshared.util.ResponseWrapper
import au.com.deanpike.network.api.SuggestedLocationsApi
import au.com.deanpike.network.model.external.suggestedlocation.SuggestedLocations
import au.com.deanpike.network.util.DataSourceBase
import javax.inject.Inject

internal interface SuggestedLocationsDataSource {
    suspend fun getSuggestedLocations(location: String): ResponseWrapper<SuggestedLocations>
}

internal class SuggestedLocationsDataSourceImpl @Inject constructor(
    private val api: SuggestedLocationsApi
) : SuggestedLocationsDataSource, DataSourceBase() {
    override suspend fun getSuggestedLocations(location: String): ResponseWrapper<SuggestedLocations> {
        return safeApiCall {
            api.getSuggestedLocations(
                contentType = "application/json",
                searchText = location
            )
        }
    }
}