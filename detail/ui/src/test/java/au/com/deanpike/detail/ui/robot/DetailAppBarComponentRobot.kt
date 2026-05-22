package au.com.deanpike.detail.ui.robot

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import au.com.deanpike.detail.ui.shared.DetailAppBarComponent
import au.com.deanpike.detail.ui.shared.DetailAppBarComponentTestTags
import au.com.deanpike.uishared.theme.MviExampleTheme
import au.com.deanpike.uitestshared.base.TestRobotBase
import au.com.deanpike.uitestshared.base.TestRobotInitData
import au.com.deanpike.uitestshared.util.assertTagDisplayed
import au.com.deanpike.uitestshared.util.clickOn

class DetailAppBarComponentRobot(composeRule: ComposeContentTestRule) : TestRobotBase<DetailAppBarComponentRobot, TestRobotInitData>(composeRule) {
    var closeClicked = false
        private set

    override fun setupComponent(data: TestRobotInitData?) = apply {
        composeRule.setContent {
            MviExampleTheme {
                DetailAppBarComponent(
                    onCloseClicked = {
                        closeClicked = true
                    }
                )
            }
        }
    }

    override fun assertLayoutDisplayed() = apply {
        composeRule.assertTagDisplayed(DetailAppBarComponentTestTags.DETAIL_APP_BAR_LAYOUT)
    }

    fun assertCloseIconDisplayed() = apply {
        composeRule.assertTagDisplayed(DetailAppBarComponentTestTags.DETAIL_APP_BAR_CLOSE_ICON)
    }

    fun clickOnClose() = apply {
        composeRule.clickOn(DetailAppBarComponentTestTags.DETAIL_APP_BAR_CLOSE_ICON)
    }
}