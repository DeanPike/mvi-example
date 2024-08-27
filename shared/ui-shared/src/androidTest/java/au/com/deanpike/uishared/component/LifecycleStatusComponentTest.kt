package au.com.deanpike.uishared.component

import au.com.deanpike.uitestshared.base.UiUnitTestBase
import au.com.deanpike.uitestshared.robot.LifecycleStatusComponentRobot
import au.com.deanpike.uitestshared.robot.LifecycleStatusComponentRobotInitData
import org.junit.Before
import org.junit.Test

class LifecycleStatusComponentTest : UiUnitTestBase() {
    private lateinit var robot: LifecycleStatusComponentRobot

    @Before
    fun setupTest(){
        robot = LifecycleStatusComponentRobot(composeTestRule)
    }

    @Test
    fun assert_lifecycle_displayed(){
        val initData = LifecycleStatusComponentRobotInitData(
            lifecycleStatus = "New House"
        )
        robot
            .setupComponent(initData)
            .assertLayoutDisplayed()
            .assertLifecycle("New House")
    }
}