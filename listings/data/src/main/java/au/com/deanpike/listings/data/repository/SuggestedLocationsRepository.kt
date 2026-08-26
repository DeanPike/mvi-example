package au.com.deanpike.listings.data.repository

import au.com.deanpike.commonshared.util.ResponseWrapper
import au.com.deanpike.listings.client.model.suggestedlocation.Location
import au.com.deanpike.listings.data.converter.SuggestedLocationConverter
import au.com.deanpike.listings.data.datasource.remote.SuggestedLocationsDataSource
import javax.inject.Inject

internal interface SuggestedLocationsRepository {
    suspend fun getSuggestedLocations(location: String): ResponseWrapper<List<Location>>
}

internal class SuggestedLocationsRepositoryImpl @Inject constructor(
    private val dataSource: SuggestedLocationsDataSource,
    private val converter: SuggestedLocationConverter
) : SuggestedLocationsRepository {
    override suspend fun getSuggestedLocations(location: String): ResponseWrapper<List<Location>> {
        when (val response = dataSource.getSuggestedLocations(location)) {
            is ResponseWrapper.Success -> {
                val locations = mutableListOf<Location>()
                response.data.suggestedLocations.forEach {
                    locations.add(converter.convertSuggestedLocation(it))
                }
                return ResponseWrapper.Success(locations)
            }

            is ResponseWrapper.Error -> {
                return ResponseWrapper.Error(response.exception)
            }
        }
    }

}