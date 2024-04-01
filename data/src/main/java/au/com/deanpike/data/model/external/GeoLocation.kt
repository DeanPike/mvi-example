package au.com.deanpike.data.model.external

import com.google.gson.annotations.SerializedName

internal data class GeoLocation(

    @SerializedName("latitude") var latitude: Double? = null,
    @SerializedName("longitude") var longitude: Double? = null

)