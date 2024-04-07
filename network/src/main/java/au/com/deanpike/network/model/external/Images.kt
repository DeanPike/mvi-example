package au.com.deanpike.network.model.external

import com.google.gson.annotations.SerializedName

data class Images(
    @SerializedName("logo_url")
    val logoUrl: String,
)