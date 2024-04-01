package au.com.deanpike.data.repository

import au.com.deanpike.client.model.listing.response.Listing
import au.com.deanpike.data.converter.ListingConverterFactory
import au.com.deanpike.data.datasource.remote.ListingDataSource
import au.com.deanpike.data.model.internal.ListingSearchRequest
import au.com.deanpike.datashared.util.ResponseWrapper
import javax.inject.Inject

internal interface ListingRepository {
    suspend fun getListings(request: ListingSearchRequest): ResponseWrapper<List<Listing>>
}

internal class ListingRepositoryImpl @Inject constructor(
    private val dataSource: ListingDataSource,
    private val converterFactory: ListingConverterFactory
) : ListingRepository {
    override suspend fun getListings(request: ListingSearchRequest): ResponseWrapper<List<Listing>> {
        when (val response = dataSource.getListings(request)) {
            is ResponseWrapper.Success -> {
                val listings = mutableListOf<Listing>()
                val data = response.data.searchResults
                data.forEach { searchResult ->
                    converterFactory.getConverter(searchResult.listingType)?.convertListing(searchResult)?.let { listing ->
                        listings.add(listing)
                    }

                }
                return ResponseWrapper.Success(listings)
            }
            is ResponseWrapper.Error -> {
                return ResponseWrapper.Error(response.exception)
            }
        }
    }
}