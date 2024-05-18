package au.com.deanpike.listings.data.usecase

import au.com.deanpike.client.model.listing.response.Listing
import au.com.deanpike.client.model.listing.search.ListingSearch
import au.com.deanpike.client.type.ListingType
import au.com.deanpike.client.type.StatusType
import au.com.deanpike.client.usecase.ListingUseCase
import au.com.deanpike.client.util.ResponseWrapper
import au.com.deanpike.listings.data.repository.ListingRepository
import au.com.deanpike.network.model.internal.ListingSearchRequest
import javax.inject.Inject

internal class ListingUseCaseImpl @Inject constructor(
    private val repo: ListingRepository
) : ListingUseCase {
    override suspend fun getListings(search: ListingSearch): ResponseWrapper<List<Listing>> {
        return repo.getListings(
            ListingSearchRequest(
                searchMode = getSearchMode(search.searchMode),
                dwellingTypes = getDwellingTypes(search.dwellingTypes) ?: emptyList()
            )
        )
    }

    private fun getSearchMode(type: StatusType): String {
        return when (type) {
            StatusType.BUY -> "buy"
            StatusType.RENT -> "rent"
            StatusType.SOLD -> "sold"
        }
    }

    private fun getDwellingTypes(types: List<ListingType>): List<String>? {
        val dwellings = mutableListOf<String>()
        return if (types.isEmpty()) {
            null
        } else {
            types.forEach { listingType ->
                val value = when (listingType) {
                    ListingType.ALL -> null
                    ListingType.HOUSE -> "House"
                    ListingType.TOWNHOUSE -> "Townhouse"
                    ListingType.APARTMENT_UNIT_FLAT -> "Apartment / Unit / Flat"

                }
                value?.let {
                    dwellings.add(it)
                }
            }

            return dwellings
        }
    }
}