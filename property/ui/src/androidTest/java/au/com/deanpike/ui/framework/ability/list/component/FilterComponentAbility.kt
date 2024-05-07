package au.com.deanpike.ui.framework.ability.list.component

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import au.com.deanpike.ui.R
import au.com.deanpike.ui.screen.list.component.FilterComponentTestTags
import au.com.deanpike.ui.screen.list.component.FilterComponentTestTags.STATUS_BUTTON
import au.com.deanpike.uitestshared.util.assertDrawableDisplayed
import au.com.deanpike.uitestshared.util.assertTagDisplayed
import au.com.deanpike.uitestshared.util.assertTextDisplayed
import au.com.deanpike.uitestshared.util.clickOn

class FilterComponentAbility(private val composeTestRule: ComposeContentTestRule) {

    fun assertStatusButtonDisplayed(status: String) {
        composeTestRule.assertTagDisplayed(STATUS_BUTTON)
        composeTestRule.assertTextDisplayed(tag = "${STATUS_BUTTON}_TEXT", text = status)
        composeTestRule.assertDrawableDisplayed(tag = "${STATUS_BUTTON}_ICON", drawable = R.drawable.arrow_drop_down)
    }

    fun assertBuyStatusItemDisplayed() {
        composeTestRule.assertTagDisplayed("${FilterComponentTestTags.STATUS_ITEM}_BUY")
        composeTestRule.assertTextDisplayed(tag = "${FilterComponentTestTags.STATUS_ITEM}_BUY_TEXT", text = "Buy")
    }

    fun assertRentStatusItemDisplayed() {
        composeTestRule.assertTagDisplayed("${FilterComponentTestTags.STATUS_ITEM}_RENT")
        composeTestRule.assertTextDisplayed(tag = "${FilterComponentTestTags.STATUS_ITEM}_RENT_TEXT", text = "Rent")
    }

    fun assertSoldStatusItemDisplayed() {
        composeTestRule.assertTagDisplayed("${FilterComponentTestTags.STATUS_ITEM}_SOLD")
        composeTestRule.assertTextDisplayed(tag = "${FilterComponentTestTags.STATUS_ITEM}_SOLD_TEXT", text = "Sold")
    }

    fun clickStatusButton() {
        composeTestRule.clickOn(STATUS_BUTTON)
    }

    fun clickBuyStatus() {
        composeTestRule.clickOn("${FilterComponentTestTags.STATUS_ITEM}_BUY")
    }

    fun clickRentStatus() {
        composeTestRule.clickOn("${FilterComponentTestTags.STATUS_ITEM}_RENT")
    }

    fun clickSoldStatus() {
        composeTestRule.clickOn("${FilterComponentTestTags.STATUS_ITEM}_SOLD")
    }

    fun assertListingTypeButtonDisplayed(text: String) {
        composeTestRule.assertTagDisplayed(FilterComponentTestTags.LISTING_TYPE)
        composeTestRule.assertTextDisplayed(tag = "${FilterComponentTestTags.LISTING_TYPE}_TEXT", text = text)
    }

    fun clickListingTypeButton(){
        composeTestRule.clickOn(FilterComponentTestTags.LISTING_TYPE)
    }
}