package au.com.deanpike.data.model.external

import com.google.gson.annotations.SerializedName

internal data class Metadata(

    @SerializedName("address_components") var addressComponents: AddressComponents? = AddressComponents()

)