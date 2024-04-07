package au.com.deanpike.network.model.external

import com.google.gson.annotations.SerializedName

data class ListingResponse(
    @SerializedName("search_results")
    val searchResults: List<SearchResult>,
    @SerializedName("new_results")
    val newResults: Any?,
    @SerializedName("results_total")
    val resultsTotal: Long,
    @SerializedName("results_returned")
    val resultsReturned: Long,
    @SerializedName("school_metadata")
    val schoolMetadata: Any?,
    @SerializedName("allows_immediate_property_alert")
    val allowsImmediatePropertyAlert: Boolean,
)