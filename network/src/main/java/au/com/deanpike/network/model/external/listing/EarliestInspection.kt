package au.com.deanpike.network.model.external.listing

import com.google.gson.annotations.SerializedName

data class EarliestInspection(
    @SerializedName("time_open")
    val timeOpen: String,
    @SerializedName("time_close")
    val timeClose: String,
    @SerializedName("recurrence_type")
    val recurrenceType: String,
)