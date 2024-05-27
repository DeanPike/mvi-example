package au.com.deanpike.network.model.external.detail

import au.com.deanpike.network.model.external.listing.GeoLocation
import au.com.deanpike.network.model.external.listing.TrackingMetadata
import com.google.gson.annotations.SerializedName

data class ProjectDetail(
    override var id: Int,
    val headline: String? = null,
    @SerializedName("end_date")
    val endDate: String? = null,
    val description: String? = null,
    val address: String? = null,
    @SerializedName("listing_type")
    val listingType: String? = null,
    val media: List<Media> = emptyList(),
    val advertiser: Advertiser?,
    @SerializedName("additional_features")
    val additionalFeatures: List<String> = emptyList(),
    @SerializedName("geo_location")
    val geoLocation: GeoLocation? = null,
    val schools: List<School> = emptyList(),
    @SerializedName("inspection_schedule")
    val inspectionSchedule: InspectionSchedule? = null,
    @SerializedName("dwelling_type")
    val dwellingType: String? = null,
    @SerializedName("phone_enquiry_preference")
    val phoneEnquiryPreference: Boolean? = null,
    @SerializedName("inspection_metadata")
    val inspectionMetadata: InspectionMetadata? = null,
    @SerializedName("tracking_metadata")
    val trackingMetadata: TrackingMetadata? = null,
    @SerializedName("project_brochure_url")
    val projectBrochureUrl: String? = null,
    @SerializedName("project_website_url")
    val projectWebsiteUrl: String? = null,
    @SerializedName("child_listings")
    val childListings: List<ChildListing> = emptyList(),
    @SerializedName("project_name")
    val projectName: String? = null,
    @SerializedName("project_category")
    val projectCategory: String? = null,
    val priceRange: PriceRange? = null,
    @SerializedName("completion_date")
    val completionDate: CompletionDate? = null,
    @SerializedName("total_number_residences")
    val totalNumberResidences: Long? = null,
    @SerializedName("project_color_hex")
    val projectColorHex: String? = null,
    @SerializedName("project_logo_image_url")
    val projectLogoImageUrl: String? = null,
    @SerializedName("is_premium_project")
    val isPremiumProject: Boolean? = null,
    @SerializedName("showroom_geo_location")
    val showroomGeoLocation: GeoLocation? = null,
    @SerializedName("showroom_address")
    val showroomAddress: String? = null,
): Detail
