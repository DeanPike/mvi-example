package au.com.deanpike.client.model.listing.search

import au.com.deanpike.client.type.ListingType
import au.com.deanpike.client.type.StatusType

data class ListingSearch(
    val searchMode: StatusType,
    val dwellingTypes: List<ListingType>
)