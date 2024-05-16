package au.com.deanpike.network.model.external.listing

import com.google.gson.annotations.SerializedName

data class Suburb(
    @SerializedName("suburb_profile_url")
    val suburbProfileUrl: String,
)