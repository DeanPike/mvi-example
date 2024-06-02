package au.com.deanpike.detail.client.model.detail

import au.com.deanpike.datashared.type.ListingType

data class PropertyDetail(
    override val id: Long,
    override val listingType: ListingType,
    override val address: String? = null,
    val headline: String? = null,
    val description: String? = null,
    val lifecycleStatus: String? = null,
    val media: List<Media> = emptyList(),
    val advertiser: Advertiser? = null,
    val schools: List<School> = emptyList(),
    val dwellingType: String? = null,
    val price: String? = null,
    val bedroomCount: Int? = null,
    val bathroomCount: Int? = null,
    val carSpaceCount: Int? = null,
    val dateSold: String? = null,
    val saleType: String? = null
) : ListingDetail