package au.com.deanpike.uishared.component

import au.com.deanpike.uitestshared.base.UiUnitTestBase
import au.com.deanpike.uitestshared.robot.BedBathCarComponentRobot
import au.com.deanpike.uitestshared.robot.BedBathCarComponentRobotInitData
import org.junit.Test

class PropertyDetailComponentTest : UiUnitTestBase() {

    private val robot = BedBathCarComponentRobot(composeTestRule)

    @Test
    fun check_that_property_details_are_displayed() {
        robot
            .setupComponent(
                data = BedBathCarComponentRobotInitData(
                    bedrooms = 5,
                    bathrooms = 3,
                    carSpaces = 2,
                )
            )
            .assertLayoutDisplayed()
            .assertNumberOfBedrooms(5)
            .assertNumberOfBathrooms(3)
            .assertNumberOfCarSpaces(2)
    }

    @Test
    fun check_that_nothing_is_displayed_for_empty_values() {
        robot
            .setupComponent(
                data = BedBathCarComponentRobotInitData(
                    bedrooms = null,
                    bathrooms = null,
                    carSpaces = null,
                )
            )
            .assertBedroomLayoutNotDisplayed()
            .assertBathroomLayoutNotDisplayed()
            .assertCarSpaceLayoutNotDisplayed()
    }

    @Test
    fun bedroom_should_not_display() {
        robot
            .setupComponent(
                data = BedBathCarComponentRobotInitData(
                    bedrooms = null,
                    bathrooms = 1,
                    carSpaces = 2,
                )
            )
            .assertBedroomLayoutNotDisplayed()
            .assertNumberOfBathrooms(1)
            .assertNumberOfCarSpaces(2)
    }

    @Test
    fun bathroom_should_not_display() {
        robot
            .setupComponent(
                data = BedBathCarComponentRobotInitData(
                    bedrooms = 1,
                    bathrooms = null,
                    carSpaces = 2,
                )
            )
            .assertNumberOfBedrooms(1)
            .assertBathroomLayoutNotDisplayed()
            .assertNumberOfCarSpaces(2)
    }

    @Test
    fun parking_should_not_display() {
        robot
            .setupComponent(
                data = BedBathCarComponentRobotInitData(
                    bedrooms = 1,
                    bathrooms = 2,
                    carSpaces = null,
                )
            )
            .assertNumberOfBedrooms(1)
            .assertNumberOfBathrooms(2)
            .assertCarSpaceLayoutNotDisplayed()
    }

    @Test
    fun dwelling_type_should_not_display() {
        robot
            .setupComponent(
                data = BedBathCarComponentRobotInitData(
                    bedrooms = 1,
                    bathrooms = 2,
                    carSpaces = 3,
                )
            )
            .assertNumberOfBedrooms(1)
            .assertNumberOfBathrooms(2)
            .assertNumberOfCarSpaces(3)
    }
}