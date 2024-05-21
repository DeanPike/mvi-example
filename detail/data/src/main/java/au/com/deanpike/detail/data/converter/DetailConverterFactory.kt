package au.com.deanpike.detail.data.converter

import au.com.deanpike.datashared.type.ListingType
import javax.inject.Inject

internal interface DetailConverterFactory {
    fun getConverter(listingType: ListingType): DetailConverter?
}

internal class DetailConverterFactoryImpl @Inject constructor(
    private val propertyConverter: PropertyConverter,
    private val projectConverter: ProjectConverter

) : DetailConverterFactory {
    override fun getConverter(listingType: ListingType): DetailConverter? {
        return when (listingType) {
            ListingType.PROPERTY -> propertyConverter
            ListingType.PROJECT -> projectConverter
            else -> null
        }
    }
}