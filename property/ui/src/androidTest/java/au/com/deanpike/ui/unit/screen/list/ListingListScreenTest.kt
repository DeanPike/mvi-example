package au.com.deanpike.ui.unit.screen.list

import au.com.deanpike.client.model.listing.response.ListingDetails
import au.com.deanpike.client.model.listing.response.ListingType
import au.com.deanpike.client.model.listing.response.Project
import au.com.deanpike.client.model.listing.response.ProjectChild
import au.com.deanpike.client.model.listing.response.Property
import au.com.deanpike.ui.framework.ability.list.ListingListScreenAbility
import au.com.deanpike.ui.framework.ability.list.component.FilterComponentAbility
import au.com.deanpike.ui.framework.screen.ProjectListItemScreen
import au.com.deanpike.ui.framework.screen.PropertyListItemScreen
import au.com.deanpike.ui.screen.list.ListingListScreenContent
import au.com.deanpike.ui.screen.list.ListingListScreenState
import au.com.deanpike.uishared.base.ScreenStateType
import au.com.deanpike.uishared.theme.MviExampleTheme
import au.com.deanpike.uitestshared.base.UiUnitTestBase
import au.com.deanpike.uitestshared.util.advanceTimeAndWait
import org.junit.Test

class ListingListScreenTest : UiUnitTestBase() {

    private val listAbility = ListingListScreenAbility(composeTestRule)
    private val filterAbility = FilterComponentAbility(composeTestRule)
    private val propertyItemScreen = PropertyListItemScreen(composeTestRule)
    private val projectItemScreen = ProjectListItemScreen(composeTestRule)

    @Test
    fun show_listings() {
        val propertyOne = getPropertyOne()
        val propertyTwo = getPropertyTwo()
        val project = getProject()
        with(composeTestRule) {
            setContent {
                MviExampleTheme {
                    ListingListScreenContent(
                        state = ListingListScreenState(
                            screenState = ScreenStateType.SUCCESS,
                            listings = listOf(propertyOne, propertyTwo, project)
                        )
                    )
                }
            }
            advanceTimeAndWait()
        }

        with(listAbility) {
            assertListDisplayed()
        }

        with(filterAbility){
            assertStatusButtonDisplayed("Buy")
            assertListingTypeButtonDisplayed("Property types")
        }

        propertyItemScreen.assertPropertyDisplayed(
            position = 0,
            property = propertyOne
        )
        propertyItemScreen.assertPropertyDisplayed(
            position = 1,
            property = propertyTwo
        )
        projectItemScreen.assertProjectDisplayed(
            position = 2,
            project = project
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

    private fun getProject(): Project {
        val childOne = ProjectChild(
            id = 2222,
            listingType = ListingType.PROPERTY,
            listingDetails = ListingDetails(
                price = "$100,000",
                numberOfBedrooms = 3,
                numberOfBathrooms = 1,
                numberOfCarSpaces = 2
            )
        )
        val childTwo = ProjectChild(
            id = 3333,
            listingType = ListingType.PROPERTY,
            listingDetails = ListingDetails(
                price = "$357,000",
                numberOfBedrooms = 5,
                numberOfBathrooms = 2,
                numberOfCarSpaces = 4
            )
        )

        val project = Project(
            id = 1234,
            listingType = ListingType.PROJECT,
            address = "Project address",
            listingImage = "http://listing.image",
            bannerImage = "http://banner.image",
            logoImage = "http://logo.image",
            projectName = "Project name",
            projectColour = "White",
            properties = listOf(childOne, childTwo)
        )

        return project
    }
}