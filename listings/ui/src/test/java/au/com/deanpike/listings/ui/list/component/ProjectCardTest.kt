package au.com.deanpike.listings.ui.list.component

import au.com.deanpike.commonshared.model.ListingDetails
import au.com.deanpike.datashared.type.ListingType
import au.com.deanpike.listings.client.model.listing.response.Project
import au.com.deanpike.listings.client.model.listing.response.ProjectChild
import au.com.deanpike.listings.ui.list.component.ProjectCardTesTags.PROJECT_CARD_CHILD_ROW
import au.com.deanpike.listings.ui.robot.ProjectCardRobot
import au.com.deanpike.listings.ui.robot.ProjectCardRobotInitData
import au.com.deanpike.listings.ui.robot.ProjectChildCardRobot
import au.com.deanpike.uitestshared.base.RobolectricTestBase
import au.com.deanpike.uitestshared.util.advanceTimeAndWait
import au.com.deanpike.uitestshared.util.swipeLeft
import org.junit.Assert.assertEquals
import org.junit.Test

class ProjectCardTest : RobolectricTestBase() {
    private val robot = ProjectCardRobot(composeTestRule)
    private val childRobot = ProjectChildCardRobot(composeTestRule)

    private val project = Project(
        id = 1234,
        listingType = ListingType.PROJECT,
        address = "100 Harris Street, Pyrmont, 2000",
        listingImage = null,
        bannerImage = null,
        logoImage = null,
        projectName = "Blakelys Run",
        projectColour = "#c4bfad",
        properties = listOf(
            ProjectChild(
                id = 1111,
                listingType = ListingType.PROPERTY,
                lifecycleStatus = "New",
                listingDetails = ListingDetails(
                    price = "Offers Above $659,275",
                    numberOfBedrooms = 3,
                    numberOfBathrooms = 2,
                    numberOfCarSpaces = 1
                )
            ),
            ProjectChild(
                id = 2222,
                listingType = ListingType.PROPERTY,
                lifecycleStatus = "Sold",
                listingDetails = ListingDetails(
                    price = "Contact Agent",
                    numberOfBedrooms = 4,
                    numberOfBathrooms = 3,
                    numberOfCarSpaces = 2
                )
            )
        )
    )

    @Test
    fun `screen should be displayed`() {
        robot
            .setupComponent(
                data = ProjectCardRobotInitData(project = project)
            )
            .assertLayoutDisplayed()
            .assertImageDisplayed()
            .assertProjectNameDisplayed("Blakelys Run")
            .assertAddressDisplayed("100 Harris Street, Pyrmont, 2000")
    }

    @Test
    fun `project children should be displayed`() {
        robot
            .setupComponent(
                data = ProjectCardRobotInitData(project = project)
            )
            .assertLayoutDisplayed()

        // First child
        childRobot.assertLayoutDisplayedAtPosition(0)
        childRobot.assertPriceDisplayed(price = "Offers Above $659,275", position = 0)
        childRobot.assertDetailsDisplayed(details = "3 Bed • 2 Bath • 1 Car", position = 0)
        childRobot.assertLifecycleStatusDisplayed(status = "New", position = 0)

        // Scroll to second child
        composeTestRule.swipeLeft(PROJECT_CARD_CHILD_ROW)
        composeTestRule.advanceTimeAndWait()

        childRobot.assertLayoutDisplayedAtPosition(1)
        childRobot.assertPriceDisplayed(price = "Contact Agent", position = 1)
        childRobot.assertDetailsDisplayed(details = "4 Bed • 3 Bath • 2 Car", position = 1)
        childRobot.assertLifecycleStatusDisplayed(status = "Sold", position = 1)
    }

    @Test
    fun `should select project card`() {
        robot
            .setupComponent(
                data = ProjectCardRobotInitData(project = project)
            )
            .assertLayoutDisplayed()
            .clickProjectCard()

        assertEquals(1234L, robot.projectSelectedId)
    }
}