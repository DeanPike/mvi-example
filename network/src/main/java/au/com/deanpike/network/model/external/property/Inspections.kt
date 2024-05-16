package au.com.deanpike.network.model.external.property

import com.google.gson.annotations.SerializedName

data class Inspections(
    @SerializedName("recurrence_type") var recurrenceType: String? = null,
    @SerializedName("time_open") var timeOpen: String? = null,
    @SerializedName("time_close") var timeClose: String? = null
)