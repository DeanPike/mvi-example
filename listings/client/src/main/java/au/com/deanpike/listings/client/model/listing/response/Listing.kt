package au.com.deanpike.listings.client.model.listing.response

import au.com.deanpike.datashared.type.ListingType

abstract class Listing {
    abstract val id: Long
    abstract val listingType: ListingType
}