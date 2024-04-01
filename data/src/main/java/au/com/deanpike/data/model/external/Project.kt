package au.com.deanpike.data.model.external

import com.google.gson.annotations.SerializedName

internal data class Project(
    @SerializedName("project_name")
    val projectName: String,
    @SerializedName("project_banner_image")
    val projectBannerImage: String,
    @SerializedName("project_logo_image")
    val projectLogoImage: String,
    @SerializedName("project_price_from")
    val projectPriceFrom: Long?,
    @SerializedName("child_listings")
    val childListings: List<ChildListing>,
    @SerializedName("project_color_hex")
    val projectColorHex: String?,
)