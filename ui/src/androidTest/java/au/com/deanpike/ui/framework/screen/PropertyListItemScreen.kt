package au.com.deanpike.ui.framework.screen

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import au.com.deanpike.client.model.listing.response.Project
import au.com.deanpike.client.model.listing.response.Property
import au.com.deanpike.ui.framework.ability.list.component.PropertyListItemAbility

class PropertyListItemScreen(private val composeTestRule: ComposeContentTestRule) {
    private val ability = PropertyListItemAbility(composeTestRule)

    fun assertPropertyDisplayed(
        position: Int,
        property: Property
    ) {
        with(ability) {
            scrollTo(position)
            assertItemDisplayed(position)
            assertPropertyImageDisplayed(position)
            assertAgencyImageDisplayed(position)
            assertPriceDisplayed(position = position, price = property.detail.price!!)
            assertHeadlineDisplayed(position = position, headline = property.headLine!!)
            assertAddressDisplayed(position = position, address = property.address)
            assertNumberOfBedrooms(
                parentPosition = position,
                position = position,
                bedroomCount = property.detail.numberOfBedrooms!!
            )
            assertNumberOfBathrooms(
                parentPosition = position,
                position = position,
                bathroomCount = property.detail.numberOfBathrooms!!
            )
            assertNumberOfCarSpaces(
                parentPosition = position,
                position = position,
                carSpaces = property.detail.numberOfCarSpaces!!
            )
            assertDwellingType(
                parentPosition = position,
                position = position,
                dwellingType = property.dwellingType!!
            )
        }
    }

    fun assertProjectDisplayed(
        position: Int,
        project: Project
    ) {

    }
}