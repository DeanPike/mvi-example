package au.com.deanpike.listings.client.model.listing.response

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