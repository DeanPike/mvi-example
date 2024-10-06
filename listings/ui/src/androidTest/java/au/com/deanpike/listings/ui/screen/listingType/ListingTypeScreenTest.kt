package au.com.deanpike.listings.ui.screen.listingType

import au.com.deanpike.listings.client.type.DwellingType
import au.com.deanpike.listings.ui.listingType.ListingTypeState
import au.com.deanpike.listings.ui.framework.robot.ListingTypeScreenRobot
import au.com.deanpike.listings.ui.framework.robot.ListingTypeScreenRobotInitData
import au.com.deanpike.uitestshared.base.UiUnitTestBase
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ListingTypeScreenTest : UiUnitTestBase() {
    private val robot = ListingTypeScreenRobot(composeTestRule)

    @Test
    fun should_show_default_screen() {
        robot
            .setupComponent(
                data = ListingTypeScreenRobotInitData(
                    state = ListingTypeState(),
                )
            )
            .assertLayoutDisplayed()
            .assertAllDisplayed()
            .assertAllSelected(true)
            .assertHouseDisplayed()
            .assertHouseSelected(false)
            .assertTownhouseDisplayed()
            .assertTownhouseSelected(false)
            .assertApartmentDisplayed()
            .assertApartmentSelected(false)
            .assertApplyDisplayed()

        robot.clickHouse()
        assertThat(robot.listingTypeSelected).isEqualTo(DwellingType.HOUSE)
        assertThat(robot.isSelected).isTrue()

        robot.clickApply()
        assertThat(robot.applyClicked).isTrue()
    }
}