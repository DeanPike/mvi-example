package au.com.deanpike.data.converter

import au.com.deanpike.client.model.listing.response.ListingType
import javax.inject.Inject

internal interface ListingConverterFactory {
    fun getConverter(listingType: ListingType): ListingConverter?
}

internal class ListingConverterFactoryImpl @Inject constructor(
    private val propertyConverter: PropertyConverter,
    private val projectConverter: ProjectConverter
) : ListingConverterFactory {
    override fun getConverter(listingType: ListingType): ListingConverter? {
        return when (listingType) {
            ListingType.PROPERTY -> propertyConverter
            ListingType.PROJECT -> projectConverter
            else -> null
        }
    }
}