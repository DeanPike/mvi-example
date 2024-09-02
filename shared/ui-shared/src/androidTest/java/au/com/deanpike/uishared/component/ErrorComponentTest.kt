package au.com.deanpike.uishared.component

import au.com.deanpike.uitestshared.base.UiUnitTestBase
import au.com.deanpike.uitestshared.robot.ErrorComponentRobot
import org.junit.Test

class ErrorComponentTest : UiUnitTestBase() {
    private val robot = ErrorComponentRobot(composeTestRule)

    @Test
    fun should_display_error_component() {
        robot
            .setupComponent().assertLayoutDisplayed()
            .waitForIdle()
            .assertTitle("Something went wrong")
            .assertMessage("Please check your network connection and try again")
            .assertButtonText("Retry")
            .clickRetryButton()
            .waitForIdle()
            .assertRetryClicked()
    }
}