package au.com.deanpike.listings.ui.robot

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import au.com.deanpike.listings.client.model.listing.response.Property
import au.com.deanpike.listings.ui.list.ListingListScreenEvent
import au.com.deanpike.listings.ui.list.component.PropertyCard
import au.com.deanpike.listings.ui.list.component.PropertyCardTestTags.PROPERTY_CARD_ADDRESS
import au.com.deanpike.listings.ui.list.component.PropertyCardTestTags.PROPERTY_CARD_DESCRIPTION
import au.com.deanpike.listings.ui.list.component.PropertyCardTestTags.PROPERTY_CARD_HEADLINE
import au.com.deanpike.listings.ui.list.component.PropertyCardTestTags.PROPERTY_CARD_IMAGE
import au.com.deanpike.listings.ui.list.component.PropertyCardTestTags.PROPERTY_CARD_LAYOUT
import au.com.deanpike.listings.ui.list.component.PropertyCardTestTags.PROPERTY_CARD_PRICE
import au.com.deanpike.uishared.theme.MviExampleTheme
import au.com.deanpike.uitestshared.base.TestRobotBase
import au.com.deanpike.uitestshared.base.TestRobotInitData
import au.com.deanpike.uitestshared.util.advanceTimeAndWait
import au.com.deanpike.uitestshared.util.assertTagDisplayed
import au.com.deanpike.uitestshared.util.assertTextDisplayed
import au.com.deanpike.uitestshared.util.clickOn
import au.com.deanpike.uitestshared.util.disableAnimations

class PropertyCardRobot(composeRule: ComposeContentTestRule) : TestRobotBase<PropertyCardRobot, PropertyCardRobotInitData>(composeRule) {

    var propertySelectedId: Long? = null
        private set

    override fun setupComponent(data: PropertyCardRobotInitData?) = apply {
        composeRule.setContent {
            MviExampleTheme {
                PropertyCard(
                    property = data!!.property,
                    onEvent = { event ->
                        when (event) {
                            is ListingListScreenEvent.OnPropertySelected -> propertySelectedId = event.id
                            else -> {}
                        }
                    }
                )
            }
        }
        composeRule.disableAnimations()
    }

    override fun assertLayoutDisplayed() = apply {
        composeRule.advanceTimeAndWait()
        composeRule.assertTagDisplayed(PROPERTY_CARD_LAYOUT)
    }

    fun assertImageDisplayed() = apply {
        composeRule.assertTagDisplayed(PROPERTY_CARD_IMAGE)
    }

    fun assertPriceDisplayed(price: String) = apply {
        composeRule.assertTextDisplayed(tag = PROPERTY_CARD_PRICE, text = price)
    }

    fun assertDescriptionDisplayed(description: String) = apply {
        composeRule.assertTextDisplayed(tag = PROPERTY_CARD_DESCRIPTION, text = description)
    }

    fun assertAddressDisplayed(address: String) = apply {
        composeRule.assertTextDisplayed(tag = PROPERTY_CARD_ADDRESS, text = address)
    }

    fun assertHeadlineDisplayed(headline: String) = apply {
        composeRule.assertTextDisplayed(tag = PROPERTY_CARD_HEADLINE, text = headline)
    }

    fun clickPropertyCard() = apply {
        composeRule.clickOn(PROPERTY_CARD_LAYOUT)
        composeRule.advanceTimeAndWait()
    }
}

data class PropertyCardRobotInitData(
    val property: Property
) : TestRobotInitData