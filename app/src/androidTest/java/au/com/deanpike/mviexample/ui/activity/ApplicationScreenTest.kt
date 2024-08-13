package au.com.deanpike.mviexample.ui.activity

import au.com.deanpike.uitestshared.HiltTestActivity
import au.com.deanpike.uitestshared.base.UiE2ETestBase
import au.com.deanpike.uitestshared.mockserver.HttpMethod
import dagger.hilt.android.testing.HiltAndroidTest
import java.net.HttpURLConnection
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class ApplicationScreenTest : UiE2ETestBase() {
    @get:Rule(order = 1)
    val composeTestRule = createComposeRuleFor(HiltTestActivity::class.java)

    private val timeout = 5000L

    @Test
    fun testOne() {
        setupListingResponse(
            listingType = emptyList(),
            statusType = "buy"
        )

//        with(composeTestRule) {
//            setContent {
//                MviExampleTheme {
//                    ApplicationScreen()
//                }
//            }
//            advanceTimeAndWait()
//
//            waitUntilTagExists(tag = LISTING_LIST, timeout = timeout)
//        }
    }

    private fun setupListingResponse(listingType: List<String>, statusType: String) {

        val listingTypes = listingType.map {
            "\"$it\""
        }.toString()

        webServerDispatcher.addResponse(
            context = context,
            pathPattern = "v1/search",
            filename = "raw/listing_response.json",
            httpMethod = HttpMethod.POST,
            status = HttpURLConnection.HTTP_OK,
            body = """{"dwelling_types":$listingTypes,"search_mode":"$statusType"}"""
        )
    }
}