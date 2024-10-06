package au.com.deanpike.listings.ui.screen.list.component

import au.com.deanpike.listings.client.type.DwellingType
import au.com.deanpike.listings.client.type.StatusType
import au.com.deanpike.listings.ui.framework.robot.FilterComponentRobot
import au.com.deanpike.listings.ui.framework.robot.FilterComponentRobotInitData
import au.com.deanpike.listings.ui.framework.robot.StatusDropDownMenuRobot
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
            .setupComponent(
                data = FilterComponentRobotInitData()
            )
            .waitForIdle()
            .assertLayoutDisplayed()
            .assertStatusButtonText("Rent")
            .assertStatusButtonIcon()
            .assertListingTypeButtonText("1 Property type")

    }

    @Test
    fun change_the_status() {
        filterComponentRobot
            .setupComponent(
                data = FilterComponentRobotInitData(
                    listingTypes = listOf(DwellingType.HOUSE, DwellingType.TOWNHOUSE)
                )
            )
            .waitForIdle()
            .assertListingTypeButtonText("2 Property types")
            .clickStatusButton()
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
            .setupComponent(
                data = FilterComponentRobotInitData()
            )
            .waitForIdle()
            .assertListingTypeButtonText("1 Property type")
            .clickListingTypeButton()
            .waitForIdle()
            .assertListingTypeButtonClicked()
    }
}