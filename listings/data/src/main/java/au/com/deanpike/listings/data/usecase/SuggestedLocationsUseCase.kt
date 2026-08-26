package au.com.deanpike.listings.data.usecase

import au.com.deanpike.commonshared.util.ResponseWrapper
import au.com.deanpike.listings.client.model.suggestedlocation.Location
import au.com.deanpike.listings.client.usecase.SuggestedLocationsUseCase
import au.com.deanpike.listings.data.repository.SuggestedLocationsRepository
import javax.inject.Inject

internal class SuggestedLocationsUseCaseImpl @Inject constructor(
    private val repo: SuggestedLocationsRepository
) : SuggestedLocationsUseCase {
    override suspend fun getSuggestedLocations(location: String): ResponseWrapper<List<Location>> {
        return repo.getSuggestedLocations(location)
    }
}