package au.com.deanpike.detail.client.model.detail

import au.com.deanpike.datashared.type.ListingType

data class PropertyDetail(
    override val id: Int?,
    override val listingType: ListingType,
    override val address: String?,
    val headline: String?,
    val description: String?,
    val lifecycleStatus: String?,
    val media: List<Media>,
    val advertiser: Advertiser?,
    val schools: List<School>,
    val dwellingType: String?,
    val price: String?,
    val bedroomCount: Int?,
    val bathroomCount: Int?,
    val carSpaceCount: Int?,
    val dateSold: String?,
    val saleType: String?
) : ListingDetail