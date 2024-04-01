package au.com.deanpike.client.model.listing.response

data class ListingDetails(
    val price: String?,
    val numberOfBedrooms: Int?,
    val numberOfBathrooms: Int?,
    val numberOfCarSpaces: Int?
)