package au.com.deanpike.network.api

import au.com.deanpike.network.model.external.property.PropertyDetail
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface PropertyDetailsAPI {
    @GET("v1/property-details/{id}")
    suspend fun getPropertyDetails(
        @Header("context-type") contentType: String,
        @Path("id") id: String
    ): PropertyDetail
}