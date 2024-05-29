package au.com.deanpike.detail.data.usecase

import au.com.deanpike.commonshared.util.ResponseWrapper
import au.com.deanpike.datashared.type.ListingType
import au.com.deanpike.detail.client.model.detail.ProjectDetail
import au.com.deanpike.detail.client.usecase.ProjectDetailUseCase
import au.com.deanpike.detail.data.repository.ProjectDetailRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ProjectDetailUseCaseImplTest {
    private val repo: ProjectDetailRepository = mockk()
    private lateinit var useCase: ProjectDetailUseCase

    @BeforeEach
    fun setupTest() {
        useCase = ProjectDetailUseCaseImpl(
            repo = repo
        )
    }

    @Test
    fun `should get listing details`() = runTest {
        coEvery { repo.getDetails(1234) } returns ResponseWrapper.Success(
            ProjectDetail(
                id = 1234,
                listingType = ListingType.PROJECT
            )
        )

        val details = useCase.getProjectDetails(1234)

        assertThat(details).isInstanceOf(ResponseWrapper.Success::class.java)
        val data = (details as ResponseWrapper.Success).data
        assertThat(data).isInstanceOf(ProjectDetail::class.java)
        assertThat(data.id).isEqualTo(1234)
        assertThat(data.listingType).isEqualTo(ListingType.PROJECT)
    }
}