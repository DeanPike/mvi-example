package au.com.deanpike.ui.framework.ability

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import au.com.deanpike.ui.R
import au.com.deanpike.ui.screen.list.component.PropertyListItemTesTags
import au.com.deanpike.uitestshared.util.assertDrawableDisplayed
import au.com.deanpike.uitestshared.util.assertTagDisplayed
import au.com.deanpike.uitestshared.util.assertTextDisplayed
import au.com.deanpike.uitestshared.util.clickOn

class PropertyListItemAbility(private val composeTestRule: ComposeContentTestRule) {
    fun assertItemDisplayed(position: Int) {
        composeTestRule.assertTagDisplayed(tag = "${PropertyListItemTesTags.PROPERTY_LIST_ITEM_LAYOUT}_$position")
    }

    fun assertPropertyImageDisplayed(position: Int) {
        composeTestRule.assertTagDisplayed(tag = "${PropertyListItemTesTags.PROPERTY_LIST_ITEM_PROPERTY_IMAGE}_$position")
    }

    fun assertAgencyImageDisplayed(position: Int) {
        composeTestRule.assertTagDisplayed("${PropertyListItemTesTags.PROPERTY_LIST_ITEM_AGENCY_IMAGE}_$position")
    }

    fun assertPriceDisplayed(position: Int, price: String) {
        composeTestRule.assertTextDisplayed(tag = "${PropertyListItemTesTags.PROPERTY_LIST_ITEM_PRICE}_$position", text = price)
    }

    fun assertHeadlineDisplayed(position: Int, headline: String) {
        composeTestRule.assertTextDisplayed(tag = "${PropertyListItemTesTags.PROPERTY_LIST_ITEM_HEADLINE}_$position", text = headline)
    }

    fun assertAddressDisplayed(position: Int, address: String) {
        composeTestRule.assertTextDisplayed(tag = "${PropertyListItemTesTags.PROPERTY_LIST_ITEM_ADDRESS}_$position", text = address)
    }

    fun assertNumberOfBedrooms(position: Int, bedroomCount: Int) {
        composeTestRule.assertDrawableDisplayed(
            tag = "${PropertyListItemTesTags.PROPERTY_LIST_ITEM_BEDROOMS}_ICON_$position",
            drawable = R.drawable.bed_outline
        )
        composeTestRule.assertTagDisplayed(tag = "${PropertyListItemTesTags.PROPERTY_LIST_ITEM_BEDROOMS}_ICON_$position")
        composeTestRule.assertTextDisplayed(tag = "${PropertyListItemTesTags.PROPERTY_LIST_ITEM_BEDROOMS}_TEXT_$position", text = "$bedroomCount")
    }

    fun assertNumberOfBathrooms(position: Int, bathroomCount: Int) {
        composeTestRule.assertDrawableDisplayed(
            tag = "${PropertyListItemTesTags.PROPERTY_LIST_ITEM_BATHROOMS}_ICON_$position",
            drawable = R.drawable.bath_outline
        )
        composeTestRule.assertTagDisplayed(tag = "${PropertyListItemTesTags.PROPERTY_LIST_ITEM_BATHROOMS}_ICON_$position")
        composeTestRule.assertTextDisplayed(tag = "${PropertyListItemTesTags.PROPERTY_LIST_ITEM_BATHROOMS}_TEXT_$position", text = "$bathroomCount")
    }

    fun assertNumberOfCarSpaces(position: Int, carSpaces: Int) {
        composeTestRule.assertDrawableDisplayed(
            tag = "${PropertyListItemTesTags.PROPERTY_LIST_ITEM_CAR_SPACES}_ICON_$position",
            drawable = R.drawable.car_outline
        )
        composeTestRule.assertTagDisplayed(tag = "${PropertyListItemTesTags.PROPERTY_LIST_ITEM_CAR_SPACES}_ICON_$position")
        composeTestRule.assertTextDisplayed(tag = "${PropertyListItemTesTags.PROPERTY_LIST_ITEM_CAR_SPACES}_TEXT_$position", text = "$carSpaces")
    }

    fun assertDwellingType(position: Int, dwellingType: String) {
        composeTestRule.assertTextDisplayed(tag = "${PropertyListItemTesTags.PROPERTY_LIST_ITEM_DWELLING_TYPE}_$position", text = dwellingType)
    }

    fun clickItem(position: Int) {
        composeTestRule.clickOn(tag = "${PropertyListItemTesTags.PROPERTY_LIST_ITEM_LAYOUT}_$position")
    }
}