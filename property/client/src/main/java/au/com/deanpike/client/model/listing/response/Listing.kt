package au.com.deanpike.client.model.listing.response

abstract class Listing {
    abstract val id: Long
    abstract val listingType: ListingType
}