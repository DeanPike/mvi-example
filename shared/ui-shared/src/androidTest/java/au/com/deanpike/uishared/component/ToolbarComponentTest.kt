package au.com.deanpike.uishared.component

import au.com.deanpike.uitestshared.base.UiUnitTestBase
import au.com.deanpike.uitestshared.robot.ToolbarComponentRobot
import au.com.deanpike.uitestshared.robot.ToolbarComponentRobotInitData
import au.com.deanpike.uitestshared.util.disableAnimations
import org.junit.Before
import org.junit.Test

class ToolbarComponentTest : UiUnitTestBase() {
    private val robot = ToolbarComponentRobot(composeTestRule)

    @Before
    fun setupTest() {
        composeTestRule.disableAnimations()
    }

    @Test
    fun should_show_toolbar() {
        robot
            .setupComponent(
                data = ToolbarComponentRobotInitData(title = "Toolbar Title")
            )
            .assertLayoutDisplayed()
            .assertNavigationIconDisplayed()
            .assertToolbarTitle("Toolbar Title")
    }
}