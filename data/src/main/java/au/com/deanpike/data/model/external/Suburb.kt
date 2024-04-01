package au.com.deanpike.data.model.external

import com.google.gson.annotations.SerializedName

internal data class Suburb(
    @SerializedName("suburb_profile_url")
    val suburbProfileUrl: String,
)