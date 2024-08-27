package au.com.deanpike.uishared.component

import au.com.deanpike.uitestshared.base.UiUnitTestBase
import au.com.deanpike.uitestshared.robot.DetailItemComponentRobot
import org.junit.Before
import org.junit.Test

class DetailItemComponentTest : UiUnitTestBase() {
    private lateinit var robot: DetailItemComponentRobot

    @Before
    fun setupTest() {
        robot = DetailItemComponentRobot(composeTestRule)
    }

    @Test
    fun check_that_details_items_are_displayed() {
        robot
            .setupComponent()
            .waitForIdle()
            .assertLayoutDisplayed()
            .assertIconDisplayed()
            .assertItemCount()

    }
}