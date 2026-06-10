package au.com.deanpike.uitestshared.robot

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import au.com.deanpike.uishared.component.LifecycleStatusComponent
import au.com.deanpike.uishared.component.LifecycleStatusTestTags.LIFECYCLE_STATUS_LAYOUT
import au.com.deanpike.uishared.component.LifecycleStatusTestTags.LIFECYCLE_STATUS_TEXT
import au.com.deanpike.uishared.theme.AppTheme
import au.com.deanpike.uitestshared.base.TestRobotBase
import au.com.deanpike.uitestshared.base.TestRobotInitData
import au.com.deanpike.uitestshared.util.assertTagDisplayed
import au.com.deanpike.uitestshared.util.assertTextDisplayed

class LifecycleStatusComponentRobot(composeRule: ComposeContentTestRule) :
    TestRobotBase<LifecycleStatusComponentRobot, LifecycleStatusComponentRobotInitData>(composeRule) {

    override fun setupComponent(data: LifecycleStatusComponentRobotInitData?) = apply {
        composeRule.setContent {
            AppTheme {
                LifecycleStatusComponent(
                    lifecycleStatus = data!!.lifecycleStatus
                )
            }
        }
    }

    override fun assertLayoutDisplayed() = apply {
        composeRule.assertTagDisplayed(LIFECYCLE_STATUS_LAYOUT)
    }

    fun assertLifecycle(lifecycle: String) = apply {
        composeRule.assertTextDisplayed(
            tag = LIFECYCLE_STATUS_TEXT,
            text = lifecycle
        )
    }
}

data class LifecycleStatusComponentRobotInitData(
    val lifecycleStatus: String
) : TestRobotInitData