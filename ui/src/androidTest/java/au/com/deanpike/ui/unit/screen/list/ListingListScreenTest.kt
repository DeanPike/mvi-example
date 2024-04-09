package au.com.deanpike.ui.unit.screen.list

import au.com.deanpike.client.model.listing.response.ListingDetails
import au.com.deanpike.client.model.listing.response.ListingType
import au.com.deanpike.client.model.listing.response.Property
import au.com.deanpike.ui.framework.ability.ListingListScreenAbility
import au.com.deanpike.ui.framework.screen.PropertyListItemScreen
import au.com.deanpike.ui.screen.list.ListingListScreenContent
import au.com.deanpike.ui.screen.list.ListingListScreenState
import au.com.deanpike.uishared.base.ScreenStateType
import au.com.deanpike.uishared.theme.MviExampleTheme
import au.com.deanpike.uitestshared.base.UiUnitTestBase
import org.junit.Test

class ListingListScreenTest : UiUnitTestBase() {

    private val listAbility = ListingListScreenAbility(composeTestRule)
    private val propertyItemScreen = PropertyListItemScreen(composeTestRule)

    @Test
    fun show_listings() {
        val property = getProperty()
        with(composeTestRule) {
            setContent {
                MviExampleTheme {
                    ListingListScreenContent(
                        state = ListingListScreenState(
                            screenState = ScreenStateType.SUCCESS,
                            listings = listOf(property)
                        )
                    )
                }
            }
            advanceTimeAndWait(this)
        }

        with(listAbility) {
            assertListDisplayed()
        }

        propertyItemScreen.assertPropertyDisplayed(
            position = 0,
            property = property
        )
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