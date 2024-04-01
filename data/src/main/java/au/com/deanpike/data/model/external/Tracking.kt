package au.com.deanpike.data.model.external

import com.google.gson.annotations.SerializedName

internal data class Tracking(
    @SerializedName("search.propertyType")
    val searchPropertyType: String,
    @SerializedName("search.surSuburbs")
    val searchSurSuburbs: Boolean,
    @SerializedName("search.excludeUnderOffer")
    val searchExcludeUnderOffer: Boolean,
    @SerializedName("search.searchResultCount")
    val searchSearchResultCount: Long,
    @SerializedName("cat.subCategory1")
    val catSubCategory1: String,
)