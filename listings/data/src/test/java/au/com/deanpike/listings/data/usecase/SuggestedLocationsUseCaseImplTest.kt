package au.com.deanpike.listings.data.usecase

import au.com.deanpike.commonshared.util.ResponseWrapper
import au.com.deanpike.listings.client.model.suggestedlocation.Location
import au.com.deanpike.listings.client.usecase.SuggestedLocationsUseCase
import au.com.deanpike.listings.data.repository.SuggestedLocationsRepository
import io.mockk.coEvery
import io.mockk.mockk
import java.io.IOException
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SuggestedLocationsUseCaseImplTest {
    private val repo: SuggestedLocationsRepository = mockk()
    private lateinit var useCase: SuggestedLocationsUseCase

    @BeforeEach
    fun setupTest() {
        useCase = SuggestedLocationsUseCaseImpl(
            repo = repo
        )
    }

    @Test
    fun `get suggested locations`() = runTest {
        val location = Location(
            nameSlug = "bondi-beach-nsw-2026",
            displayName = "Bondi Beach, NSW 2026"
        )

        coEvery {
            repo.getSuggestedLocations(location = "Bondi")
        } returns ResponseWrapper.Success(
            listOf(location)
        )

        val response = useCase.getSuggestedLocations(location = "Bondi")

        assertThat(response).isInstanceOf(ResponseWrapper.Success::class.java)
        val data = (response as ResponseWrapper.Success).data
        assertThat(data.size).isEqualTo(1)
        assertThat(data[0]).isEqualTo(location)
    }

    @Test
    fun `should handle error`() = runTest {
        coEvery {
            repo.getSuggestedLocations(location = "Bondi")
        } returns ResponseWrapper.Error(IOException("No internet"))

        val response = useCase.getSuggestedLocations(location = "Bondi")

        assertThat(response).isInstanceOf(ResponseWrapper.Error::class.java)
        val error = response as ResponseWrapper.Error
        assertThat(error.exception).isInstanceOf(IOException::class.java)
        assertThat(error.exception.message).isEqualTo("No internet")
    }
}
