package au.com.deanpike.network.model.external.listing

import com.google.gson.annotations.SerializedName

data class Medium(
    val type: String,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("media_type")
    val mediaType: String,
)