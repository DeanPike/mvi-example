package au.com.deanpike.data.model.external

import com.google.gson.annotations.SerializedName

internal data class AddressComponents(
    @SerializedName("street") var street: String? = null,
    @SerializedName("street_number") var streetNumber: String? = null,
    @SerializedName("state_short") var stateShort: String? = null,
    @SerializedName("suburb_id") var suburbId: Int? = null,
    @SerializedName("suburb") var suburb: String? = null,
    @SerializedName("postcode") var postcode: String? = null
)