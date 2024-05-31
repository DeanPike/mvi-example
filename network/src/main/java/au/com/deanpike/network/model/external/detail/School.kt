package au.com.deanpike.network.model.external.detail

import au.com.deanpike.network.model.external.listing.GeoLocation
import com.google.gson.annotations.SerializedName

data class School(
    @SerializedName("id") var id: Int,
    @SerializedName("acara_id") var acaraId: Int? = null,
    @SerializedName("domain_id") var domainId: Int? = null,
    @SerializedName("address") var address: String? = null,
    @SerializedName("year_range") var yearRange: String? = null,
    @SerializedName("geo_location") var geoLocation: GeoLocation? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("website_url") var websiteUrl: String? = null,
    @SerializedName("metadata") var metadata: Metadata? = Metadata(),
    @SerializedName("education_level") var educationLevel: String? = null,
    @SerializedName("gender") var gender: String? = null,
    @SerializedName("system") var system: String? = null
)