package au.com.deanpike.listings.client.model.listing.search

import au.com.deanpike.listings.client.type.ListingType
import au.com.deanpike.listings.client.type.StatusType

data class ListingSearch(
    val searchMode: StatusType,
    val dwellingTypes: List<ListingType>
)