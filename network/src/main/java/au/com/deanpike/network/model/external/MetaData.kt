package au.com.deanpike.network.model.external

import com.google.gson.annotations.SerializedName

data class MetaData(
    @SerializedName("address_components")
    val addressComponents: AddressComponents,
)