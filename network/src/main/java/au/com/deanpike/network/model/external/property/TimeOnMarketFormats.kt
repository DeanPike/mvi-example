package au.com.deanpike.network.model.external.property

import com.google.gson.annotations.SerializedName

data class TimeOnMarketFormats(
    @SerializedName("subheading") var subheading: String? = null,
    @SerializedName("details_section") var detailsSection: String? = null
)