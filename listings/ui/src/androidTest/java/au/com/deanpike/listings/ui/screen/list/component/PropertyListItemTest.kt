package au.com.deanpike.listings.ui.screen.list.component

import au.com.deanpike.commonshared.model.ListingDetails
import au.com.deanpike.datashared.type.ListingType
import au.com.deanpike.listings.client.model.listing.response.Property
import au.com.deanpike.listings.ui.framework.robot.PropertyListItemRobot
import au.com.deanpike.listings.ui.framework.robot.PropertyListItemRobotInitData
import au.com.deanpike.uitestshared.base.UiUnitTestBase
import au.com.deanpike.uitestshared.robot.LifecycleStatusComponentRobot
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class PropertyListItemTest : UiUnitTestBase() {
    private val robot = PropertyListItemRobot(composeTestRule)
    private val lifecycleRobot = LifecycleStatusComponentRobot(composeTestRule)

    @Test
    fun show_property_list_item() {

        robot
            .setupComponent(
                data = PropertyListItemRobotInitData(
                    property = getProperty()
                )
            )
            .assertLayoutDisplayed()
            .assertPropertyImageDisplayed()
            .assertPriceDisplayed("$1000000")
            .assertHeadlineDisplayed("Property headline")
            .assertAddressDisplayed("Property address")
            .assertBedroomDisplayed("4")
            .assertBathroomDisplayed("3")
            .assertCarSpacesDisplayed("2")
            .clickProperty()

        lifecycleRobot.assertLifecycle("New")

        assertThat(robot.clickedId).isEqualTo(1)
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