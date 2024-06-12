package au.com.deanpike.uitestshared.ability

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import au.com.deanpike.uishared.component.ErrorComponentTestTags.ERROR_COMPONENT_BUTTON
import au.com.deanpike.uishared.component.ErrorComponentTestTags.ERROR_COMPONENT_LAYOUT
import au.com.deanpike.uishared.component.ErrorComponentTestTags.ERROR_COMPONENT_MESSAGE
import au.com.deanpike.uishared.component.ErrorComponentTestTags.ERROR_COMPONENT_TITLE
import au.com.deanpike.uitestshared.util.assertTagDisplayed
import au.com.deanpike.uitestshared.util.assertTextDisplayed
import au.com.deanpike.uitestshared.util.clickOn

class ErrorComponentAbility(private val composeTestRule: ComposeContentTestRule) {

    fun assertComponentDisplayed() {
        composeTestRule.assertTagDisplayed(ERROR_COMPONENT_LAYOUT)
    }

    fun assertTitle() {
        composeTestRule.assertTextDisplayed(
            tag = ERROR_COMPONENT_TITLE,
            text = "Something went wrong"
        )
    }

    fun assertMessage() {
        composeTestRule.assertTextDisplayed(
            tag = ERROR_COMPONENT_MESSAGE,
            text = "Please check your network connection and try again"
        )
    }

    fun assertRetryButtonDisplayed() {
        composeTestRule.assertTagDisplayed(ERROR_COMPONENT_BUTTON)
        composeTestRule.assertTextDisplayed(
            tag = "${ERROR_COMPONENT_BUTTON}_TEXT",
            text = "Retry"
        )
    }

    fun clickRetryButton() {
        composeTestRule.clickOn(ERROR_COMPONENT_BUTTON)
    }
}