package au.com.deanpike.ui.unit.screen.list.component

import au.com.deanpike.listings.client.type.DwellingType
import au.com.deanpike.listings.client.type.StatusType
import au.com.deanpike.ui.framework.robot.FilterComponentRobot
import au.com.deanpike.ui.framework.robot.StatusDropDownMenuRobot
import au.com.deanpike.uitestshared.base.UiUnitTestBase
import org.junit.Before
import org.junit.Test

class FilterComponentTest : UiUnitTestBase() {

    private lateinit var filterComponentRobot: FilterComponentRobot
    private lateinit var statusDropDownMenuRobot: StatusDropDownMenuRobot

    @Before
    fun setUp() {
        filterComponentRobot = FilterComponentRobot(composeTestRule)
        statusDropDownMenuRobot = StatusDropDownMenuRobot(composeTestRule)
    }

    @Test
    fun should_show_filter_component() {
        filterComponentRobot
            .setUpLoginScreen()
            .waitForIdle()
            .assertLayoutDisplayed()
            .assertStatusButtonText("Rent")
            .assertStatusButtonIcon()
            .assertListingTypeButtonText("1 Property type")

    }

    @Test
    fun change_the_status() {
        filterComponentRobot
            .setUpLoginScreen(
                listingTypes = listOf(DwellingType.HOUSE, DwellingType.TOWNHOUSE)
            )
            .waitForIdle()
            .assertListingTypeButtonText("2 Property types")
            .selectStatusButton()
            .waitForIdle()

        statusDropDownMenuRobot
            .assertMenuDisplayed()
            .assertBuyStatusDisplayed()
            .assertRentStatusDisplayed()
            .assertSoldStatusDisplayed()
            .clickBuy()
            .waitForIdle()
            .assertMenuNotDisplayed()

        filterComponentRobot
            .assertStatusSelected(StatusType.BUY)
    }

    @Test
    fun click_the_listing_type() {
        filterComponentRobot
            .setUpLoginScreen()
            .waitForIdle()
            .assertListingTypeButtonText("1 Property type")
            .clickListingTypeButton()
            .waitForIdle()
            .assertListingTypeButtonClicked()
    }
}