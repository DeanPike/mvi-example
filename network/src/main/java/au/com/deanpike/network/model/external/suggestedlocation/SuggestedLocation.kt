package au.com.deanpike.network.model.external.suggestedlocation

import com.google.gson.annotations.SerializedName

data class SuggestedLocation(
    @SerializedName("DisplayName")
    val displayName: String? = null,
    @SerializedName("Name")
    val name: String? = null,
    @SerializedName("State")
    val state: String? = null,
    @SerializedName("RegionName")
    val regionName: String? = null,
    @SerializedName("AreaName")
    val areaName: String? = null,
    @SerializedName("Postcode")
    val postCode: String? = null,
    @SerializedName("SuburbID")
    val suburbId: String? = null,
    @SerializedName("NameSlug")
    val nameSlug: String? = null,
    @SerializedName("Category")
    val category: String? = null,
    @SerializedName("Group")
    val group: String? = null,
)