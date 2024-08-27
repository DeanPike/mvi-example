package au.com.deanpike.uitestshared.robot

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import au.com.deanpike.uishared.component.LifecycleStatusComponent
import au.com.deanpike.uishared.component.LifecycleStatusTestTags.LIFECYCLE_STATUS_LAYOUT
import au.com.deanpike.uishared.component.LifecycleStatusTestTags.LIFECYCLE_STATUS_TEXT
import au.com.deanpike.uishared.theme.MviExampleTheme
import au.com.deanpike.uitestshared.base.TestRobotBase
import au.com.deanpike.uitestshared.base.TestRobotInitData
import au.com.deanpike.uitestshared.util.assertTagDisplayed
import au.com.deanpike.uitestshared.util.assertTextDisplayed

class LifecycleStatusComponentRobot(private val composeRule: ComposeContentTestRule) : TestRobotBase<LifecycleStatusComponentRobot, LifecycleStatusComponentRobotInitData>(composeRule) {

    override fun setupComponent(data: LifecycleStatusComponentRobotInitData?): LifecycleStatusComponentRobot {
        composeRule.setContent {
            MviExampleTheme {
                LifecycleStatusComponent(
                    lifecycleStatus = data!!.lifecycleStatus
                )
            }
        }
        return this
    }

    override fun assertLayoutDisplayed(): LifecycleStatusComponentRobot {
        composeRule.assertTagDisplayed(LIFECYCLE_STATUS_LAYOUT)
        return this
    }

    fun assertLifecycle(lifecycle: String): LifecycleStatusComponentRobot {
        composeRule.assertTextDisplayed(
            tag = LIFECYCLE_STATUS_TEXT,
            text = lifecycle
        )
        return this
    }
}

data class LifecycleStatusComponentRobotInitData(
    val lifecycleStatus: String
) : TestRobotInitData