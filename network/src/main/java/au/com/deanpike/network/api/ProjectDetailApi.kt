package au.com.deanpike.network.api

import au.com.deanpike.network.model.external.detail.ProjectDetail
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ProjectDetailApi {
    @GET("v1/project-details/{id}")
    suspend fun getProjectDetails(
        @Header("context-type") contentType: String,
        @Path("id") id: String
    ): ProjectDetail
}