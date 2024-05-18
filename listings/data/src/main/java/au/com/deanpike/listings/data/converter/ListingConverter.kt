package au.com.deanpike.listings.data.converter

import au.com.deanpike.client.model.listing.response.Listing
import au.com.deanpike.network.model.external.listing.SearchResult

internal interface ListingConverter {
    fun convertListing(listing: SearchResult): Listing
}