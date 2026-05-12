package au.com.deanpike.listings.ui.list.component

import au.com.deanpike.listings.client.type.DwellingType
import au.com.deanpike.listings.client.type.StatusType
import au.com.deanpike.listings.ui.robot.FilterComponentRobot
import au.com.deanpike.listings.ui.robot.FilterComponentRobotInitData
import au.com.deanpike.uitestshared.base.RobolectricTestBase
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class FilterComponentTest : RobolectricTestBase() {
    private val robot = FilterComponentRobot(composeTestRule)

    @Test
    fun `screen should be displayed`() {
        robot
            .setupComponent(
                data = FilterComponentRobotInitData(
                    status = StatusType.RENT,
                    dwellingTypes = listOf(DwellingType.HOUSE, DwellingType.TOWNHOUSE)
                )
            )
            .assertLayoutDisplayed()
            .assertStatusText("Rent")
            .assertDwellingText("Multiple property types")
            .assertFilterButtonDisplayed()
    }

    @Test
    fun `should click on filter button`(){
        assertFalse(robot.eventCalled)

        robot
            .setupComponent(
                data = FilterComponentRobotInitData(
                    status = StatusType.RENT,
                    dwellingTypes = listOf(DwellingType.HOUSE, DwellingType.TOWNHOUSE)
                )
            )
            .assertLayoutDisplayed()
            .clickFilterButton()

        assertTrue(robot.eventCalled)
    }
}