package au.com.deanpike.data.model.external

import com.google.gson.annotations.SerializedName

internal data class ChildListing(
    val id: Long,
    @SerializedName("date_listed")
    val dateListed: String,
    @SerializedName("lifecycle_status")
    val lifecycleStatus: String,
    val price: String,
    @SerializedName("bathroom_count")
    val bathroomCount: Long?,
    @SerializedName("bedroom_count")
    val bedroomCount: Long?,
    @SerializedName("carspace_count")
    val carspaceCount: Long?,
    @SerializedName("homepass_enabled")
    val homepassEnabled: Boolean,
    @SerializedName("property_size")
    val propertySize: String?,
    @SerializedName("earliest_inspections")
    val earliestInspections: List<EarliestInspection>?,
)