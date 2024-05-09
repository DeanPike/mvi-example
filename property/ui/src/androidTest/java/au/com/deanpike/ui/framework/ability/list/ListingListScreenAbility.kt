package au.com.deanpike.ui.framework.ability.list

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import au.com.deanpike.ui.screen.list.ListingListScreenTestTags
import au.com.deanpike.ui.screen.list.ListingListScreenTestTags.LISTING_LIST
import au.com.deanpike.uitestshared.util.assertTagDisplayed
import au.com.deanpike.uitestshared.util.assertTextDisplayed

class ListingListScreenAbility(private val composeTestRule: ComposeContentTestRule) {
    fun assertListDisplayed() {
        composeTestRule.assertTagDisplayed(LISTING_LIST)
    }

    fun assertHeadingDisplayed(text: String) {
        composeTestRule.assertTextDisplayed(tag = ListingListScreenTestTags.LISTING_LIST_HEADING, text = text)
    }
}