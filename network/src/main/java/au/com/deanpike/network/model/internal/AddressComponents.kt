package au.com.deanpike.network.model.internal

import com.google.gson.annotations.SerializedName

data class AddressComponents(
    val area: String? = null,
    val postcode: String? = null,
    val region: String? = null,
    @SerializedName("state_short")
    val stateShort: String? = null,
    val street: String? = null,
    @SerializedName("street_number")
    val streetNumber: String? = null,
    @SerializedName("unit_number")
    val unitNumber: String? = null,
    val suburb: String? = null,
    @SerializedName("suburb_id")
    val suburbId: Int? = null,
    val district: String? = null
)
