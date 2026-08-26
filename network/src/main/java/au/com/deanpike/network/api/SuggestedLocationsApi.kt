package au.com.deanpike.network.api

import au.com.deanpike.network.model.external.suggestedlocation.SuggestedLocations
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface SuggestedLocationsApi {
    @GET("v1/locations/suggestlocations")
    suspend fun getSuggestedLocations(
        @Header("context-type") contentType: String,
        @Query("prefixText") searchText: String
    ): SuggestedLocations
}