package au.com.deanpike.data.model.external

import com.google.gson.annotations.SerializedName

internal data class Media(

    @SerializedName("type") var type: String? = null,
    @SerializedName("image_url") var imageUrl: String? = null,
    @SerializedName("media_type") var mediaType: String? = null

)