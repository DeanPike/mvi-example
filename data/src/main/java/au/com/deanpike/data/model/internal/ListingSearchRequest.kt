package au.com.deanpike.data.model.internal

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
internal data class ListingSearchRequest(
    @SerializedName("search_mode")
    val searchMode: String,
    @SerializedName("dwelling_types")
    val dwellingTypes: List<String>
)