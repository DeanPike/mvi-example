package au.com.deanpike.network.model.external.detail

import com.google.gson.annotations.SerializedName

data class Advertiser(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("type") var type: String? = null,
    @SerializedName("address") var address: String? = null,
    @SerializedName("images") var images: Images? = Images(),
    @SerializedName("preferred_color_hex") var preferredColorHex: String? = null,
    @SerializedName("agency_listing_contacts") var agencyListingContacts: ArrayList<AgencyListingContacts> = arrayListOf(),
    @SerializedName("domain_url") var domainUrl: String? = null
)