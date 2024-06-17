package au.com.deanpike.detail.ui.framework.ability

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import au.com.deanpike.detail.ui.project.ProjectChildComponentTestTags.PROJECT_CHILD_IMAGE
import au.com.deanpike.detail.ui.project.ProjectChildComponentTestTags.PROJECT_CHILD_LAYOUT
import au.com.deanpike.detail.ui.project.ProjectChildComponentTestTags.PROJECT_CHILD_LIFECYCLE
import au.com.deanpike.detail.ui.project.ProjectChildComponentTestTags.PROJECT_CHILD_PRICE
import au.com.deanpike.uitestshared.util.assertTagDisplayed
import au.com.deanpike.uitestshared.util.assertTextDisplayed
import au.com.deanpike.uitestshared.util.clickOn

class ProjectChildComponentAbility(private val composeTestRule: ComposeContentTestRule) {
    fun assertChildListingDisplayed(position: Int) {
        composeTestRule.assertTagDisplayed("${PROJECT_CHILD_LAYOUT}_$position")
    }

    fun assertChildImageDisplayed(position: Int) {
        composeTestRule.assertTagDisplayed("${PROJECT_CHILD_IMAGE}_$position")
    }

    fun assertChildPriceDisplayed(position: Int, text: String) {
        composeTestRule.assertTextDisplayed(
            tag = "${PROJECT_CHILD_PRICE}_$position",
            text = text
        )
    }

    fun assertLifecycleDisplayed(position: Int, text: String) {
        composeTestRule.assertTextDisplayed(
            tag = "${PROJECT_CHILD_LIFECYCLE}_$position",
            text = text
        )
    }

    fun clickChild(position: Int) {
        composeTestRule.clickOn("${PROJECT_CHILD_LAYOUT}_$position")
    }
}