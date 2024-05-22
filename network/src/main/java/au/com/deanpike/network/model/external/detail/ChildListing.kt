package au.com.deanpike.network.model.external.detail

import com.google.gson.annotations.SerializedName

data class ChildListing(
    val id: Long,
    @SerializedName("bedroom_count")
    val bedroomCount: Double,
    @SerializedName("bathroom_count")
    val bathroomCount: Double,
    @SerializedName("carspace_count")
    val carspaceCount: Double,
    val price: String,
    @SerializedName("property_type_description")
    val propertyTypeDescription: String,
    @SerializedName("property_url")
    val propertyUrl: String,
    @SerializedName("property_image")
    val propertyImage: String,
    @SerializedName("lifecycle_status")
    val lifecycleStatus: String,
)
