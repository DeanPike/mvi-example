package au.com.deanpike.listings.client.usecase

import au.com.deanpike.commonshared.util.ResponseWrapper
import au.com.deanpike.listings.client.model.suggestedlocation.Location

interface SuggestedLocationsUseCase {
    suspend fun getSuggestedLocations(location: String): ResponseWrapper<List<Location>>
}