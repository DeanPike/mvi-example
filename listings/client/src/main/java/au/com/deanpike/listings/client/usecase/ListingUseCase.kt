package au.com.deanpike.listings.client.usecase

import au.com.deanpike.listings.client.model.listing.response.Listing
import au.com.deanpike.listings.client.model.listing.search.ListingSearch
import au.com.deanpike.listings.client.util.ResponseWrapper

interface ListingUseCase {
    suspend fun getListings(search: ListingSearch): ResponseWrapper<List<Listing>>
}