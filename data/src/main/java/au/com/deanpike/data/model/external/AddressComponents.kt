package au.com.deanpike.data.model.external

import com.google.gson.annotations.SerializedName

internal data class AddressComponents(
    val street: String,
    @SerializedName("street_number")
    val streetNumber: String,
    @SerializedName("state_short")
    val stateShort: String,
    @SerializedName("suburb_id")
    val suburbId: Long,
    val suburb: String,
    val postcode: String,
    @SerializedName("unit_number")
    val unitNumber: String?,
)