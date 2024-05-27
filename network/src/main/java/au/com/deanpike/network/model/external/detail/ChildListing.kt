package au.com.deanpike.network.model.external.detail

import com.google.gson.annotations.SerializedName

data class ChildListing(
    val id: Long,
    @SerializedName("bedroom_count")
    val bedroomCount: Double? = null,
    @SerializedName("bathroom_count")
    val bathroomCount: Double? = null,
    @SerializedName("carspace_count")
    val carspaceCount: Double? = null,
    val price: String? = null,
    @SerializedName("property_type_description")
    val propertyTypeDescription: String? = null,
    @SerializedName("property_url")
    val propertyUrl: String? = null,
    @SerializedName("property_image")
    val propertyImage: String? = null,
    @SerializedName("lifecycle_status")
    val lifecycleStatus: String? = null,
)
