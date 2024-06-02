package au.com.deanpike.network.model.external.detail

import au.com.deanpike.network.model.external.listing.GeoLocation
import com.google.gson.annotations.SerializedName

data class PropertyDetail(
    @SerializedName("id") override var id: Long,
    @SerializedName("headline") var headline: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("address") var address: String? = null,
    @SerializedName("listing_type") var listingType: String? = null,
    @SerializedName("lifecycle_status") var lifecycleStatus: String? = null,
    @SerializedName("media") var media: List<Media> = emptyList(),
    @SerializedName("advertiser") var advertiser: Advertiser? = Advertiser(),
    @SerializedName("geo_location") var geoLocation: GeoLocation? = null,
    @SerializedName("schools") var schools: ArrayList<School> = arrayListOf(),
    @SerializedName("inspection_schedule") var inspectionSchedule: InspectionSchedule? = InspectionSchedule(),
    @SerializedName("dwelling_type") var dwellingType: String? = null,
    @SerializedName("phone_enquiry_preference") var phoneEnquiryPreference: Boolean? = null,
    @SerializedName("price") var price: String? = null,
    @SerializedName("land_area") var landArea: String? = null,
    @SerializedName("large_land") var largeLand: Boolean? = null,
    @SerializedName("promo_level") var promoLevel: String? = null,
    @SerializedName("bedroom_count") var bedroomCount: Int? = null,
    @SerializedName("bathroom_count") var bathroomCount: Int? = null,
    @SerializedName("carspace_count") var carspaceCount: Int? = null,
    @SerializedName("metadata") var metadata: Metadata? = Metadata(),
    @SerializedName("search_mode") var searchMode: String? = null,
    @SerializedName("homepass_enabled") var homepassEnabled: Boolean? = null,
    @SerializedName("seo_url") var seoUrl: String? = null,
    @SerializedName("days_on_market") var daysOnMarket: Int? = null,
    @SerializedName("time_on_market_formats") var timeOnMarketFormats: TimeOnMarketFormats? = TimeOnMarketFormats(),
    @SerializedName("sale_metadata") var saleMetaData: SaleMetaData? = SaleMetaData()
) : Detail