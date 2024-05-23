package au.com.deanpike.detail.data.datasource.remote

import au.com.deanpike.commonshared.util.ResponseWrapper
import au.com.deanpike.network.api.ProjectDetailApi
import au.com.deanpike.network.model.external.detail.ProjectDetail
import au.com.deanpike.network.util.DataSourceBase
import javax.inject.Inject

internal interface ProjectDetailDataSource {
    suspend fun getProjectDetails(id: Int): ResponseWrapper<ProjectDetail>
}

internal class ProjectDetailDataSourceImpl @Inject constructor(
    private val api: ProjectDetailApi
) : ProjectDetailDataSource, DataSourceBase() {
    override suspend fun getProjectDetails(id: Int): ResponseWrapper<ProjectDetail> {
        return safeApiCall {
            api.getProjectDetails(
                contentType = "application/json",
                id = id.toString()
            )
        }
    }
}