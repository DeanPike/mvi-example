package au.com.deanpike.data.api

import au.com.deanpike.data.model.external.ListingResponse
import au.com.deanpike.data.model.internal.ListingSearchRequest
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

internal interface PropertyListingApi {
    @POST("v1/search")
    suspend fun getListings(
        @Header("context-type") contentType: String,
        @Body body: ListingSearchRequest
    ): ListingResponse
}