package au.com.deanpike.network.model.external.listing

import com.google.gson.annotations.SerializedName

data class AgencyListingContact(
    @SerializedName("display_full_name")
    val displayFullName: String,
    @SerializedName("image_url")
    val imageUrl: String?,
)