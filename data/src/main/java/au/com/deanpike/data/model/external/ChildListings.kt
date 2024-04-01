package au.com.deanpike.data.model.external

import com.google.gson.annotations.SerializedName

internal data class ChildListings(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("date_listed") var dateListed: String? = null,
    @SerializedName("lifecycle_status") var lifecycleStatus: String? = null,
    @SerializedName("price") var price: String? = null,
    @SerializedName("bathroom_count") var bathroomCount: Int? = null,
    @SerializedName("bedroom_count") var bedroomCount: Int? = null,
    @SerializedName("carspace_count") var carspaceCount: Int? = null,
    @SerializedName("homepass_enabled") var homepassEnabled: Boolean? = null,
    @SerializedName("property_size") var propertySize: String? = null

)