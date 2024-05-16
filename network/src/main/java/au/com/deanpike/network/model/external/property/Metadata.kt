package au.com.deanpike.network.model.external.property

import au.com.deanpike.network.model.external.listing.AddressComponents
import com.google.gson.annotations.SerializedName

data class Metadata(
    @SerializedName("address_components") var addressComponents: AddressComponents? = null
)