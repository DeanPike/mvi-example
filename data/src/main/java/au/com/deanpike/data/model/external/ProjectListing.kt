package au.com.deanpike.data.model.external

import com.google.gson.annotations.SerializedName

internal data class ExampleJson2KtKotlin(

    @SerializedName("listing_type") var listingType: String? = null,
    @SerializedName("id") var id: Int? = null,
    @SerializedName("address") var address: String? = null,
    @SerializedName("promo_level") var promoLevel: String? = null,
    @SerializedName("project") var project: Project? = Project(),
    @SerializedName("media") var media: ArrayList<Media> = arrayListOf(),
    @SerializedName("geo_location") var geoLocation: GeoLocation? = GeoLocation()

)