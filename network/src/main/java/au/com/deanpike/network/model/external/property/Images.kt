package au.com.deanpike.network.model.external.property

import com.google.gson.annotations.SerializedName

data class Images(
    @SerializedName("logo_url") var logoUrl: String? = null,
    @SerializedName("agency_banner_image_url") var agencyBannerImageUrl: String? = null
)