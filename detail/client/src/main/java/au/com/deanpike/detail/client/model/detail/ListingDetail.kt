package au.com.deanpike.detail.client.model.detail

import au.com.deanpike.datashared.type.ListingType

interface ListingDetail {
    val id: Int?
    val listingType: ListingType
    val address: String?
}