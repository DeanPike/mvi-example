package au.com.deanpike.ui.framework.screen

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import au.com.deanpike.ui.framework.ability.listingType.ListingTypeScreenAbility
import au.com.deanpike.ui.screen.list.ListingListScreenTestTags
import au.com.deanpike.uitestshared.util.advanceTimeAndWait
import au.com.deanpike.uitestshared.util.waitUntilTagExists

class ListingTypeScreen(private val composeTestRule: ComposeContentTestRule) {
    private val ability = ListingTypeScreenAbility(composeTestRule)

    fun assertDefaultScreenDisplayed() {
        with(ability) {
            assertListingTypeScreenDisplayed()

            assertAllDisplayed()
            assertAllSelected(true)

            assertHouseDisplayed()
            assertHouseSelected(false)

            assertTownhouseDisplayed()
            assertTownhouseSelected(false)

            assertApplyDisplayed()
            assertApartmentSelected(false)

            assertApplyDisplayed()
        }
    }

    fun selectHouseListingType() {
        with(ability) {
            assertHouseDisplayed()
            clickHouse()
            composeTestRule.advanceTimeAndWait()
            clickApply()
            composeTestRule.waitUntilTagExists(tag = ListingListScreenTestTags.LISTING_LIST, timeout = 2000)
        }
    }
}