package au.com.deanpike.listings.data.repository

import au.com.deanpike.commonshared.util.ResponseWrapper
import au.com.deanpike.listings.client.model.suggestedlocation.Location
import au.com.deanpike.listings.data.converter.SuggestedLocationConverter
import au.com.deanpike.listings.data.datasource.remote.SuggestedLocationsDataSource
import au.com.deanpike.network.model.external.suggestedlocation.SuggestedLocation
import au.com.deanpike.network.model.external.suggestedlocation.SuggestedLocations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import java.io.IOException
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class SuggestedLocationsRepositoryImplTest {
    private val dataSource: SuggestedLocationsDataSource = mockk()
    private val converter: SuggestedLocationConverter = mockk()

    private lateinit var repo: SuggestedLocationsRepository

    @BeforeEach
    fun setupTest() {
        repo = SuggestedLocationsRepositoryImpl(
            dataSource = dataSource,
            converter = converter
        )
    }

    @Test
    fun `should convert suggested locations`() = runTest {
        val suggestedLocation = getSuggestedLocation()

        coEvery {
            dataSource.getSuggestedLocations(location = "Bondi")
        } returns ResponseWrapper.Success(
            SuggestedLocations(
                suggestedLocations = listOf(suggestedLocation)
            )
        )

        every { converter.convertSuggestedLocation(suggestedLocation) } returns Location(
            nameSlug = "bondi-beach-nsw-2026",
            displayName = "Bondi Beach, NSW 2026"
        )

        val locations = repo.getSuggestedLocations(location = "Bondi")

        assertThat(locations).isInstanceOf(ResponseWrapper.Success::class.java)
        val success = locations as ResponseWrapper.Success
        assertThat(success.data.size).isEqualTo(1)

        with(success.data[0]) {
            assertThat(nameSlug).isEqualTo("bondi-beach-nsw-2026")
            assertThat(displayName).isEqualTo("Bondi Beach, NSW 2026")
        }
    }

    @Test
    fun `should handle error`() = runTest {
        coEvery {
            dataSource.getSuggestedLocations(location = "Bondi")
        } returns ResponseWrapper.Error(IOException("No internet"))

        val locations = repo.getSuggestedLocations(location = "Bondi")

        assertThat(locations).isInstanceOf(ResponseWrapper.Error::class.java)
        val error = locations as ResponseWrapper.Error
        assertThat(error.exception).isInstanceOf(IOException::class.java)
        assertThat(error.exception.message).isEqualTo("No internet")
    }

    private fun getSuggestedLocation(): SuggestedLocation {
        return SuggestedLocation(
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
    }
}
