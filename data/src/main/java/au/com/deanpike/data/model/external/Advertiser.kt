package au.com.deanpike.data.model.external

import com.google.gson.annotations.SerializedName

internal data class Advertiser(
    @SerializedName("agency_listing_contacts") var agencyListingContacts: ArrayList<AgencyListingContacts> = arrayListOf(),
    @SerializedName("id") var id: Int? = null,
    @SerializedName("images") var images: Images? = Images(),
    @SerializedName("name") var name: String? = null,
    @SerializedName("preferred_color_hex") var preferredColorHex: String? = null
)