package au.com.deanpike.network.model.external.listing

import com.google.gson.annotations.SerializedName

data class SearchResult(
    @SerializedName("listing_type")
    val listingType: String,
    val id: Long,
    @SerializedName("date_listed")
    val dateListed: String?,
    val address: String,
    val price: String?,
    val media: List<Medium>?,
    @SerializedName("bedroom_count")
    val bedroomCount: Long?,
    @SerializedName("bathroom_count")
    val bathroomCount: Long?,
    @SerializedName("homepass_enabled")
    val homepassEnabled: Boolean?,
    @SerializedName("additional_features")
    val additionalFeatures: List<String>?,
    @SerializedName("geo_location")
    val geoLocation: GeoLocation,
    @SerializedName("promo_level")
    val promoLevel: String,
    @SerializedName("carspace_count")
    val carspaceCount: Long?,
    @SerializedName("dwelling_type")
    val dwellingType: String?,
    @SerializedName("has_video")
    val hasVideo: Boolean?,
    val headline: String?,
    @SerializedName("land_area")
    val landArea: String?,
    @SerializedName("large_land")
    val largeLand: Boolean?,
    val metadata: MetaData?,
    val advertiser: Advertiser?,
    val topspot: Topspot?,
    val project: Project?,
    @SerializedName("lifecycle_status")
    val lifecycleStatus: String?,
    @SerializedName("earliest_inspections")
    val earliestInspections: List<EarliestInspection>?,
    @SerializedName("auction_date")
    val auctionDate: String?,
)