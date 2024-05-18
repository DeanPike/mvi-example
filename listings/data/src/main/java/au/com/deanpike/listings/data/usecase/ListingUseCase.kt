package au.com.deanpike.listings.data.usecase

import au.com.deanpike.commonshared.util.ResponseWrapper
import au.com.deanpike.listings.client.model.listing.response.Listing
import au.com.deanpike.listings.client.model.listing.search.ListingSearch
import au.com.deanpike.listings.client.type.DwellingType
import au.com.deanpike.listings.client.type.StatusType
import au.com.deanpike.listings.client.usecase.ListingUseCase
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

    private fun getDwellingTypes(types: List<DwellingType>): List<String>? {
        val dwellings = mutableListOf<String>()
        return if (types.isEmpty()) {
            null
        } else {
            types.forEach { listingType ->
                val value = when (listingType) {
                    DwellingType.ALL -> null
                    DwellingType.HOUSE -> "House"
                    DwellingType.TOWNHOUSE -> "Townhouse"
                    DwellingType.APARTMENT_UNIT_FLAT -> "Apartment / Unit / Flat"

                }
                value?.let {
                    dwellings.add(it)
                }
            }

            return dwellings
        }
    }
}