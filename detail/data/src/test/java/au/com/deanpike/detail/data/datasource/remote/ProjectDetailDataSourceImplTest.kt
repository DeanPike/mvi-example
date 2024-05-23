package au.com.deanpike.detail.data.datasource.remote

import au.com.deanpike.commonshared.util.ResponseWrapper
import au.com.deanpike.network.api.ProjectDetailApi
import au.com.deanpike.network.model.external.detail.ProjectDetail
import com.google.gson.Gson
import io.mockk.coEvery
import io.mockk.mockk
import java.io.IOException
import java.io.InputStreamReader
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ProjectDetailDataSourceImplTest {
    private val api: ProjectDetailApi = mockk()
    private lateinit var dataSource: ProjectDetailDataSource
    private val gson = Gson()

    @BeforeEach
    fun setupTest() {
        dataSource = ProjectDetailDataSourceImpl(
            api = api
        )
    }

    @Test
    fun `should get details`() = runTest {
        coEvery {
            api.getProjectDetails(
                contentType = "application/json",
                id = "6303"
            )
        } returns getResponse()

        val response = dataSource.getProjectDetails(6303)

        assertThat(response).isInstanceOf(ResponseWrapper.Success::class.java)
        val data = (response as ResponseWrapper.Success).data
        assertThat(data.id).isEqualTo(6303)
    }

    @Test
    fun `should return an error`() = runTest {
        coEvery {
            api.getProjectDetails(
                contentType = "application/json",
                id = "1468"
            )
        } throws IOException("Error")

        val response = dataSource.getProjectDetails(1468)

        assertThat(response).isInstanceOf(ResponseWrapper.Error::class.java)
    }

    private fun getResponse(): ProjectDetail {
        val data = ClassLoader.getSystemResourceAsStream("raw/project_details.json")?.let { InputStreamReader(it, "UTF-8").readText() }!!
        return gson.fromJson(data, ProjectDetail::class.java)
    }
}