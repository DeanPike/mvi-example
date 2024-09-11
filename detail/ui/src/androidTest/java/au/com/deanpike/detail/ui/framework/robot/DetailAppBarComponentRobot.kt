package au.com.deanpike.detail.ui.framework.robot

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import au.com.deanpike.detail.ui.shared.DetailAppBarComponent
import au.com.deanpike.detail.ui.shared.DetailAppBarComponentTestTags.DETAIL_APP_BAR_CLOSE_ICON
import au.com.deanpike.detail.ui.shared.DetailAppBarComponentTestTags.DETAIL_APP_BAR_LAYOUT
import au.com.deanpike.uishared.theme.MviExampleTheme
import au.com.deanpike.uitestshared.base.TestRobotBase
import au.com.deanpike.uitestshared.base.TestRobotInitData
import au.com.deanpike.uitestshared.util.assertTagDisplayed
import au.com.deanpike.uitestshared.util.clickOn

class DetailAppBarComponentRobot(private val composeRule: ComposeContentTestRule) : TestRobotBase<DetailAppBarComponentRobot, TestRobotInitData>(composeRule) {
    var closeClicked = false
        private set

    override fun setupComponent(data: TestRobotInitData?): DetailAppBarComponentRobot {
        composeRule.setContent {
            MviExampleTheme {
                DetailAppBarComponent(
                    onCloseClicked = {
                        closeClicked = true
                    }
                )
            }
        }

        return this
    }

    override fun assertLayoutDisplayed(): DetailAppBarComponentRobot {
        composeRule.assertTagDisplayed(DETAIL_APP_BAR_LAYOUT)
        return this
    }

    fun assertCloseIconDisplayed(): DetailAppBarComponentRobot {
        composeRule.assertTagDisplayed(DETAIL_APP_BAR_CLOSE_ICON)
        return this
    }

    fun clickOnClose(): DetailAppBarComponentRobot {
        composeRule.clickOn(DETAIL_APP_BAR_CLOSE_ICON)
        return this
    }
}