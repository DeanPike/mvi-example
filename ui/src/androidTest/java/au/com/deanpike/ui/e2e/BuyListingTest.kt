package au.com.deanpike.ui.e2e

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.test.platform.app.InstrumentationRegistry
import au.com.deanpike.network.api.PropertyListingApi
import au.com.deanpike.ui.screen.list.ListingListScreen
import au.com.deanpike.ui.screen.list.ListingListViewModel
import au.com.deanpike.uishared.theme.MviExampleTheme
import au.com.deanpike.uitestshared.base.UiE2ETestBase
import au.com.deanpike.uitestshared.mockserver.MockWebServerDispatcher
import dagger.hilt.android.testing.HiltAndroidTest
import java.io.InputStreamReader
import javax.inject.Inject
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

@HiltAndroidTest
class BuyListingTest : UiE2ETestBase() {

    @Inject
    lateinit var api: PropertyListingApi

    @Test
    fun test_buy_listing_flow() = runTest {
        lateinit var listingViewModel: ListingListViewModel
        with(composeTestRule) {
            setContent {
                listingViewModel = viewModel()
                MviExampleTheme {
                    ListingListScreen(
                        viewModel = listingViewModel
                    )
                }
            }
            advanceTimeAndWait(this, 2000)
        }

        assertThat(listingViewModel.uiState.listings).isNotEmpty

    }

    override fun setupResponses() {
        setupListingResponse()
    }

    private fun setupListingResponse() {
        val requestDispatcher = MockWebServerDispatcher.RequestDispatcher(readFile())
        mockServer.dispatcher = requestDispatcher
    }

    private fun readFile(): String {
        return InstrumentationRegistry.getInstrumentation().context.classLoader.getResourceAsStream("raw/listing_response.json")?.let { InputStreamReader(it, "UTF-8").readText() }!!
    }
}