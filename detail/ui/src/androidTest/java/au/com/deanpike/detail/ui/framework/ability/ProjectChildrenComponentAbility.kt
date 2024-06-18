package au.com.deanpike.detail.ui.framework.ability

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import au.com.deanpike.detail.ui.project.ProjectChildrenComponentTestTags.PROJECT_CHILDREN
import au.com.deanpike.uitestshared.util.advanceTimeAndWait
import au.com.deanpike.uitestshared.util.assertTagDisplayed
import au.com.deanpike.uitestshared.util.scrollToItemPosition

class ProjectChildrenComponentAbility(private val composeTestRule: ComposeContentTestRule) {

    fun assertProjectChildrenDisplayed() {
        composeTestRule.assertTagDisplayed(PROJECT_CHILDREN)
    }

    fun scrollToChild(index: Int) {
        composeTestRule.scrollToItemPosition(tag = PROJECT_CHILDREN, index = index)
        composeTestRule.advanceTimeAndWait()
    }
}