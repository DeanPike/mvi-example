package au.com.deanpike.network.model.external.listing

import com.google.gson.annotations.SerializedName

data class Advertiser(
    @SerializedName("agency_listing_contacts")
    val agencyListingContacts: List<AgencyListingContact>,
    val id: Long,
    val images: Images,
    val name: String,
    @SerializedName("preferred_color_hex")
    val preferredColorHex: String,
)