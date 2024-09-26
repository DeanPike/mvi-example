package au.com.deanpike.uitestshared.robot

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import au.com.deanpike.uishared.component.ErrorComponent
import au.com.deanpike.uishared.component.ErrorComponentTestTags.ERROR_COMPONENT_BUTTON
import au.com.deanpike.uishared.component.ErrorComponentTestTags.ERROR_COMPONENT_LAYOUT
import au.com.deanpike.uishared.component.ErrorComponentTestTags.ERROR_COMPONENT_MESSAGE
import au.com.deanpike.uishared.component.ErrorComponentTestTags.ERROR_COMPONENT_TITLE
import au.com.deanpike.uishared.theme.MviExampleTheme
import au.com.deanpike.uitestshared.base.TestRobotBase
import au.com.deanpike.uitestshared.base.TestRobotInitData
import au.com.deanpike.uitestshared.util.advanceTimeAndWait
import au.com.deanpike.uitestshared.util.assertTagDisplayed
import au.com.deanpike.uitestshared.util.assertTagDoesNotExist
import au.com.deanpike.uitestshared.util.assertTextDisplayed
import au.com.deanpike.uitestshared.util.clickOn
import au.com.deanpike.uitestshared.util.waitUntilTagExists
import org.assertj.core.api.Assertions.assertThat

class ErrorComponentRobot(private val composeRule: ComposeContentTestRule) : TestRobotBase<ErrorComponentRobot, TestRobotInitData>(composeRule) {
    private var retryClicked = false

    override fun setupComponent(data: TestRobotInitData?): ErrorComponentRobot {
        composeRule.setContent {
            MviExampleTheme {
                ErrorComponent(
                    onRetryClicked = {
                        retryClicked = true
                    }
                )
            }
        }
        return this
    }

    override fun assertLayoutDisplayed(): ErrorComponentRobot {
        composeRule.assertTagDisplayed(ERROR_COMPONENT_LAYOUT)
        return this
    }

    fun assertLayoutNotDisplayed(): ErrorComponentRobot {
        composeRule.assertTagDoesNotExist(ERROR_COMPONENT_LAYOUT)
        return this
    }

    fun assertTitle(title: String): ErrorComponentRobot {
        composeRule.assertTextDisplayed(
            tag = ERROR_COMPONENT_TITLE,
            text = title
        )
        return this
    }

    fun assertMessage(message: String): ErrorComponentRobot {
        composeRule.assertTextDisplayed(
            tag = ERROR_COMPONENT_MESSAGE,
            text = message
        )
        return this
    }

    fun assertButtonText(text: String): ErrorComponentRobot {
        composeRule.assertTagDisplayed(ERROR_COMPONENT_BUTTON)
        composeRule.assertTextDisplayed(
            tag = "${ERROR_COMPONENT_BUTTON}_TEXT",
            text = text
        )
        return this
    }

    fun clickRetryButton(): ErrorComponentRobot {
        composeRule.clickOn(ERROR_COMPONENT_BUTTON)
        return this
    }

    fun assertRetryClicked(): ErrorComponentRobot {
        assertThat(retryClicked).isTrue()
        return this
    }

    fun waitUntilListShown(): ErrorComponentRobot {
        composeRule.advanceTimeAndWait()
        composeRule.waitUntilTagExists(tag = ERROR_COMPONENT_LAYOUT, timeout = 2000)
        return this
    }
}