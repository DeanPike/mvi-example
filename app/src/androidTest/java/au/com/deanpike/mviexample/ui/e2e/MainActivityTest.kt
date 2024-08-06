package au.com.deanpike.mviexample.ui.e2e

import au.com.deanpike.detail.ui.project.ProjectChildComponentTestTags.PROJECT_CHILD_LAYOUT
import au.com.deanpike.detail.ui.project.ProjectChildrenComponentTestTags.PROJECT_CHILDREN
import au.com.deanpike.detail.ui.project.ProjectDetailScreenTestTags.PROJECT_DETAIL_CLOSE
import au.com.deanpike.detail.ui.project.ProjectDetailScreenTestTags.PROJECT_DETAIL_NAME
import au.com.deanpike.detail.ui.property.PropertyDetailScreenTestTags.PROPERTY_DETAIL_CLOSE
import au.com.deanpike.detail.ui.property.PropertyDetailScreenTestTags.PROPERTY_DETAIL_PRICE
import au.com.deanpike.mviexample.ui.activity.MainActivity
import au.com.deanpike.ui.screen.list.ListingListScreenTestTags.LISTING_LIST
import au.com.deanpike.ui.screen.list.component.ProjectChildListItemComponentTestTags.PROJECT_CHILD_LIST_ITEM_LAYOUT
import au.com.deanpike.ui.screen.list.component.ProjectListItemTesTags.PROJECT_LIST_ITEM_CHILD_COUNT
import au.com.deanpike.ui.screen.list.component.ProjectListItemTesTags.PROJECT_LIST_ITEM_LAYOUT
import au.com.deanpike.ui.screen.list.component.PropertyListItemTesTags.PROPERTY_LIST_ITEM_ADDRESS
import au.com.deanpike.uitestshared.base.UiE2ETestBase
import au.com.deanpike.uitestshared.mockserver.HttpMethod
import au.com.deanpike.uitestshared.util.assertTextDisplayed
import au.com.deanpike.uitestshared.util.clickOn
import au.com.deanpike.uitestshared.util.scrollToItemPosition
import au.com.deanpike.uitestshared.util.waitUntilTagExists
import dagger.hilt.android.testing.HiltAndroidTest
import java.net.HttpURLConnection
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class MainActivityTest : UiE2ETestBase() {

    @get:Rule(order = 1)
    val composeTestRule = createComposeRuleFor(MainActivity::class.java)

    private val timeout = 5000L

    @Test
    fun show_project_detail() {
        setupListingResponse(
            listingType = emptyList(),
            statusType = "buy"
        )

        with(composeTestRule) {
            waitUntilTagExists(tag = LISTING_LIST, timeout = timeout)

            setupProjectResponse()
            scrollToItemPosition(tag = LISTING_LIST, index = 0)
            clickOn("${PROJECT_LIST_ITEM_LAYOUT}_0")

            // Project detail screen
            waitUntilTagExists(PROJECT_DETAIL_NAME, timeout)
            assertTextDisplayed(PROJECT_DETAIL_NAME, "Easterly Wollongong")

            setupListingResponse(
                listingType = emptyList(),
                statusType = "buy"
            )
            clickOn(PROJECT_DETAIL_CLOSE, timeout)

            // Listing list screen
            waitUntilTagExists(tag = LISTING_LIST, timeout = timeout)
        }
    }

    @Test
    fun show_project_child_from_list() {
        setupListingResponse(
            listingType = emptyList(),
            statusType = "buy"
        )

        with(composeTestRule) {
            waitUntilTagExists(tag = LISTING_LIST, timeout = timeout)
            clickOn("${PROJECT_LIST_ITEM_CHILD_COUNT}_0", timeout)

            waitUntilTagExists("${PROJECT_CHILD_LIST_ITEM_LAYOUT}_0_0", timeout)

            setupProjectChildResponse()
            clickOn("${PROJECT_CHILD_LIST_ITEM_LAYOUT}_0_0")

            waitUntilTagExists(PROPERTY_DETAIL_PRICE)

            clickOn(PROPERTY_DETAIL_CLOSE)

            waitUntilTagExists(tag = LISTING_LIST, timeout = timeout)
        }
    }

    @Test
    fun show_project_child_from_project_details(){
        setupListingResponse(
            listingType = emptyList(),
            statusType = "buy"
        )

        with(composeTestRule) {
            waitUntilTagExists(tag = LISTING_LIST, timeout = timeout)

            setupProjectResponse()
            scrollToItemPosition(tag = LISTING_LIST, index = 0)
            clickOn("${PROJECT_LIST_ITEM_LAYOUT}_0")

            // Project detail screen
            waitUntilTagExists(PROJECT_DETAIL_NAME, timeout)

            setupProjectChildResponse()
            waitUntilTagExists(PROJECT_CHILDREN, timeout)
            scrollToItemPosition(tag = PROJECT_CHILDREN, index = 0)
            clickOn("${PROJECT_CHILD_LAYOUT}_0", timeout)

            // Project child
            waitUntilTagExists(PROPERTY_DETAIL_PRICE, timeout)
            clickOn(PROPERTY_DETAIL_CLOSE)

            // Project detail screen
            waitUntilTagExists(PROJECT_DETAIL_NAME, timeout)
            clickOn(PROJECT_DETAIL_CLOSE, timeout)

            // List
            waitUntilTagExists(tag = LISTING_LIST, timeout = timeout)
        }
    }

    @Test
    fun show_property_detail() {
        setupListingResponse(
            listingType = emptyList(),
            statusType = "buy"
        )

        with(composeTestRule) {
            waitUntilTagExists(tag = LISTING_LIST, timeout = timeout)

            setupPropertyResponse()
            scrollToItemPosition(tag = LISTING_LIST, index = 1)
            clickOn("${PROPERTY_LIST_ITEM_ADDRESS}_1", timeout)

            // Property detail screen
            waitUntilTagExists(PROPERTY_DETAIL_PRICE)

            clickOn(PROPERTY_DETAIL_CLOSE)

            waitUntilTagExists(tag = LISTING_LIST, timeout = timeout)
        }
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

    private fun setupProjectResponse() {
        webServerDispatcher.addResponse(
            context = context,
            pathPattern = "v1/project-details/2842",
            filename = "raw/project_detail.json",
            httpMethod = HttpMethod.GET,
            status = HttpURLConnection.HTTP_OK
        )
    }

    private fun setupPropertyResponse() {
        webServerDispatcher.addResponse(
            context = context,
            pathPattern = "v1/property-details/2019150933",
            filename = "raw/property_detail.json",
            httpMethod = HttpMethod.GET,
            status = HttpURLConnection.HTTP_OK
        )
    }

    private fun setupProjectChildResponse() {
        webServerDispatcher.addResponse(
            context = context,
            pathPattern = "v1/property-details/2019256252",
            filename = "raw/project_child_detail.json",
            httpMethod = HttpMethod.GET,
            status = HttpURLConnection.HTTP_OK
        )
    }
}