package au.com.deanpike.listings.ui.search

import au.com.deanpike.commonshared.util.ResponseWrapper
import au.com.deanpike.datashared.dispatcher.DispatcherProvider
import au.com.deanpike.listings.client.model.suggestedlocation.Location
import au.com.deanpike.listings.client.type.DwellingType
import au.com.deanpike.listings.client.type.StatusType
import au.com.deanpike.listings.client.usecase.SuggestedLocationsUseCase
import au.com.deanpike.testshared.extension.TestDispatcherExtension
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(TestDispatcherExtension::class)
class SearchViewModelTest {

    private val useCase: SuggestedLocationsUseCase = mockk()

    private lateinit var viewModel: SearchViewModel

    @BeforeEach
    fun setupTest(dispatcherProvider: DispatcherProvider) {
        viewModel = SearchViewModel(
            dispatcher = dispatcherProvider,
            suggestedLocationsUseCase = useCase
        )
    }

    @Test
    fun `should have default initial state`() {
        with(viewModel.uiState) {
            assertThat(location).isEqualTo("")
            assertThat(suggestedLocations).isEmpty()
            assertThat(selectedStatus).isEqualTo(StatusType.BUY)
            assertThat(selectedDwellingTypes).isEqualTo(listOf(DwellingType.ALL))
        }
    }

    @Test
    fun `should update location and fetch suggestions when location changed`() = runTest {
        val locations = listOf(Location(nameSlug = "sydney-nsw", displayName = "Sydney, NSW"))
        coEvery { useCase.getSuggestedLocations("Sydney") } returns ResponseWrapper.Success(locations)

        viewModel.setEvent(SearchScreenEvent.OnLocationChanged("Sydney"))
        advanceUntilIdle()

        with(viewModel.uiState) {
            assertThat(location).isEqualTo("Sydney")
            assertThat(suggestedLocations).isEqualTo(locations)
        }
    }

    @Test
    fun `should clear suggestions when location changed to blank`() = runTest {
        val locations = listOf(Location(nameSlug = "sydney-nsw", displayName = "Sydney, NSW"))
        coEvery { useCase.getSuggestedLocations("Sydney") } returns ResponseWrapper.Success(locations)

        viewModel.setEvent(SearchScreenEvent.OnLocationChanged("Sydney"))
        advanceUntilIdle()
        assertThat(viewModel.uiState.suggestedLocations).isEqualTo(locations)

        viewModel.setEvent(SearchScreenEvent.OnLocationChanged(""))
        advanceUntilIdle()

        with(viewModel.uiState) {
            assertThat(location).isEqualTo("")
            assertThat(suggestedLocations).isEmpty()
        }
        coVerify(exactly = 1) { useCase.getSuggestedLocations(any()) }
    }

    @Test
    fun `should clear suggestions on error`() = runTest {
        coEvery { useCase.getSuggestedLocations("Sydney") } returns ResponseWrapper.Error(IOException("No Internet"))

        viewModel.setEvent(SearchScreenEvent.OnLocationChanged("Sydney"))
        advanceUntilIdle()

        with(viewModel.uiState) {
            assertThat(location).isEqualTo("Sydney")
            assertThat(suggestedLocations).isEmpty()
        }
    }

    @Test
    fun `should not fetch suggestions again for the same location`() = runTest {
        val locations = listOf(Location(nameSlug = "sydney-nsw", displayName = "Sydney, NSW"))
        coEvery { useCase.getSuggestedLocations("Sydney") } returns ResponseWrapper.Success(locations)

        viewModel.setEvent(SearchScreenEvent.OnLocationChanged("Sydney"))
        advanceUntilIdle()
        viewModel.setEvent(SearchScreenEvent.OnLocationChanged("Sydney"))
        advanceUntilIdle()

        coVerify(exactly = 1) { useCase.getSuggestedLocations("Sydney") }
    }

    @Test
    fun `should select location and clear suggestions`() = runTest {
        val locations = listOf(Location(nameSlug = "sydney-nsw", displayName = "Sydney, NSW"))
        coEvery { useCase.getSuggestedLocations("Sydney") } returns ResponseWrapper.Success(locations)

        viewModel.setEvent(SearchScreenEvent.OnLocationChanged("Sydney"))
        advanceUntilIdle()
        assertThat(viewModel.uiState.suggestedLocations).isEqualTo(locations)

        viewModel.setEvent(SearchScreenEvent.OnLocationSelected("Sydney, NSW"))
        advanceUntilIdle()

        with(viewModel.uiState) {
            assertThat(location).isEqualTo("Sydney, NSW")
            assertThat(suggestedLocations).isEmpty()
        }
    }

    @Test
    fun `should update selected status`() = runTest {
        viewModel.setEvent(SearchScreenEvent.OnStatusChanged(StatusType.RENT))
        advanceUntilIdle()

        assertThat(viewModel.uiState.selectedStatus).isEqualTo(StatusType.RENT)
    }

    @Test
    fun `should update selected dwelling types`() = runTest {
        viewModel.setEvent(
            SearchScreenEvent.OnDwellingTypesChanged(listOf(DwellingType.HOUSE, DwellingType.TOWNHOUSE))
        )
        advanceUntilIdle()

        assertThat(viewModel.uiState.selectedDwellingTypes)
            .isEqualTo(listOf(DwellingType.HOUSE, DwellingType.TOWNHOUSE))
    }

    @Test
    fun `should emit search requested effect with current state when search clicked`() = runTest {
        viewModel.setEvent(SearchScreenEvent.OnStatusChanged(StatusType.RENT))
        viewModel.setEvent(
            SearchScreenEvent.OnDwellingTypesChanged(listOf(DwellingType.HOUSE))
        )
        viewModel.setEvent(SearchScreenEvent.OnLocationSelected("Sydney, NSW"))
        advanceUntilIdle()

        viewModel.setEvent(SearchScreenEvent.OnSearchClicked)
        advanceUntilIdle()

        val effect = viewModel.effect.first() as SearchScreenEffect.OnSearchRequested
        assertThat(effect.location).isEqualTo("Sydney, NSW")
        assertThat(effect.status).isEqualTo(StatusType.RENT)
        assertThat(effect.dwellingTypes).isEqualTo(listOf(DwellingType.HOUSE))
    }
}
