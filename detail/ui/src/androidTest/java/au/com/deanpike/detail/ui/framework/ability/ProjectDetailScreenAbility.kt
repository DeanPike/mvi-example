package au.com.deanpike.detail.ui.framework.ability

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import au.com.deanpike.detail.ui.R
import au.com.deanpike.detail.ui.project.ProjectChildrenComponentTestTags.PROJECT_CHILDREN
import au.com.deanpike.detail.ui.project.ProjectDetailScreenTestTags.PROJECT_DETAILS_LAYOUT
import au.com.deanpike.detail.ui.project.ProjectDetailScreenTestTags.PROJECT_DETAIL_ADDRESS
import au.com.deanpike.detail.ui.project.ProjectDetailScreenTestTags.PROJECT_DETAIL_AGENT_LABEL
import au.com.deanpike.detail.ui.project.ProjectDetailScreenTestTags.PROJECT_DETAIL_CLOSE
import au.com.deanpike.detail.ui.project.ProjectDetailScreenTestTags.PROJECT_DETAIL_DESCRIPTION
import au.com.deanpike.detail.ui.project.ProjectDetailScreenTestTags.PROJECT_DETAIL_HEADLINE
import au.com.deanpike.detail.ui.project.ProjectDetailScreenTestTags.PROJECT_DETAIL_NAME
import au.com.deanpike.detail.ui.project.ProjectDetailScreenTestTags.PROJECT_DETAIL_PROGRESS
import au.com.deanpike.detail.ui.project.ProjectDetailScreenTestTags.PROJECT_LAYOUT
import au.com.deanpike.uishared.component.ErrorComponentTestTags.ERROR_COMPONENT_LAYOUT
import au.com.deanpike.uitestshared.util.assertDrawableDisplayed
import au.com.deanpike.uitestshared.util.assertTagDisplayed
import au.com.deanpike.uitestshared.util.assertTagDoesNotExist
import au.com.deanpike.uitestshared.util.assertTextDisplayed
import au.com.deanpike.uitestshared.util.clickOn

class ProjectDetailScreenAbility(private val composeTestRule: ComposeContentTestRule) {

    fun assertProjectLayoutDisplayed() {
        composeTestRule.assertTagDisplayed(PROJECT_LAYOUT)
    }

    fun assertProgressDisplayed() {
        composeTestRule.assertTagDisplayed(PROJECT_DETAIL_PROGRESS)
    }

    fun assertProgressNotDisplayed() {
        composeTestRule.assertTagDoesNotExist(PROJECT_DETAIL_PROGRESS)
    }

    fun assertErrorDisplayed() {
        composeTestRule.assertTagDisplayed(ERROR_COMPONENT_LAYOUT)
    }

    fun assertErrorNotDisplayed() {
        composeTestRule.assertTagDoesNotExist(ERROR_COMPONENT_LAYOUT)
    }

    fun assertProjectDetailsDisplayed() {
        composeTestRule.assertTagDisplayed(PROJECT_DETAILS_LAYOUT)
    }

    fun assertProjectDetailsNotDisplayed() {
        composeTestRule.assertTagDoesNotExist(PROJECT_DETAILS_LAYOUT)
    }

    fun assertCloseDisplayed() {
        composeTestRule.assertDrawableDisplayed(
            tag = PROJECT_DETAIL_CLOSE,
            drawable = R.drawable.clear_24
        )
    }

    fun assertProjectNameDisplayed(text: String) {
        composeTestRule.assertTextDisplayed(
            tag = PROJECT_DETAIL_NAME,
            text = text
        )
    }

    fun assertProjectAddressDisplayed(text: String) {
        composeTestRule.assertTextDisplayed(
            tag = PROJECT_DETAIL_ADDRESS,
            text = text
        )
    }

    fun assertProjectHeadlineDisplayed(text: String) {
        composeTestRule.assertTextDisplayed(
            tag = PROJECT_DETAIL_HEADLINE,
            text = text
        )
    }

    fun assertProjectDetailDescriptionDisplayed() {
        composeTestRule.assertTagDisplayed(tag = PROJECT_DETAIL_DESCRIPTION)
    }

    fun assertProjectChildrenDisplayed() {
        composeTestRule.assertTagDisplayed(tag = PROJECT_CHILDREN)
    }

    fun assertAgentLabelDisplayed() {
        composeTestRule.assertTextDisplayed(tag = PROJECT_DETAIL_AGENT_LABEL, text = "Agent")
    }

    fun clickOnClose() {
        composeTestRule.clickOn(PROJECT_DETAIL_CLOSE)
    }
}