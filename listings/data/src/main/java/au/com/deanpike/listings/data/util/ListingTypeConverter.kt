package au.com.deanpike.listings.data.util

import au.com.deanpike.datashared.type.ListingType

object ListingTypeConverter {
    fun getListingType(type: String): ListingType {
        return enumValues<ListingType>().first {
            it.value == type
        }
    }
}