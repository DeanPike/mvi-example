package au.com.deanpike.detail.data.usecase

import au.com.deanpike.commonshared.util.ResponseWrapper
import au.com.deanpike.detail.client.model.detail.ProjectDetail
import au.com.deanpike.detail.client.usecase.ProjectDetailUseCase
import au.com.deanpike.detail.data.repository.ProjectDetailRepository
import javax.inject.Inject

internal class ProjectDetailUseCaseImpl @Inject constructor(
    private val repo: ProjectDetailRepository
) : ProjectDetailUseCase {
    override suspend fun getProjectDetails(id: Long): ResponseWrapper<ProjectDetail> {
        return repo.getDetails(id)
    }
}