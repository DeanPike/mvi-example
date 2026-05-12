package au.com.deanpike.listings.ui.list.component

import au.com.deanpike.commonshared.model.ListingDetails
import au.com.deanpike.datashared.type.ListingType
import au.com.deanpike.listings.client.model.listing.response.Property
import au.com.deanpike.listings.ui.robot.PropertyCardRobot
import au.com.deanpike.listings.ui.robot.PropertyCardRobotInitData
import au.com.deanpike.uitestshared.base.RobolectricTestBase
import org.junit.Assert.assertEquals
import org.junit.Test

class PropertyCardTest : RobolectricTestBase() {
    private val robot = PropertyCardRobot(composeTestRule)

    private val property = Property(
        id = 1234,
        listingType = ListingType.PROPERTY,
        address = "1 Smith Street, Sydney, 2000",
        listingImage = null,
        agencyImage = null,
        agencyColour = "#ffffff",
        dwellingType = "House",
        headLine = "Two Bedroom Apartment in The Quay",
        lifecycleStatus = "New",
        detail = ListingDetails(
            price = "Guide $520,000",
            numberOfBedrooms = 3,
            numberOfBathrooms = 1,
            numberOfCarSpaces = 0
        )
    )

    @Test
    fun `screen should be displayed`() {
        robot
            .setupComponent(
                data = PropertyCardRobotInitData(property = property)
            )
            .assertLayoutDisplayed()
            .assertImageDisplayed()
            .assertPriceDisplayed("Guide $520,000")
            .assertDescriptionDisplayed("House • 3 Bed • 1 Bath • 0 Car")
            .assertAddressDisplayed("1 Smith Street, Sydney, 2000")
            .assertHeadlineDisplayed("Two Bedroom Apartment in The Quay")
    }

    @Test
    fun `should select property card`() {
        robot
            .setupComponent(
                data = PropertyCardRobotInitData(property = property)
            )
            .assertLayoutDisplayed()
            .clickPropertyCard()

        assertEquals(1234L, robot.propertySelectedId)
    }
}