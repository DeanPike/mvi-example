package au.com.deanpike.detail.client.model.detail

data class Advertiser(
    val id: Int?,
    val name: String?,
    val address: String?,
    val logoUrl: String?,
    val agencyBannerImageUrl: String?,
    val preferredColorHex: String?,
    val agencyListingContacts: List<Agent>,
)
