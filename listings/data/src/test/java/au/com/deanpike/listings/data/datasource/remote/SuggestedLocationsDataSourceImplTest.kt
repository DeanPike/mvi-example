package au.com.deanpike.listings.data.datasource.remote

import au.com.deanpike.commonshared.util.ResponseWrapper
import au.com.deanpike.network.api.SuggestedLocationsApi
import au.com.deanpike.network.model.external.suggestedlocation.SuggestedLocation
import au.com.deanpike.network.model.external.suggestedlocation.SuggestedLocations
import io.mockk.coEvery
import io.mockk.mockk
import java.io.IOException
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SuggestedLocationsDataSourceImplTest {
    private val api: SuggestedLocationsApi = mockk()
    private lateinit var dataSource: SuggestedLocationsDataSource

    @BeforeEach
    fun setupTest() {
        dataSource = SuggestedLocationsDataSourceImpl(
            api = api
        )
    }

    @Test
    fun `should get suggested locations`() = runTest {
        coEvery {
            api.getSuggestedLocations(
                contentType = "application/json",
                searchText = "Bondi"
            )
        } returns getResponse()

        val response = dataSource.getSuggestedLocations(location = "Bondi")

        assertThat(response).isInstanceOf(ResponseWrapper::class.java)
        val data = (response as ResponseWrapper.Success).data

        assertThat(data.suggestedLocations.size).isEqualTo(1)

        with(data.suggestedLocations[0]) {
            assertThat(displayName).isEqualTo("Bondi Beach, NSW 2026")
            assertThat(name).isEqualTo("Bondi Beach")
            assertThat(state).isEqualTo("NSW")
            assertThat(regionName).isEqualTo("Sydney")
            assertThat(areaName).isEqualTo("Eastern Suburbs")
            assertThat(postCode).isEqualTo("2026")
            assertThat(suburbId).isEqualTo("12345")
            assertThat(nameSlug).isEqualTo("bondi-beach-nsw-2026")
            assertThat(category).isEqualTo("Suburb")
            assertThat(group).isEqualTo("Suburbs")
        }
    }

    @Test
    fun `should handle exception`() = runTest {
        coEvery {
            api.getSuggestedLocations(
                contentType = "application/json",
                searchText = "Bondi"
            )
        } throws IOException("No internet")

        val response = dataSource.getSuggestedLocations(location = "Bondi")

        assertThat(response).isInstanceOf(ResponseWrapper.Error::class.java)
        val error = response as ResponseWrapper.Error
        assertThat(error.exception).isInstanceOf(IOException::class.java)
        assertThat(error.exception.message).isEqualTo("No internet")
    }

    private fun getResponse(): SuggestedLocations {
        val suggestedLocation = SuggestedLocation(
            displayName = "Bondi Beach, NSW 2026",
            name = "Bondi Beach",
            state = "NSW",
            regionName = "Sydney",
            areaName = "Eastern Suburbs",
            postCode = "2026",
            suburbId = "12345",
            nameSlug = "bondi-beach-nsw-2026",
            category = "Suburb",
            group = "Suburbs"
        )

        return SuggestedLocations(
            suggestedLocations = listOf(suggestedLocation)
        )
    }
}
