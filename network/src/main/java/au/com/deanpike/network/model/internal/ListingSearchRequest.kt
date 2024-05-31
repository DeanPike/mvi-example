package au.com.deanpike.network.model.internal

import com.google.gson.annotations.SerializedName

data class ListingSearchRequest(
    @SerializedName("search_mode")
    val searchMode: String,
    @SerializedName("dwelling_types")
    val dwellingTypes: List<String>
)