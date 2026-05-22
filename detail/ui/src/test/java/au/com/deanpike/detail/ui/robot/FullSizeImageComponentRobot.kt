package au.com.deanpike.detail.ui.robot

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import au.com.deanpike.detail.ui.shared.FullSizeImageComponent
import au.com.deanpike.detail.ui.shared.FullSizeImageTestTags
import au.com.deanpike.uishared.R
import au.com.deanpike.uishared.theme.MviExampleTheme
import au.com.deanpike.uitestshared.base.TestRobotBase
import au.com.deanpike.uitestshared.base.TestRobotInitData
import au.com.deanpike.uitestshared.util.assertDrawableDisplayed
import au.com.deanpike.uitestshared.util.assertTagDisplayed
import au.com.deanpike.uitestshared.util.clickOn

class FullSizeImageComponentRobot(composeRule: ComposeContentTestRule) : TestRobotBase<FullSizeImageComponentRobot, FullSizeImageComponentRobot.FullSizeImageComponentRobotInitData>(composeRule) {

    var backClicked = false
        private set

    override fun setupComponent(data: FullSizeImageComponentRobotInitData?) = apply {
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
    }

    override fun assertLayoutDisplayed() = apply {
        composeRule.assertTagDisplayed(FullSizeImageTestTags.FULL_SIZE_IMAGE_IMAGE)
    }

    fun assertBackButtonDisplayed() = apply {
        composeRule.assertDrawableDisplayed(
            tag = FullSizeImageTestTags.FULL_SIZE_IMAGE_BACK_BUTTON,
            drawable = R.drawable.arrow_back_24
        )
    }

    fun clickBackButton() {
        composeRule.clickOn(FullSizeImageTestTags.FULL_SIZE_IMAGE_BACK_BUTTON)
    }

    data class FullSizeImageComponentRobotInitData(
        val url: String
    ) : TestRobotInitData
}