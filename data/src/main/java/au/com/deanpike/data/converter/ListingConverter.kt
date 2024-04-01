package au.com.deanpike.data.converter

import au.com.deanpike.client.model.listing.response.Listing
import au.com.deanpike.data.model.external.SearchResult

internal interface ListingConverter {
    fun convertListing(listing: SearchResult): Listing
}