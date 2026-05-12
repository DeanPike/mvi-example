package au.com.deanpike.listings.ui.list.component

import au.com.deanpike.commonshared.model.ListingDetails
import au.com.deanpike.datashared.type.ListingType
import au.com.deanpike.listings.client.model.listing.response.ProjectChild
import au.com.deanpike.listings.ui.robot.ProjectChildCardRobot
import au.com.deanpike.listings.ui.robot.ProjectChildCardRobotInitData
import au.com.deanpike.uitestshared.base.RobolectricTestBase
import org.junit.Assert.assertEquals
import org.junit.Test

class ProjectChildCardTest : RobolectricTestBase() {
    private val robot = ProjectChildCardRobot(composeTestRule)

    @Test
    fun `screen should be displayed`() {
        robot
            .setupComponent(
                data = ProjectChildCardRobotInitData(
                    projectChild = ProjectChild(
                        id = 1111L,
                        listingType = ListingType.PROPERTY,
                        lifecycleStatus = "New",
                        listingDetails = ListingDetails(
                            price = "Offers Above $659,275",
                            numberOfBedrooms = 3,
                            numberOfBathrooms = 2,
                            numberOfCarSpaces = 1
                        )
                    )
                )
            )
            .assertLayoutDisplayed()
            .assertPriceDisplayed("Offers Above $659,275")
            .assertDetailsDisplayed("3 Bed • 2 Bath • 1 Car")
            .assertLifecycleStatusDisplayed("New")
    }

    @Test
    fun `price should not be displayed when null`() {
        robot
            .setupComponent(
                data = ProjectChildCardRobotInitData(
                    projectChild = ProjectChild(
                        id = 2222L,
                        listingType = ListingType.PROPERTY,
                        lifecycleStatus = "Available",
                        listingDetails = ListingDetails(
                            price = null,
                            numberOfBedrooms = 2,
                            numberOfBathrooms = 1,
                            numberOfCarSpaces = 1
                        )
                    )
                )
            )
            .assertLayoutDisplayed()
            .assertPriceNotDisplayed()
            .assertLifecycleStatusDisplayed("Available")
    }

    @Test
    fun `lifecycle status should not be displayed when null`() {
        robot
            .setupComponent(
                data = ProjectChildCardRobotInitData(
                    projectChild = ProjectChild(
                        id = 3333L,
                        listingType = ListingType.PROPERTY,
                        lifecycleStatus = null,
                        listingDetails = ListingDetails(
                            price = "$500,000",
                            numberOfBedrooms = 2,
                            numberOfBathrooms = 1,
                            numberOfCarSpaces = 0
                        )
                    )
                )
            )
            .assertLayoutDisplayed()
            .assertPriceDisplayed("$500,000")
            .assertLifecycleStatusNotDisplayed()
    }

    @Test
    fun `should handle click on project child card`() {
        robot
            .setupComponent(
                data = ProjectChildCardRobotInitData(
                    projectChild = ProjectChild(
                        id = 4444L,
                        listingType = ListingType.PROPERTY,
                        lifecycleStatus = null,
                        listingDetails = ListingDetails(
                            price = "$750,000",
                            numberOfBedrooms = 4,
                            numberOfBathrooms = 2,
                            numberOfCarSpaces = 2
                        )
                    )
                )
            )
            .assertLayoutDisplayed()
            .clickProjectChildCard()

        assertEquals(4444L, robot.childClickedId)
    }
}