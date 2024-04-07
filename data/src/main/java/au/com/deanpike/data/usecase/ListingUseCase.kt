package au.com.deanpike.data.usecase

import au.com.deanpike.client.model.listing.response.Listing
import au.com.deanpike.client.model.listing.search.ListingSearch
import au.com.deanpike.client.usecase.ListingUseCase
import au.com.deanpike.client.util.ResponseWrapper
import au.com.deanpike.data.repository.ListingRepository
import au.com.deanpike.network.model.internal.ListingSearchRequest
import javax.inject.Inject

internal class ListingUseCaseImpl @Inject constructor(
    private val repo: ListingRepository
) : ListingUseCase {
    override suspend fun getListings(search: ListingSearch): ResponseWrapper<List<Listing>> {
        return repo.getListings(
            ListingSearchRequest(
                searchMode = search.searchMode,
                dwellingTypes = search.dwellingTypes
            )
        )
    }
}