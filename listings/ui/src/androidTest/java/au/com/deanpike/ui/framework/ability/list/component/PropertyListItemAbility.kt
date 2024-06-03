package au.com.deanpike.ui.framework.ability.list.component

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import au.com.deanpike.ui.framework.ability.shared.PropertyDetailComponentAbility
import au.com.deanpike.ui.screen.list.ListingListScreenTestTags
import au.com.deanpike.ui.screen.list.component.PropertyListItemTesTags
import au.com.deanpike.ui.screen.shared.DetailListItemTestTags
import au.com.deanpike.uitestshared.util.assertTagDisplayed
import au.com.deanpike.uitestshared.util.assertTextDisplayed
import au.com.deanpike.uitestshared.util.clickOn
import au.com.deanpike.uitestshared.util.scrollTo
import au.com.deanpike.uitestshared.util.scrollToItemPosition

class PropertyListItemAbility(private val composeTestRule: ComposeContentTestRule) {
    private val ability = PropertyDetailComponentAbility(composeTestRule)

    fun assertItemDisplayed(position: Int) {
        composeTestRule.assertTagDisplayed(tag = "${PropertyListItemTesTags.PROPERTY_LIST_ITEM_LAYOUT}_$position")
    }

    fun assertPropertyImageDisplayed(position: Int) {
        composeTestRule.assertTagDisplayed(tag = "${PropertyListItemTesTags.PROPERTY_LIST_ITEM_PROPERTY_IMAGE}_$position")
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

    fun assertNumberOfBedrooms(
        parentPosition: Int,
        position: Int,
        bedroomCount: Int
    ) {
        ability.assertBedroomDisplayed(
            parentPosition = parentPosition,
            position = position,
            text = "$bedroomCount"
        )
    }

    fun assertNumberOfBathrooms(
        parentPosition: Int,
        position: Int,
        bathroomCount: Int
    ) {
        ability.assertBathroomDisplayed(
            parentPosition = parentPosition,
            position = position,
            text = "$bathroomCount"
        )
    }

    fun assertNumberOfCarSpaces(
        parentPosition: Int,
        position: Int,
        carSpaces: Int
    ) {
        ability.assertCarSpaceDisplayed(
            parentPosition = parentPosition,
            position = position,
            text = "$carSpaces"
        )
    }

    fun assertDwellingType(
        parentPosition: Int,
        position: Int,
        dwellingType: String
    ) {
        ability.assertDwellingTypeDisplayed(
            parentPosition = parentPosition,
            position = position,
            text = dwellingType
        )
    }

    fun clickItem(position: Int) {
        composeTestRule.clickOn(tag = "${PropertyListItemTesTags.PROPERTY_LIST_ITEM_LAYOUT}_$position")
    }

    fun scrollTo(position: Int) {
        composeTestRule.scrollToItemPosition(ListingListScreenTestTags.LISTING_LIST, position)
        composeTestRule.scrollTo("${DetailListItemTestTags.DETAIL_ITEM_GROUP}_${position}_${position}")
    }
}