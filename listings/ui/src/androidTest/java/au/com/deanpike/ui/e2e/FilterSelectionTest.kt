package au.com.deanpike.ui.e2e

import au.com.deanpike.ui.framework.robot.FilterComponentRobot
import au.com.deanpike.ui.framework.robot.ListingListScreenRobot
import au.com.deanpike.ui.framework.robot.ListingTypeScreenRobot
import au.com.deanpike.ui.framework.robot.StatusDropDownMenuRobot
import au.com.deanpike.listings.ui.list.ListingListScreen
import au.com.deanpike.uishared.theme.MviExampleTheme
import au.com.deanpike.uitestshared.HiltTestActivity
import au.com.deanpike.uitestshared.base.UiE2ETestBase
import au.com.deanpike.uitestshared.mockserver.HttpMethod
import dagger.hilt.android.testing.HiltAndroidTest
import java.net.HttpURLConnection
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class FilterSelectionTest : UiE2ETestBase() {
    @get:Rule(order = 1)
    val composeTestRule = createComposeRuleFor(HiltTestActivity::class.java)

    private val filterRobot = FilterComponentRobot(composeTestRule)
    private val dropDownMenuRobot = StatusDropDownMenuRobot(composeTestRule)
    private val listingTypeRobot = ListingTypeScreenRobot(composeTestRule)
    private val listingListScreenRobot = ListingListScreenRobot(composeTestRule)

    @Test
    fun should_show_status_and_listing_types() {
        setupResponse(
            listingType = emptyList(),
            statusType = "buy"
        )

        with(composeTestRule) {
            setContent {
                MviExampleTheme {
                    ListingListScreen()
                }
            }

            listingListScreenRobot.waitUntilListShown()

            filterRobot
                .assertLayoutDisplayed()
                .assertStatusButtonIcon()
                .assertStatusButtonText("Buy")
                .assertListingTypeButtonText("Property types")

            filterRobot.clickStatusButton()

            setupResponse(
                listingType = emptyList(),
                statusType = "sold"
            )

            dropDownMenuRobot
                .assertMenuDisplayed()
                .clickSold()
                .assertMenuNotDisplayed()
            listingListScreenRobot.waitUntilListShown()

            filterRobot
                .clickListingTypeButton()
                .waitUntilLayoutShown()

            setupResponse(
                listingType = listOf("House"),
                statusType = "sold"
            )

            listingTypeRobot
                .assertLayoutDisplayed()
                .clickHouse()
                .clickApply()
            listingListScreenRobot.waitUntilListShown()
        }
    }

    private fun setupResponse(listingType: List<String>, statusType: String) {

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