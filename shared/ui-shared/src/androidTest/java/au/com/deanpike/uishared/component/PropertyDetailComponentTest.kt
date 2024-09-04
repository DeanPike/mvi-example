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
            .assertNumberOfBedrooms(5)
            .assertNumberOfBathrooms(3)
            .assertNumberOfParkingSpaces(2)
            .assertDwellingTypeDisplayed("House")
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
            .assertBedroomLayoutNotDisplayed()
            .assertBathroomLayoutNotDisplayed()
            .assertParkingLayoutNotDisplayed()
            .assertDwellingTypeNotDisplayed()
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
            .assertBedroomLayoutNotDisplayed()
            .assertNumberOfBathrooms(1)
            .assertNumberOfParkingSpaces(2)
            .assertDwellingTypeDisplayed("House")
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
            .assertNumberOfBedrooms(1)
            .assertBathroomLayoutNotDisplayed()
            .assertNumberOfParkingSpaces(2)
            .assertDwellingTypeDisplayed("House")
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
            .assertNumberOfBedrooms(1)
            .assertNumberOfBathrooms(2)
            .assertParkingLayoutNotDisplayed()
            .assertDwellingTypeDisplayed("House")
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
            .assertNumberOfBedrooms(1)
            .assertNumberOfBathrooms(2)
            .assertNumberOfParkingSpaces(3)
            .assertDwellingTypeNotDisplayed()
    }
}