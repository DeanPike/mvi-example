package au.com.deanpike.network.model.external.detail

import com.google.gson.annotations.SerializedName

data class CTA(
    val title: String,
    @SerializedName("body_text")
    val bodyText: String,
    val button: Button,
)
