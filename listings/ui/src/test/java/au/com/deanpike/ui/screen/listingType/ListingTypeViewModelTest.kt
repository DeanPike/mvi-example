package au.com.deanpike.ui.screen.listingType

import app.cash.turbine.test
import au.com.deanpike.listings.client.type.ListingType
import au.com.deanpike.datashared.dispatcher.DispatcherProvider
import au.com.deanpike.testshared.extension.TestDispatcherExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(TestDispatcherExtension::class)
class ListingTypeViewModelTest {
    private lateinit var viewModel: ListingTypeViewModel

    @BeforeEach
    fun setupTest(dispatcherProvider: DispatcherProvider) {
        viewModel = ListingTypeViewModel()
    }

    @Test
    fun `should initialise viewmodel`() = runTest {
        viewModel.setEvent(
            ListingTypeEvent.Initialise(
                listOf(
                    ListingType.HOUSE,
                    ListingType.TOWNHOUSE
                )
            )
        )
        advanceUntilIdle()

        with(viewModel.uiState) {
            assertThat(selectedListingTypes.count()).isEqualTo(2)
            assertThat(selectedListingTypes[0]).isEqualTo(ListingType.HOUSE)
            assertThat(selectedListingTypes[1]).isEqualTo(ListingType.TOWNHOUSE)
        }
    }

    @Test
    fun `handle item selected`() = runTest {
        assertThat(viewModel.uiState.selectedListingTypes.isEmpty()).isTrue()

        // Add first item
        viewModel.setEvent(ListingTypeEvent.OnItemSelected(ListingType.TOWNHOUSE, true))
        advanceUntilIdle()

        assertThat(viewModel.uiState.selectedListingTypes.count()).isEqualTo(1)
        assertThat(viewModel.uiState.selectedListingTypes[0]).isEqualTo(ListingType.TOWNHOUSE)

        // Add second item
        viewModel.setEvent(ListingTypeEvent.OnItemSelected(ListingType.HOUSE, true))
        advanceUntilIdle()

        assertThat(viewModel.uiState.selectedListingTypes.count()).isEqualTo(2)
        assertThat(viewModel.uiState.selectedListingTypes[1]).isEqualTo(ListingType.HOUSE)

        // Remove first item
        viewModel.setEvent(ListingTypeEvent.OnItemSelected(ListingType.TOWNHOUSE, false))
        advanceUntilIdle()

        assertThat(viewModel.uiState.selectedListingTypes.count()).isEqualTo(1)
        assertThat(viewModel.uiState.selectedListingTypes[0]).isEqualTo(ListingType.HOUSE)

        // Clear list
        viewModel.setEvent(ListingTypeEvent.OnItemSelected(ListingType.ALL, true))
        advanceUntilIdle()

        assertThat(viewModel.uiState.selectedListingTypes.isEmpty()).isTrue()
    }

    @Test
    fun `handle apply clicked`() = runTest {
        viewModel.setEvent(ListingTypeEvent.OnItemSelected(ListingType.TOWNHOUSE, true))
        viewModel.setEvent(ListingTypeEvent.OnItemSelected(ListingType.HOUSE, true))
        advanceUntilIdle()

        viewModel.setEvent(ListingTypeEvent.OnApplyClicked)
        advanceUntilIdle()

        viewModel.effect.test {
            val item = awaitItem()
            assertThat(item).isInstanceOf(ListingTypeEffect.OnApplyClicked::class.java)

            val listingTypes = (item as ListingTypeEffect.OnApplyClicked).listingTypes
            assertThat(listingTypes.count()).isEqualTo(2)
            assertThat(listingTypes[0]).isEqualTo(ListingType.TOWNHOUSE)
            assertThat(listingTypes[1]).isEqualTo(ListingType.HOUSE)
        }
    }
}