package au.com.deanpike.listings.client.model.listing.response

import au.com.deanpike.commonshared.model.ListingDetails
import au.com.deanpike.datashared.type.ListingType

data class ProjectChild(
    override val id: Long,
    override val listingType: ListingType,
    val listingDetails: ListingDetails,
    val lifecycleStatus: String?
) : Listing()