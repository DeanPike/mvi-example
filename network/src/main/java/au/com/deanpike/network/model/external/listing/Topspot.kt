package au.com.deanpike.network.model.external.listing

import com.google.gson.annotations.SerializedName

data class Topspot(
    @SerializedName("available_listings")
    val availableListings: Int,
)