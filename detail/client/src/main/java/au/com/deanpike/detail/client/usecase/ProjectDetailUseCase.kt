package au.com.deanpike.detail.client.usecase

import au.com.deanpike.commonshared.util.ResponseWrapper
import au.com.deanpike.detail.client.model.detail.ProjectDetail

interface ProjectDetailUseCase {
    suspend fun getProjectDetails(id: Long): ResponseWrapper<ProjectDetail>
}