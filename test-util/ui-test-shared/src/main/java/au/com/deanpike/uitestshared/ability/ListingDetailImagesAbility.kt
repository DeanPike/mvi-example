package au.com.deanpike.uitestshared.ability

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import au.com.deanpike.uishared.component.ListingDetailImagesTestTags.LISTING_DETAIL_IMAGES_PAGER
import au.com.deanpike.uitestshared.util.assertTagDisplayed

class ListingDetailImagesAbility(private val composeTestRule: ComposeContentTestRule) {
    fun assertImagesDisplayed() {
        composeTestRule.assertTagDisplayed(LISTING_DETAIL_IMAGES_PAGER)
    }
}