package au.com.deanpike.network.api

import au.com.deanpike.network.model.external.ListingResponse
import au.com.deanpike.network.model.internal.ListingSearchRequest
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface PropertyListingApi {
    @POST("v1/search")
    suspend fun getListings(
        @Header("context-type") contentType: String,
        @Body body: ListingSearchRequest
    ): ListingResponse
}