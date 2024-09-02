package au.com.deanpike.uishared.component

import au.com.deanpike.commonshared.model.ListingDetails
import au.com.deanpike.uitestshared.base.UiUnitTestBase
import au.com.deanpike.uitestshared.robot.PropertyDetailComponentRobot
import au.com.deanpike.uitestshared.robot.PropertyDetailComponentRobotInitData
import org.junit.Test

class PropertyDetailComponentTest : UiUnitTestBase() {

    private val robot = PropertyDetailComponentRobot(composeTestRule)

    @Test
    fun check_that_property_details_are_displayed() {
        robot
            .setupComponent(
                data = PropertyDetailComponentRobotInitData(
                    details = ListingDetails(
                        price = "$100,000",
                        numberOfBedrooms = 5,
                        numberOfBathrooms = 3,
                        numberOfCarSpaces = 2
                    ),
                    dwellingType = "House"
                )
            )
            .assertLayoutDisplayed()
            .numberOfBedrooms(5)
            .numberOfBathrooms(3)
            .numberOfParkingSpaces(2)
            .dwellingTypeDisplayed("House")
    }

    @Test
    fun check_that_nothing_is_displayed_for_empty_values() {
        robot
            .setupComponent(
                data = PropertyDetailComponentRobotInitData(
                    details = ListingDetails(
                        price = "100",
                        numberOfBedrooms = null,
                        numberOfBathrooms = null,
                        numberOfCarSpaces = null
                    ),
                    dwellingType = null
                )
            )
            .bedroomLayoutNotDisplayed()
            .bathroomLayoutNotDisplayed()
            .parkingLayoutNotDisplayed()
            .dwellingTypeNotDisplayed()
    }

    @Test
    fun bedroom_should_not_display() {
        robot
            .setupComponent(
                data = PropertyDetailComponentRobotInitData(
                    details = ListingDetails(
                        price = "100",
                        numberOfBedrooms = null,
                        numberOfBathrooms = 1,
                        numberOfCarSpaces = 2
                    ),
                    dwellingType = "House"
                )
            )
            .bedroomLayoutNotDisplayed()
            .numberOfBathrooms(1)
            .numberOfParkingSpaces(2)
            .dwellingTypeDisplayed("House")
    }

    @Test
    fun bathroom_should_not_display() {
        robot
            .setupComponent(
                data = PropertyDetailComponentRobotInitData(
                    details = ListingDetails(
                        price = "100",
                        numberOfBedrooms = 1,
                        numberOfBathrooms = null,
                        numberOfCarSpaces = 2
                    ),
                    dwellingType = "House"
                )
            )
            .numberOfBedrooms(1)
            .bathroomLayoutNotDisplayed()
            .numberOfParkingSpaces(2)
            .dwellingTypeDisplayed("House")
    }

    @Test
    fun parking_should_not_display() {
        robot
            .setupComponent(
                data = PropertyDetailComponentRobotInitData(
                    details = ListingDetails(
                        price = "100",
                        numberOfBedrooms = 1,
                        numberOfBathrooms = 2,
                        numberOfCarSpaces = null
                    ),
                    dwellingType = "House"
                )
            )
            .numberOfBedrooms(1)
            .numberOfBathrooms(2)
            .parkingLayoutNotDisplayed()
            .dwellingTypeDisplayed("House")
    }

    @Test
    fun dwelling_type_should_not_display() {
        robot
            .setupComponent(
                data = PropertyDetailComponentRobotInitData(
                    details = ListingDetails(
                        price = "100",
                        numberOfBedrooms = 1,
                        numberOfBathrooms = 2,
                        numberOfCarSpaces = 3
                    ),
                    dwellingType = null
                )
            )
            .numberOfBedrooms(1)
            .numberOfBathrooms(2)
            .numberOfParkingSpaces(3)
            .dwellingTypeNotDisplayed()
    }
}