package au.com.deanpike.data.model.external

import com.google.gson.annotations.SerializedName

internal data class Medium(
    val type: String,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("media_type")
    val mediaType: String,
)