package au.com.deanpike.ui.screen.list.component

import au.com.deanpike.client.model.listing.response.ListingDetails
import au.com.deanpike.client.model.listing.response.ListingType
import au.com.deanpike.client.model.listing.response.Property
import au.com.deanpike.ui.ability.PropertyListItemAbility
import au.com.deanpike.uishared.theme.MviExampleTheme
import au.com.deanpike.uitestshared.base.UiTestBase
import dagger.hilt.android.testing.HiltAndroidTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

@HiltAndroidTest
class PropertyListItemTest : UiTestBase() {
    private val ability = PropertyListItemAbility(composeTestRule)

    @Test
    fun show_property_list_item() {
        hiltRule.inject()
        val property = getProperty()
        var wasItemClicked = false
        with(composeTestRule) {
            setContent {
                MviExampleTheme {
                    PropertyListItem(
                        position = 0,
                        property = property,
                        onItemClicked = {
                            wasItemClicked = true
                        }
                    )
                }
            }
            advanceTimeAndWait(this)

            with(ability) {
                assertItemDisplayed(0)
                assertPropertyImageDisplayed(0)
                assertAgencyImageDisplayed(0)
                assertPriceDisplayed(position = 0, price = "$1000000")
                assertHeadlineDisplayed(position = 0, headline = "Property headline")
                assertAddressDisplayed(position = 0, address = "Property address")
                assertNumberOfBedrooms(position = 0, bedroomCount = 4)
                assertNumberOfBathrooms(position = 0, bathroomCount = 3)
                assertNumberOfCarSpaces(position = 0, carSpaces = 2)
                assertDwellingType(position = 0, dwellingType = "House")

                clickItem(0)
                assertThat(wasItemClicked).isTrue()
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