package au.com.deanpike.ui.framework.ability

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import au.com.deanpike.ui.screen.list.component.PropertyListItemTesTags
import au.com.deanpike.uitestshared.util.assertTagDisplayed
import au.com.deanpike.uitestshared.util.assertTextDisplayed
import au.com.deanpike.uitestshared.util.clickOn

class PropertyListItemAbility(private val composeTestRule: ComposeContentTestRule) {
    private val ability = PropertyDetailComponentAbility(composeTestRule)

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
        ability.assertBedroomDisplayed(
            position = position,
            text = "$bedroomCount"
        )
    }

    fun assertNumberOfBathrooms(position: Int, bathroomCount: Int) {
        ability.assertBathroomDisplayed(
            position = position,
            text = "$bathroomCount"
        )
    }

    fun assertNumberOfCarSpaces(position: Int, carSpaces: Int) {
        ability.assertCarSpaceDisplayed(
            position = position,
            text = "$carSpaces"
        )
    }

    fun assertDwellingType(position: Int, dwellingType: String) {
        ability.assertDwellingTypeDisplayed(
            position = position,
            text = dwellingType
        )
    }

    fun clickItem(position: Int) {
        composeTestRule.clickOn(tag = "${PropertyListItemTesTags.PROPERTY_LIST_ITEM_LAYOUT}_$position")
    }
}