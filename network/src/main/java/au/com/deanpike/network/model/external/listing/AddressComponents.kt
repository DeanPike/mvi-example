package au.com.deanpike.network.model.external.listing

import com.google.gson.annotations.SerializedName

data class AddressComponents(
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