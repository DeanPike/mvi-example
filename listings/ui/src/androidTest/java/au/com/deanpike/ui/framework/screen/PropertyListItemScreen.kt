package au.com.deanpike.ui.framework.screen

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import au.com.deanpike.listings.client.model.listing.response.Property
import au.com.deanpike.ui.framework.ability.list.component.PropertyListItemAbility
import au.com.deanpike.uitestshared.ability.AgencyBannerAbility
import au.com.deanpike.uitestshared.ability.LifecycleStatusAbility

class PropertyListItemScreen(composeTestRule: ComposeContentTestRule) {
    private val listItemAbility = PropertyListItemAbility(composeTestRule)
    private val lifecycleStatusAbility = LifecycleStatusAbility(composeTestRule)
    private val agentBannerAbility = AgencyBannerAbility(composeTestRule)

    fun assertPropertyDisplayed(
        position: Int,
        property: Property
    ) {
        with(listItemAbility) {
            scrollTo(position)
            assertItemDisplayed(position)
            assertPropertyImageDisplayed(position)
            lifecycleStatusAbility.assertLifecycleDisplayed(position = position, text = property.lifecycleStatus ?: "")
            agentBannerAbility.assertAgencyImageDisplayed(position)
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
}