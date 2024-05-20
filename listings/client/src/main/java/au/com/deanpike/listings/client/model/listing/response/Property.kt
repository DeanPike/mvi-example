package au.com.deanpike.listings.client.model.listing.response

import au.com.deanpike.datashared.type.ListingType

data class Property(
    override val id: Long,
    override val listingType: ListingType,
    val address: String,
    val listingImage: String?,
    val agencyImage: String?,
    val agencyColour: String?,
    val dwellingType: String?,
    val headLine: String?,
    val lifecycleStatus: String?,
    val detail: ListingDetails
) : Listing()