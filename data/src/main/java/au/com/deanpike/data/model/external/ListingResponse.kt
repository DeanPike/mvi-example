package au.com.deanpike.data.model.external

import com.google.gson.annotations.SerializedName

internal data class ListingResponse(
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
    @SerializedName("tracking_metadata")
    val trackingMetadata: TrackingMetadata,
    val tracking: Tracking,
    @SerializedName("allows_immediate_property_alert")
    val allowsImmediatePropertyAlert: Boolean,
    @SerializedName("search_insights")
    val searchInsights: SearchInsights,
)