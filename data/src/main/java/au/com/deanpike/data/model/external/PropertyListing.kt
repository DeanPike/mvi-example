package au.com.deanpike.data.model.external

import com.google.gson.annotations.SerializedName

internal data class PropertyListing(

    @SerializedName("listing_type") var listingType: String? = null,
    @SerializedName("id") var id: Int? = null,
    @SerializedName("date_listed") var dateListed: String? = null,
    @SerializedName("address") var address: String? = null,
    @SerializedName("price") var price: String? = null,
    @SerializedName("media") var media: ArrayList<Media> = arrayListOf(),
    @SerializedName("bedroom_count") var bedroomCount: Int? = null,
    @SerializedName("bathroom_count") var bathroomCount: Int? = null,
    @SerializedName("homepass_enabled") var homepassEnabled: Boolean? = null,
    @SerializedName("additional_features") var additionalFeatures: ArrayList<String> = arrayListOf(),
    @SerializedName("geo_location") var geoLocation: GeoLocation? = GeoLocation(),
    @SerializedName("promo_level") var promoLevel: String? = null,
    @SerializedName("carspace_count") var carspaceCount: Int? = null,
    @SerializedName("dwelling_type") var dwellingType: String? = null,
    @SerializedName("has_video") var hasVideo: Boolean? = null,
    @SerializedName("headline") var headline: String? = null,
    @SerializedName("land_area") var landArea: String? = null,
    @SerializedName("large_land") var largeLand: Boolean? = null,
    @SerializedName("metadata") var metadata: Metadata? = Metadata(),
    @SerializedName("advertiser") var advertiser: Advertiser? = Advertiser(),
    @SerializedName("auction_date") var auctionDate: String? = null,
    @SerializedName("topspot") var topspot: Topspot? = Topspot()

)