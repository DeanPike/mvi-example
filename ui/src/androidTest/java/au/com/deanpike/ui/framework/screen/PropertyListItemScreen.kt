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
            assertPriceDisplayed(position = 0, price = property.detail.price!!)
            assertHeadlineDisplayed(position = 0, headline = property.headLine!!)
            assertAddressDisplayed(position = 0, address = property.address)
            assertNumberOfBedrooms(position = 0, bedroomCount = property.detail.numberOfBedrooms!!)
            assertNumberOfBathrooms(position = 0, bathroomCount = property.detail.numberOfBathrooms!!)
            assertNumberOfCarSpaces(position = 0, carSpaces = property.detail.numberOfCarSpaces!!)
            assertDwellingType(position = 0, dwellingType = property.dwellingType!!)
        }
    }
}