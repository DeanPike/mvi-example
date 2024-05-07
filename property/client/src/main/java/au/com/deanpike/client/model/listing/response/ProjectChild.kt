package au.com.deanpike.client.model.listing.response

data class ProjectChild(
    override val id: Long,
    override val listingType: ListingType,
    val listingDetails: ListingDetails,
    val lifecycleStatus: String?
) : Listing()