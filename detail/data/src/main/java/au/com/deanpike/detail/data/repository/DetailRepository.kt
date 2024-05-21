package au.com.deanpike.detail.data.repository

import au.com.deanpike.commonshared.util.ResponseWrapper
import au.com.deanpike.datashared.util.ListingTypeConverter.getListingType
import au.com.deanpike.detail.client.model.detail.ListingDetail
import au.com.deanpike.detail.data.converter.DetailConverterFactory
import au.com.deanpike.detail.data.datasource.remote.DetailDataSource
import javax.inject.Inject

internal interface DetailRepository {
    suspend fun getDetails(id: Int): ResponseWrapper<ListingDetail?>
}

internal class DetailRepositoryImpl @Inject constructor(
    private val dataSource: DetailDataSource,
    private val converterFactory: DetailConverterFactory
) : DetailRepository {
    override suspend fun getDetails(id: Int): ResponseWrapper<ListingDetail?> {
        when (val response = dataSource.getListingDetails(id)) {
            is ResponseWrapper.Success -> {
                val data = response.data
                data.listingType?.let { checkedListingType ->
                    val listingType = getListingType(checkedListingType)
                    listingType.let { type ->
                        converterFactory.getConverter(type)?.convertDetail(data)?.let { listing ->
                            return ResponseWrapper.Success(listing)
                        }
                    }
                }
                return ResponseWrapper.Success(null)
            }
            is ResponseWrapper.Error -> {
                return ResponseWrapper.Error(response.exception)
            }
        }
    }
}