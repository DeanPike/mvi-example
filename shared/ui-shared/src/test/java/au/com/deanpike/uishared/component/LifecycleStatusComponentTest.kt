package au.com.deanpike.uishared.component

import au.com.deanpike.uitestshared.base.RobolectricTestBase
import au.com.deanpike.uitestshared.robot.LifecycleStatusComponentRobot
import au.com.deanpike.uitestshared.robot.LifecycleStatusComponentRobotInitData
import org.junit.Test

class LifecycleStatusComponentTest : RobolectricTestBase() {
    private val robot = LifecycleStatusComponentRobot(composeTestRule)

    @Test
    fun assert_lifecycle_displayed() {
        val initData = LifecycleStatusComponentRobotInitData(
            lifecycleStatus = "New House"
        )
        robot
            .setupComponent(initData)
            .assertLayoutDisplayed()
            .assertLifecycle("New House")
    }
}