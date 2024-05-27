package au.com.deanpike.detail.client.model.detail

import au.com.deanpike.datashared.type.ListingType

data class ProjectDetail(
    override val id: Int?,
    override val listingType: ListingType,
    override val address: String? = null,
    val headline: String? = null,
    val description: String? = null,
    val media: List<Media> = emptyList(),
    val advertiser: Advertiser? = null,
    val schools: List<School> = emptyList(),
    val dwellingType: String? = null,
    val childListings: List<ProjectChild> = emptyList(),
    val projectName: String? = null,
    val projectColorHex: String? = null,
    val projectLogoImageUrl: String? = null,
    val showroomAddress: String? = null
) : ListingDetail
