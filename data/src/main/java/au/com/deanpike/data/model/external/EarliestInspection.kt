package au.com.deanpike.data.model.external

import com.google.gson.annotations.SerializedName

internal data class EarliestInspection(
    @SerializedName("time_open")
    val timeOpen: String,
    @SerializedName("time_close")
    val timeClose: String,
    @SerializedName("recurrence_type")
    val recurrenceType: String,
)