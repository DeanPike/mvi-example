package au.com.deanpike.data.model.external

import com.google.gson.annotations.SerializedName

internal data class Topspot(

    @SerializedName("available_listings") var availableListings: Int? = null

)