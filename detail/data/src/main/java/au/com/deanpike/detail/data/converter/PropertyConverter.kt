package au.com.deanpike.detail.data.converter

import au.com.deanpike.datashared.type.ListingType
import au.com.deanpike.detail.data.converter.ConverterShared.advertiserConverter
import au.com.deanpike.detail.data.converter.ConverterShared.mediaConverter
import au.com.deanpike.detail.data.converter.ConverterShared.schoolConverter
import au.com.deanpike.network.model.external.detail.PropertyDetail

object PropertyConverter {
    fun convertDetail(detail: PropertyDetail): au.com.deanpike.detail.client.model.detail.PropertyDetail {
        return au.com.deanpike.detail.client.model.detail.PropertyDetail(
            id = detail.id,
            listingType = ListingType.PROPERTY,
            address = detail.address,
            headline = detail.headline,
            description = detail.description,
            lifecycleStatus = detail.lifecycleStatus,
            media = detail.media?.let { mediaConverter(it) } ?: emptyList(),
            advertiser = detail.advertiser?.let { advertiserConverter(it) },
            schools = schoolConverter(detail.schools),
            dwellingType = detail.dwellingType,
            price = detail.price,
            bedroomCount = detail.bedroomCount,
            bathroomCount = detail.bathroomCount,
            carSpaceCount = detail.carspaceCount,
            dateSold = detail.saleMetaData?.dateSold,
            saleType = detail.saleMetaData?.saleType
        )
    }
}