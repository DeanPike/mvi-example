package au.com.deanpike.data.model.external

import com.google.gson.annotations.SerializedName

internal data class Project(

    @SerializedName("project_name") var projectName: String? = null,
    @SerializedName("project_banner_image") var projectBannerImage: String? = null,
    @SerializedName("project_logo_image") var projectLogoImage: String? = null,
    @SerializedName("project_price_from") var projectPriceFrom: Int? = null,
    @SerializedName("child_listings") var childListings: ArrayList<ChildListings> = arrayListOf()

)