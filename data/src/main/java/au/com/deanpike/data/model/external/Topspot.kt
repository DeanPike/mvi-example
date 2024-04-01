package au.com.deanpike.data.model.external

import com.google.gson.annotations.SerializedName

internal data class Topspot(
    @SerializedName("logo_url")
    val logoUrl: String,
)