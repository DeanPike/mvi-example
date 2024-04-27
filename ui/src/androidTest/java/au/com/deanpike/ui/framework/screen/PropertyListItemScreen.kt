package au.com.deanpike.ui.framework.screen

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import au.com.deanpike.client.model.listing.response.Property
import au.com.deanpike.ui.framework.ability.PropertyListItemAbility

class PropertyListItemScreen(private val composeTestRule: ComposeContentTestRule) {
    private val ability = PropertyListItemAbility(composeTestRule)

    fun assertPropertyDisplayed(
        position: Int,
        property: Property
    ) {
        with(ability) {
            assertItemDisplayed(position)
            assertPropertyImageDisplayed(position)
            assertAgencyImageDisplayed(position)
            assertPriceDisplayed(position = position, price = property.detail.price!!)
            assertHeadlineDisplayed(position = position, headline = property.headLine!!)
            assertAddressDisplayed(position = position, address = property.address)
            assertNumberOfBedrooms(position = position, bedroomCount = property.detail.numberOfBedrooms!!)
            assertNumberOfBathrooms(position = position, bathroomCount = property.detail.numberOfBathrooms!!)
            assertNumberOfCarSpaces(position = position, carSpaces = property.detail.numberOfCarSpaces!!)
            assertDwellingType(position = position, dwellingType = property.dwellingType!!)
        }
    }
}