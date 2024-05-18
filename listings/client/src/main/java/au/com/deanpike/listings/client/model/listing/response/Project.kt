package au.com.deanpike.listings.client.model.listing.response

data class Project(
    override val id: Long,
    override val listingType: ListingType,
    val address: String,
    val listingImage: String?,
    val bannerImage: String?,
    val logoImage: String?,
    val projectName: String?,
    val projectColour: String?,
    val properties: List<ProjectChild>
) : Listing() {
}