package au.com.deanpike.data.model.external

import com.google.gson.annotations.SerializedName

internal data class AgencyListingContact(
    @SerializedName("display_full_name")
    val displayFullName: String,
    @SerializedName("image_url")
    val imageUrl: String?,
)