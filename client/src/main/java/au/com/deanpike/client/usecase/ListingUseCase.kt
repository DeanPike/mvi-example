package au.com.deanpike.client.usecase

import au.com.deanpike.client.model.listing.response.Listing
import au.com.deanpike.client.model.listing.search.ListingSearch
import au.com.deanpike.client.util.ResponseWrapper

interface ListingUseCase {
    suspend fun getListings(search: ListingSearch): ResponseWrapper<List<Listing>>
}