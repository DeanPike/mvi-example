package au.com.deanpike.client.model.listing.search

data class ListingSearch(
    val searchMode: String,
    val dwellingTypes: List<String>
)