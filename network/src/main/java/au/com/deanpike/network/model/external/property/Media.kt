package au.com.deanpike.network.model.external.property

import com.google.gson.annotations.SerializedName

data class Media(
    @SerializedName("media_type") var mediaType: String? = null,
    @SerializedName("type") var type: String? = null,
    @SerializedName("image_url") var imageUrl: String? = null
)