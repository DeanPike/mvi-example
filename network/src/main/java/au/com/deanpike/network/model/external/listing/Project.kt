package au.com.deanpike.network.model.external.listing

import com.google.gson.annotations.SerializedName

data class Project(
    @SerializedName("project_name")
    val projectName: String,
    @SerializedName("project_banner_image")
    val projectBannerImage: String,
    @SerializedName("project_logo_image")
    val projectLogoImage: String,
    @SerializedName("project_price_from")
    val projectPriceFrom: Long?,
    @SerializedName("child_listings")
    val childListings: List<ProjectChildListing>,
    @SerializedName("project_color_hex")
    val projectColorHex: String?,
)