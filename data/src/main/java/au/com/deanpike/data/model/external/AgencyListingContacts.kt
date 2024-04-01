package au.com.deanpike.data.model.external

import com.google.gson.annotations.SerializedName

internal data class AgencyListingContacts(
    @SerializedName("display_full_name") var displayFullName: String? = null,
    @SerializedName("image_url") var imageUrl: String? = null
)