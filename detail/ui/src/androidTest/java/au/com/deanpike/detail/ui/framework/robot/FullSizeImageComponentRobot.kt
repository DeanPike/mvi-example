package au.com.deanpike.detail.ui.framework.robot

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import au.com.deanpike.detail.ui.shared.FullSizeImageComponent
import au.com.deanpike.detail.ui.shared.FullSizeImageTestTags.FULL_SIZE_IMAGE_BACK_BUTTON
import au.com.deanpike.detail.ui.shared.FullSizeImageTestTags.FULL_SIZE_IMAGE_IMAGE
import au.com.deanpike.uishared.theme.MviExampleTheme
import au.com.deanpike.uitestshared.base.TestRobotBase
import au.com.deanpike.uitestshared.base.TestRobotInitData
import au.com.deanpike.uitestshared.util.assertDrawableDisplayed
import au.com.deanpike.uitestshared.util.assertTagDisplayed
import au.com.deanpike.uitestshared.util.clickOn

class FullSizeImageComponentRobot(composeRule: ComposeContentTestRule) : TestRobotBase<FullSizeImageComponentRobot, FullSizeImageComponentRobot.FullSizeImageComponentRobotInitData>(composeRule) {

    var backClicked = false
        private set

    override fun setupComponent(data: FullSizeImageComponentRobotInitData?): FullSizeImageComponentRobot {
        composeRule.setContent {
            MviExampleTheme {
                FullSizeImageComponent(
                    url = data!!.url,
                    onBackClicked = {
                        backClicked = true
                    }
                )
            }
        }
        return this
    }

    override fun assertLayoutDisplayed(): FullSizeImageComponentRobot {
        composeRule.assertTagDisplayed(FULL_SIZE_IMAGE_IMAGE)
        return this
    }

    fun assertBackButtonDisplayed(): FullSizeImageComponentRobot {
        composeRule.assertDrawableDisplayed(
            tag = FULL_SIZE_IMAGE_BACK_BUTTON,
            drawable = au.com.deanpike.uishared.R.drawable.arrow_back_24
        )
        return this
    }

    fun clickBackButton() {
        composeRule.clickOn(FULL_SIZE_IMAGE_BACK_BUTTON)
    }

    data class FullSizeImageComponentRobotInitData(
        val url: String
    ) : TestRobotInitData
}