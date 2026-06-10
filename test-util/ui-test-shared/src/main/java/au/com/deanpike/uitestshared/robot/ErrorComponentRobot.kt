package au.com.deanpike.uitestshared.robot

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import au.com.deanpike.uishared.component.ErrorComponent
import au.com.deanpike.uishared.component.ErrorComponentTestTags.ERROR_COMPONENT_BUTTON
import au.com.deanpike.uishared.component.ErrorComponentTestTags.ERROR_COMPONENT_LAYOUT
import au.com.deanpike.uishared.component.ErrorComponentTestTags.ERROR_COMPONENT_MESSAGE
import au.com.deanpike.uishared.component.ErrorComponentTestTags.ERROR_COMPONENT_TITLE
import au.com.deanpike.uishared.theme.AppTheme
import au.com.deanpike.uitestshared.base.TestRobotBase
import au.com.deanpike.uitestshared.base.TestRobotInitData
import au.com.deanpike.uitestshared.util.advanceTimeAndWait
import au.com.deanpike.uitestshared.util.assertTagDisplayed
import au.com.deanpike.uitestshared.util.assertTagDoesNotExist
import au.com.deanpike.uitestshared.util.assertTextDisplayed
import au.com.deanpike.uitestshared.util.clickOn
import au.com.deanpike.uitestshared.util.waitUntilTagExists
import org.assertj.core.api.Assertions.assertThat

class ErrorComponentRobot(composeRule: ComposeContentTestRule) : TestRobotBase<ErrorComponentRobot, TestRobotInitData>(composeRule) {
    private var retryClicked = false

    override fun setupComponent(data: TestRobotInitData?) = apply {
        composeRule.setContent {
            AppTheme {
                ErrorComponent(
                    onRetryClicked = {
                        retryClicked = true
                    }
                )
            }
        }
    }

    override fun assertLayoutDisplayed() = apply {
        composeRule.assertTagDisplayed(ERROR_COMPONENT_LAYOUT)
    }

    fun assertLayoutNotDisplayed() = apply {
        composeRule.assertTagDoesNotExist(ERROR_COMPONENT_LAYOUT)
    }

    fun assertTitle(title: String) = apply {
        composeRule.assertTextDisplayed(
            tag = ERROR_COMPONENT_TITLE,
            text = title
        )
    }

    fun assertMessage(message: String) = apply {
        composeRule.assertTextDisplayed(
            tag = ERROR_COMPONENT_MESSAGE,
            text = message
        )
    }

    fun assertButtonText(text: String) = apply {
        composeRule.assertTagDisplayed(ERROR_COMPONENT_BUTTON)
        composeRule.assertTextDisplayed(
            tag = "${ERROR_COMPONENT_BUTTON}_TEXT",
            text = text
        )
    }

    fun clickRetryButton() = apply {
        composeRule.clickOn(ERROR_COMPONENT_BUTTON)
    }

    fun assertRetryClicked() = apply {
        assertThat(retryClicked).isTrue()
    }

    fun waitUntilListShown() = apply {
        composeRule.advanceTimeAndWait()
        composeRule.waitUntilTagExists(tag = ERROR_COMPONENT_LAYOUT, timeout = 2000)
    }
}