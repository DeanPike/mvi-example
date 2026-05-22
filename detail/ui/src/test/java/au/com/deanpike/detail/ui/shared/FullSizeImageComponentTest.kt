package au.com.deanpike.detail.ui.shared

import au.com.deanpike.detail.ui.robot.FullSizeImageComponentRobot
import au.com.deanpike.uitestshared.base.RobolectricTestBase
import junit.framework.TestCase.assertTrue
import org.junit.Test

class FullSizeImageComponentTest : RobolectricTestBase() {

    private val robot = FullSizeImageComponentRobot(composeTestRule)

    @Test
    fun shouldDisplay_full_size_image() {
        val testUrl = "https://example.com/image.jpg"
        robot
            .setupComponent(FullSizeImageComponentRobot.FullSizeImageComponentRobotInitData(url = testUrl))
            .assertLayoutDisplayed()
            .assertBackButtonDisplayed()
            .clickBackButton()

        assertTrue(robot.backClicked)
    }
}