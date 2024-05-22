package au.com.deanpike.network.model.external.detail

import com.google.gson.annotations.SerializedName

data class Action(
    val type: String,
    @SerializedName("tracking_label")
    val trackingLabel: String,
    val content: String,
)
