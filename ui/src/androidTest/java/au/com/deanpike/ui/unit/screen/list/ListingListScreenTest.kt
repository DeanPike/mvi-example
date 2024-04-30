package au.com.deanpike.ui.unit.screen.list

import au.com.deanpike.client.model.listing.response.ListingDetails
import au.com.deanpike.client.model.listing.response.ListingType
import au.com.deanpike.client.model.listing.response.Property
import au.com.deanpike.ui.framework.ability.ListingListScreenAbility
import au.com.deanpike.ui.framework.screen.PropertyListItemScreen
import au.com.deanpike.ui.screen.list.ListingListScreenContent
import au.com.deanpike.ui.screen.list.ListingListScreenState
import au.com.deanpike.ui.screen.shared.DetailListItemTestTags
import au.com.deanpike.uishared.base.ScreenStateType
import au.com.deanpike.uishared.theme.MviExampleTheme
import au.com.deanpike.uitestshared.base.UiUnitTestBase
import au.com.deanpike.uitestshared.util.advanceTimeAndWait
import au.com.deanpike.uitestshared.util.scrollTo
import org.junit.Test

class ListingListScreenTest : UiUnitTestBase() {

    private val listAbility = ListingListScreenAbility(composeTestRule)
    private val propertyItemScreen = PropertyListItemScreen(composeTestRule)

    @Test
    fun show_listings() {
        val propertyOne = getPropertyOne()
        val propertyTwo = getPropertyTwo()
        with(composeTestRule) {
            setContent {
                MviExampleTheme {
                    ListingListScreenContent(
                        state = ListingListScreenState(
                            screenState = ScreenStateType.SUCCESS,
                            listings = listOf(propertyOne, propertyTwo)
                        )
                    )
                }
            }
            advanceTimeAndWait()
        }

        with(listAbility) {
            assertListDisplayed()
        }

        propertyItemScreen.assertPropertyDisplayed(
            position = 0,
            property = propertyOne
        )

        composeTestRule.scrollTo(
            tag = "${DetailListItemTestTags.DETAIL_ITEM_GROUP}_1"
        )
        propertyItemScreen.assertPropertyDisplayed(
            position = 1,
            property = propertyTwo
        )
    }

    private fun getPropertyOne(): Property {
        return Property(
            id = 1,
            listingType = ListingType.PROPERTY,
            address = "Property address one",
            listingImage = "http://listing.image1",
            agencyImage = "http://agency.image1",
            dwellingType = "House",
            headLine = "Property headline one",
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

    private fun getPropertyTwo(): Property {
        return Property(
            id = 2,
            listingType = ListingType.PROPERTY,
            address = "Property address two",
            listingImage = "http://listing.image2",
            agencyImage = "http://agency.image2",
            dwellingType = "Apartment",
            headLine = "Property headline two",
            lifecycleStatus = "Sold",
            agencyColour = "Blue",
            detail = ListingDetails(
                price = "$100,500",
                numberOfBedrooms = 5,
                numberOfBathrooms = 4,
                numberOfCarSpaces = 3
            )
        )
    }
}