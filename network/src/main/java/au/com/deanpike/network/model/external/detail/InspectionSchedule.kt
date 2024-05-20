package au.com.deanpike.network.model.external.detail

import com.google.gson.annotations.SerializedName

data class InspectionSchedule(
    @SerializedName("is_by_appointment_only") var isByAppointmentOnly: Boolean? = null,
    @SerializedName("is_bookings_enabled") var isBookingsEnabled: Boolean? = null,
    @SerializedName("inspections") var inspections: List<Inspections> = emptyList()
)