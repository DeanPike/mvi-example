package au.com.deanpike.data.model.external

import com.google.gson.annotations.SerializedName

internal data class MetaData(
    @SerializedName("address_components")
    val addressComponents: AddressComponents,
)