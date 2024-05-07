package au.com.deanpike.ui.unit.screen.list.component

import au.com.deanpike.client.model.listing.response.ListingDetails
import au.com.deanpike.client.model.listing.response.ListingType
import au.com.deanpike.client.model.listing.response.Property
import au.com.deanpike.ui.framework.ability.list.component.PropertyListItemAbility
import au.com.deanpike.ui.screen.list.component.PropertyListItem
import au.com.deanpike.uishared.theme.MviExampleTheme
import au.com.deanpike.uitestshared.base.UiUnitTestBase
import au.com.deanpike.uitestshared.util.advanceTimeAndWait
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class PropertyListItemTest : UiUnitTestBase() {
    private val ability = PropertyListItemAbility(composeTestRule)

    @Test
    fun show_property_list_item() {
        val property = getProperty()
        var clickedId: Long? = null

        with(composeTestRule) {
            setContent {
                MviExampleTheme {
                    PropertyListItem(
                        position = 0,
                        property = property,
                        onItemClicked = {
                            clickedId = it
                        }
                    )
                }
            }
            advanceTimeAndWait()

            with(ability) {
                assertItemDisplayed(0)
                assertPropertyImageDisplayed(0)
                assertAgencyImageDisplayed(0)
                assertLifecycleDisplayed(0, "New")
                assertPriceDisplayed(position = 0, price = "$1000000")
                assertHeadlineDisplayed(position = 0, headline = "Property headline")
                assertAddressDisplayed(position = 0, address = "Property address")
                assertNumberOfBedrooms(
                    parentPosition = 0,
                    position = 0,
                    bedroomCount = 4
                )
                assertNumberOfBathrooms(
                    parentPosition = 0,
                    position = 0,
                    bathroomCount = 3
                )
                assertNumberOfCarSpaces(
                    parentPosition = 0,
                    position = 0,
                    carSpaces = 2
                )
                assertDwellingType(
                    parentPosition = 0,
                    position = 0,
                    dwellingType = "House"
                )

                clickItem(0)
                assertThat(clickedId).isEqualTo(1)
            }
        }
    }

    private fun getProperty(): Property {
        return Property(
            id = 1,
            listingType = ListingType.PROPERTY,
            address = "Property address",
            listingImage = "http://listing.image",
            agencyImage = "http://agency.image",
            dwellingType = "House",
            headLine = "Property headline",
            lifecycleStatus = "New",
            agencyColour = "White",
            detail = ListingDetails(
                price = "$1000000",
                numberOfBedrooms = 4,
                numberOfBathrooms = 3,
                numberOfCarSpaces = 2
            )
        )
    }

}