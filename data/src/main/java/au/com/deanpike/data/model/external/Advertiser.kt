package au.com.deanpike.data.model.external

import com.google.gson.annotations.SerializedName

internal data class Advertiser(
    @SerializedName("agency_listing_contacts")
    val agencyListingContacts: List<AgencyListingContact>,
    val id: Long,
    val images: Images,
    val name: String,
    @SerializedName("preferred_color_hex")
    val preferredColorHex: String,
)