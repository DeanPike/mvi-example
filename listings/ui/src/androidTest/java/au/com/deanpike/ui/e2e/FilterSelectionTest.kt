//package au.com.deanpike.ui.e2e
//
//import au.com.deanpike.ui.screen.list.ListingListScreen
//import au.com.deanpike.uishared.theme.MviExampleTheme
//import au.com.deanpike.uitestshared.HiltTestActivity
//import au.com.deanpike.uitestshared.base.UiE2ETestBase
//import au.com.deanpike.uitestshared.mockserver.HttpMethod
//import au.com.deanpike.uitestshared.util.advanceTimeAndWait
//import au.com.deanpike.uitestshared.util.waitUntilTagExists
//import dagger.hilt.android.testing.HiltAndroidTest
//import java.net.HttpURLConnection
//import org.junit.Rule
//import org.junit.Test
//
//@HiltAndroidTest
//class FilterSelectionTest : UiE2ETestBase() {
//    @get:Rule(order = 1)
//    val composeTestRule = createComposeRuleFor(HiltTestActivity::class.java)
//
//    private val filterScreen = FilterComponentScreen(composeTestRule)
//
//    @Test
//    fun should_show_status_and_listing_types() {
//        setupResponse(
//            listingType = emptyList(),
//            statusType = "buy"
//        )
//
//        with(composeTestRule) {
//            setContent {
//                MviExampleTheme {
//                    ListingListScreen()
//                }
//            }
//            advanceTimeAndWait()
//
//            waitUntilTagExists(tag = au.com.deanpike.ui.screen.list.ListingListScreenTestTags.LISTING_LIST, timeout = 2000)
//
//            with(filterScreen) {
//                assertFilterComponentDisplayed()
//                assertStatusItemsDisplayed()
//
//                setupResponse(
//                    listingType = emptyList(),
//                    statusType = "sold"
//                )
//                selectSoldStatus()
//
//                assertListingTypesDisplayed()
//
//                setupResponse(
//                    listingType = listOf("House"),
//                    statusType = "sold"
//                )
//                selectHouseListingType()
//            }
//        }
//    }
//
//    private fun setupResponse(listingType: List<String>, statusType: String) {
//
//        val listingTypes = listingType.map {
//            "\"$it\""
//        }.toString()
//
//        webServerDispatcher.addResponse(
//            context = context,
//            pathPattern = "v1/search",
//            filename = "raw/listing_response.json",
//            httpMethod = HttpMethod.POST,
//            status = HttpURLConnection.HTTP_OK,
//            body = """{"dwelling_types":$listingTypes,"search_mode":"$statusType"}"""
//        )
//    }
//}