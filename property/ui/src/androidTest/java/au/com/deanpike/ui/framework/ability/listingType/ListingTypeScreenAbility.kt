package au.com.deanpike.ui.framework.ability.listingType

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import au.com.deanpike.ui.screen.listingType.ListingTypeScreenTestTags
import au.com.deanpike.uitestshared.util.assertButtonWithTextDisplayed
import au.com.deanpike.uitestshared.util.assertIsOff
import au.com.deanpike.uitestshared.util.assertIsOn
import au.com.deanpike.uitestshared.util.assertTagDisplayed
import au.com.deanpike.uitestshared.util.assertTextDisplayed
import au.com.deanpike.uitestshared.util.clickOn

class ListingTypeScreenAbility(private val composeTestRule: ComposeContentTestRule) {
    fun assertListingTypeScreenDisplayed() {
        composeTestRule.assertTagDisplayed(ListingTypeScreenTestTags.LISTING_TYPE_SCREEN_LAYOUT)
    }

    // All
    fun assertAllDisplayed() {
        composeTestRule.assertTextDisplayed(tag = "${ListingTypeScreenTestTags.LISTING_TYPE_SCREEN_ALL}_TEXT", text = "All")
    }

    fun assertAllSelected(isSelected: Boolean) {
        if (isSelected) {
            composeTestRule.assertIsOn(tag = "${ListingTypeScreenTestTags.LISTING_TYPE_SCREEN_ALL}_CHECKBOX")
        } else {
            composeTestRule.assertIsOn(tag = "${ListingTypeScreenTestTags.LISTING_TYPE_SCREEN_ALL}_CHECKBOX")
        }
    }

    fun clickAll() {
        composeTestRule.clickOn("${ListingTypeScreenTestTags.LISTING_TYPE_SCREEN_ALL}_CHECKBOX")
    }

    // House
    fun assertHouseDisplayed() {
        composeTestRule.assertTextDisplayed(tag = "${ListingTypeScreenTestTags.LISTING_TYPE_SCREEN_HOUSE}_TEXT", text = "House")
    }

    fun assertHouseSelected(isSelected: Boolean) {
        if (isSelected) {
            composeTestRule.assertIsOn(tag = "${ListingTypeScreenTestTags.LISTING_TYPE_SCREEN_HOUSE}_CHECKBOX")
        } else {
            composeTestRule.assertIsOff(tag = "${ListingTypeScreenTestTags.LISTING_TYPE_SCREEN_HOUSE}_CHECKBOX")
        }
    }

    fun clickHouse() {
        composeTestRule.clickOn("${ListingTypeScreenTestTags.LISTING_TYPE_SCREEN_HOUSE}_CHECKBOX")
    }

    // TownHouse
    fun assertTownhouseDisplayed() {
        composeTestRule.assertTextDisplayed(tag = "${ListingTypeScreenTestTags.LISTING_TYPE_SCREEN_TOWNHOUSE}_TEXT", text = "Townhouse")
    }

    fun assertTownhouseSelected(isSelected: Boolean) {
        if (isSelected) {
            composeTestRule.assertIsOn(tag = "${ListingTypeScreenTestTags.LISTING_TYPE_SCREEN_TOWNHOUSE}_CHECKBOX")
        } else {
            composeTestRule.assertIsOff(tag = "${ListingTypeScreenTestTags.LISTING_TYPE_SCREEN_TOWNHOUSE}_CHECKBOX")
        }
    }

    fun clickTownhouse() {
        composeTestRule.clickOn("${ListingTypeScreenTestTags.LISTING_TYPE_SCREEN_TOWNHOUSE}_CHECKBOX")
    }

    // Apartment
    fun assertApartmentDisplayed() {
        composeTestRule.assertTextDisplayed(tag = "${ListingTypeScreenTestTags.LISTING_TYPE_SCREEN_APARTMENT}_TEXT", text = "Apartment / Unit / Flat")
    }

    fun assertApartmentSelected(isSelected: Boolean) {
        if (isSelected) {
            composeTestRule.assertIsOn(tag = "${ListingTypeScreenTestTags.LISTING_TYPE_SCREEN_APARTMENT}_CHECKBOX")
        } else {
            composeTestRule.assertIsOff(tag = "${ListingTypeScreenTestTags.LISTING_TYPE_SCREEN_APARTMENT}_CHECKBOX")
        }
    }

    fun clickApartment() {
        composeTestRule.clickOn("${ListingTypeScreenTestTags.LISTING_TYPE_SCREEN_APARTMENT}_CHECKBOX")
    }

    // Button
    fun assertApplyDisplayed() {
        composeTestRule.assertButtonWithTextDisplayed(tag = ListingTypeScreenTestTags.LISTING_TYPE_SCREEN_APPLY, text = "Apply")
    }

    fun clickApply() {
        composeTestRule.clickOn(tag = ListingTypeScreenTestTags.LISTING_TYPE_SCREEN_APPLY)
    }
}