package au.com.deanpike.ui.framework.ability.list.component

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import au.com.deanpike.ui.screen.list.ListingListScreenTestTags
import au.com.deanpike.ui.screen.list.component.ProjectListItemTesTags
import au.com.deanpike.uitestshared.util.assertTagDisplayed
import au.com.deanpike.uitestshared.util.assertTextDisplayed
import au.com.deanpike.uitestshared.util.clickOn
import au.com.deanpike.uitestshared.util.scrollTo
import au.com.deanpike.uitestshared.util.scrollToItemPosition

class ProjectListItemAbility(private val composeTestRule: ComposeContentTestRule) {

    fun assertProjectDisplayed(position: Int) {
        composeTestRule.assertTagDisplayed("${ProjectListItemTesTags.PROJECT_LIST_ITEM_LAYOUT}_$position")
    }

    fun assertBannerImageDisplayed(position: Int) {
        composeTestRule.assertTagDisplayed("${ProjectListItemTesTags.PROJECT_LIST_ITEM_BANNER_IMAGE}_$position")
    }

    fun assertProjectImageDisplayed(position: Int) {
        composeTestRule.assertTagDisplayed("${ProjectListItemTesTags.PROJECT_LIST_ITEM_IMAGE}_$position")
    }


    fun assertProjectName(
        position: Int,
        text: String
    ) {
        composeTestRule.assertTextDisplayed(
            tag = "${ProjectListItemTesTags.PROJECT_LIST_ITEM_PROJECT_NAME}_$position",
            text = text
        )
    }

    fun assertAddress(
        position: Int,
        text: String
    ) {
        composeTestRule.assertTextDisplayed(
            tag = "${ProjectListItemTesTags.PROJECT_LIST_ITEM_ADDRESS}_$position",
            text = text
        )
    }

    fun assertProjectChildrenButtonText(
        position: Int,
        text: String
    ) {
        composeTestRule.assertTextDisplayed(
            tag = "${ProjectListItemTesTags.PROJECT_LIST_ITEM_CHILD_COUNT}_$position",
            text = text
        )
    }

    fun clickProjectChildButton(position: Int) {
        composeTestRule.clickOn("${ProjectListItemTesTags.PROJECT_LIST_ITEM_CHILD_BUTTON}_$position")
    }

    fun clickProject(position: Int) {
        composeTestRule.clickOn("${ProjectListItemTesTags.PROJECT_LIST_ITEM_LAYOUT}_$position")
    }

    fun scrollTo(position: Int) {
        composeTestRule.scrollToItemPosition(ListingListScreenTestTags.LISTING_LIST, position)
        composeTestRule.scrollTo("${ProjectListItemTesTags.PROJECT_LIST_ITEM_CHILD_BUTTON}_$position")
    }
}