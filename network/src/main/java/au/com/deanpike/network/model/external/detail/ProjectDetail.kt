package au.com.deanpike.network.model.external.detail

import au.com.deanpike.network.model.external.listing.GeoLocation
import au.com.deanpike.network.model.external.listing.TrackingMetadata
import com.google.gson.annotations.SerializedName

data class ProjectDetail(
    override var id: Int,
    val headline: String,
    @SerializedName("end_date")
    val endDate: String,
    val description: String,
    val address: String,
    @SerializedName("listing_type")
    val listingType: String,
    val media: List<Media>,
    val advertiser: Advertiser,
    @SerializedName("additional_features")
    val additionalFeatures: List<String>,
    @SerializedName("geo_location")
    val geoLocation: GeoLocation,
    val schools: List<School>,
    @SerializedName("inspection_schedule")
    val inspectionSchedule: InspectionSchedule,
    @SerializedName("dwelling_type")
    val dwellingType: String,
    @SerializedName("phone_enquiry_preference")
    val phoneEnquiryPreference: Boolean,
    @SerializedName("inspection_metadata")
    val inspectionMetadata: InspectionMetadata,
    @SerializedName("tracking_metadata")
    val trackingMetadata: TrackingMetadata,
    @SerializedName("project_brochure_url")
    val projectBrochureUrl: String,
    @SerializedName("project_website_url")
    val projectWebsiteUrl: String,
    @SerializedName("child_listings")
    val childListings: List<ChildListing>,
    @SerializedName("project_name")
    val projectName: String,
    @SerializedName("project_category")
    val projectCategory: String,
    val priceRange: PriceRange,
    @SerializedName("completion_date")
    val completionDate: CompletionDate,
    @SerializedName("total_number_residences")
    val totalNumberResidences: Long,
    @SerializedName("project_color_hex")
    val projectColorHex: String,
    @SerializedName("project_logo_image_url")
    val projectLogoImageUrl: String,
    @SerializedName("is_premium_project")
    val isPremiumProject: Boolean,
    @SerializedName("showroom_geo_location")
    val showroomGeoLocation: GeoLocation,
    @SerializedName("showroom_address")
    val showroomAddress: String,
): Detail
