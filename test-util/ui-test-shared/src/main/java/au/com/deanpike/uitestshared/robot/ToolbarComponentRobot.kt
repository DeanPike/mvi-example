package au.com.deanpike.uitestshared.robot

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import au.com.deanpike.uishared.R
import au.com.deanpike.uishared.component.ToolbarComponent
import au.com.deanpike.uishared.component.ToolbarComponentTestTags.TOOLBAR_ICON
import au.com.deanpike.uishared.component.ToolbarComponentTestTags.TOOLBAR_LAYOUT
import au.com.deanpike.uishared.component.ToolbarComponentTestTags.TOOLBAR_TITLE
import au.com.deanpike.uishared.theme.MviExampleTheme
import au.com.deanpike.uitestshared.base.TestRobotBase
import au.com.deanpike.uitestshared.base.TestRobotInitData
import au.com.deanpike.uitestshared.util.assertDrawableDisplayed
import au.com.deanpike.uitestshared.util.assertTagDisplayed
import au.com.deanpike.uitestshared.util.assertTextDisplayed
import au.com.deanpike.uitestshared.util.clickOn

class ToolbarComponentRobot(composeRule: ComposeContentTestRule) : TestRobotBase<ToolbarComponentRobot, ToolbarComponentRobotInitData>(composeRule) {
    override fun setupComponent(data: ToolbarComponentRobotInitData?) = apply {
        composeRule.setContent {
            MviExampleTheme {
                ToolbarComponent(
                    title = data!!.title,
                )
            }
        }
    }

    override fun assertLayoutDisplayed() = apply {
        composeRule.assertTagDisplayed(TOOLBAR_LAYOUT)
    }

    fun assertNavigationIconDisplayed() = apply {
        composeRule.assertDrawableDisplayed(tag = TOOLBAR_ICON, drawable = R.drawable.arrow_back_24)
    }

    fun assertToolbarTitle(title: String) = apply {
        composeRule.assertTextDisplayed(tag = TOOLBAR_TITLE, text = title)
    }

    fun clickBack() = apply {
        composeRule.clickOn(TOOLBAR_ICON)
    }
}

data class ToolbarComponentRobotInitData(
    val title: String = ""
) : TestRobotInitData