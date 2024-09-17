package au.com.deanpike.ui.screen.list

import au.com.deanpike.commonshared.model.ListingDetails
import au.com.deanpike.datashared.type.ListingType
import au.com.deanpike.listings.client.model.listing.response.Project
import au.com.deanpike.listings.client.model.listing.response.ProjectChild
import au.com.deanpike.listings.client.model.listing.response.Property
import au.com.deanpike.ui.framework.robot.ListingListScreenRobot
import au.com.deanpike.ui.framework.robot.ListingListScreenRobotInitData
import au.com.deanpike.uishared.base.ScreenStateType
import au.com.deanpike.uitestshared.base.UiUnitTestBase
import au.com.deanpike.uitestshared.util.disableAnimations
import org.junit.Test

class ListingListScreenTest : UiUnitTestBase() {

    private val robot = ListingListScreenRobot(composeTestRule)

    @Test
    fun show_listings() {
        val propertyOne = getPropertyOne()
        val propertyTwo = getPropertyTwo()
        val project = getProject()

        composeTestRule.disableAnimations()

        robot
            .setupComponent(
                data = ListingListScreenRobotInitData(
                    state = ListingListScreenState(
                        screenState = ScreenStateType.SUCCESS,
                        listings = listOf(propertyOne, propertyTwo, project)
                    )
                )
            )
            .assertLayoutDisplayed()
            .assertHeaderText("3 Properties")
            .assertListDisplayed()
            .scrollToPosition(0)
            .assertPropertyDisplayedAtPosition(0)
            .scrollToPosition(1)
            .assertPropertyDisplayedAtPosition(0) // We have scrolled so this is now the first visible property
            .scrollToPosition(1)
            .assertProjectDisplayedAtPosition(1)
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
            lifecycleStatus = "New",
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
            lifecycleStatus = "Sold",
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