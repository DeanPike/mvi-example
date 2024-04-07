package au.com.deanpike.ui.screen.list

import androidx.lifecycle.viewmodel.compose.viewModel
import au.com.deanpike.network.api.PropertyListingApi
import au.com.deanpike.uishared.theme.MviExampleTheme
import au.com.deanpike.uitestshared.base.UiTestBase
import dagger.hilt.android.testing.HiltAndroidTest
import javax.inject.Inject
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test

@HiltAndroidTest
class ListingListScreenTest : UiTestBase() {

    lateinit var mockServer: MockWebServer

    @Inject
    lateinit var api: PropertyListingApi

    @Before
    fun setupTest() {
        hiltRule.inject()

        mockServer = MockWebServer()
        mockServer.start(8080)
    }

    @After
    fun tearDown() {
        mockServer.shutdown()
    }

    @Test
    fun show_listings() {
        with(composeTestRule) {
            setContent {
                MviExampleTheme {
                    ListingListScreen(
                        viewModel = viewModel()
                    )
                }
            }
            advanceTimeAndWait(this)
        }
    }
}