package au.com.deanpike.ui.framework.ability

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import au.com.deanpike.ui.screen.list.ListingListScreenTestTags.LISTING_LIST
import au.com.deanpike.uitestshared.util.assertTagDisplayed

class ListingListScreenAbility(private val composeTestRule: ComposeContentTestRule) {
    fun assertListDisplayed() {
        composeTestRule.assertTagDisplayed(LISTING_LIST)
    }
}