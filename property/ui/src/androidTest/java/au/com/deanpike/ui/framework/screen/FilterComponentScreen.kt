package au.com.deanpike.ui.framework.screen

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import au.com.deanpike.ui.framework.ability.list.component.FilterComponentAbility
import au.com.deanpike.ui.screen.list.ListingListScreenTestTags
import au.com.deanpike.uitestshared.util.advanceTimeAndWait
import au.com.deanpike.uitestshared.util.waitUntilTagExists

class FilterComponentScreen(private val composeTestRule: ComposeContentTestRule) {
    private val listingTypeScreen = ListingTypeScreen(composeTestRule)
    private val filterAbility = FilterComponentAbility(composeTestRule)

    fun assertFilterComponentDisplayed() {
        filterAbility.assertStatusButtonDisplayed("Buy")
        filterAbility.assertListingTypeButtonDisplayed("Property types")
    }

    fun assertStatusItemsDisplayed() {
        with(filterAbility) {
            clickStatusButton()
            composeTestRule.advanceTimeAndWait()
            assertBuyStatusItemDisplayed()
            assertRentStatusItemDisplayed()
            assertSoldStatusItemDisplayed()
        }
    }

    fun selectSoldStatus() {
        with(filterAbility) {
            clickSoldStatus()
            composeTestRule.waitUntilTagExists(tag = ListingListScreenTestTags.LISTING_LIST, timeout = 2000)
            assertStatusButtonDisplayed("Sold")
        }
    }

    fun assertListingTypesDisplayed() {
        filterAbility.clickListingTypeButton()
        composeTestRule.advanceTimeAndWait(2000)

        listingTypeScreen.assertDefaultScreenDisplayed()
    }

    fun selectHouseListingType() {
        listingTypeScreen.selectHouseListingType()
        filterAbility.assertListingTypeButtonDisplayed("1 Property type")
    }
}