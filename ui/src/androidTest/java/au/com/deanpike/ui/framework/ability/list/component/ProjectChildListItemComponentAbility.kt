package au.com.deanpike.ui.framework.ability.list.component

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import au.com.deanpike.ui.screen.list.component.ProjectChildListItemComponentTestTags
import au.com.deanpike.uitestshared.util.assertTagDisplayed
import au.com.deanpike.uitestshared.util.assertTextDisplayed
import au.com.deanpike.uitestshared.util.clickOn
import au.com.deanpike.uitestshared.util.scrollTo

class ProjectChildListItemComponentAbility(private val composeTestRule: ComposeContentTestRule) {

    fun assertCardDisplayed(
        parentPosition: Int,
        position: Int
    ) {
        composeTestRule.assertTagDisplayed("${ProjectChildListItemComponentTestTags.PROJECT_CHILD_LIST_ITEM_LAYOUT}_${parentPosition}_${position}")
    }

    fun assertPriceDisplayed(
        parentPosition: Int,
        position: Int,
        text: String
    ) {
        composeTestRule.assertTextDisplayed(
            tag = "${ProjectChildListItemComponentTestTags.PROJECT_CHILD_LIST_ITEM_PRICE}_${parentPosition}_${position}",
            text = text
        )
    }

    fun clickCard(
        parentPosition: Int,
        position: Int
    ) {
        composeTestRule.clickOn(tag = "${ProjectChildListItemComponentTestTags.PROJECT_CHILD_LIST_ITEM_LAYOUT}_${parentPosition}_${position}")
    }

    fun scrollTo(
        parentPosition: Int,
        position: Int
    ) {
        composeTestRule.scrollTo("${ProjectChildListItemComponentTestTags.PROJECT_CHILD_LIST_ITEM_LAYOUT}_${parentPosition}_${position}")
    }
}