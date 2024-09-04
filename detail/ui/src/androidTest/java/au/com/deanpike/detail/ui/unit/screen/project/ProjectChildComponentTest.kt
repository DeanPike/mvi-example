package au.com.deanpike.detail.ui.unit.screen.project

import au.com.deanpike.detail.client.model.detail.ProjectChild
import au.com.deanpike.detail.ui.framework.robot.ProjectChildComponentRobot
import au.com.deanpike.detail.ui.framework.robot.ProjectChildComponentRobotInitData
import au.com.deanpike.uitestshared.base.UiUnitTestBase
import au.com.deanpike.uitestshared.robot.PropertyDetailComponentRobot
import org.junit.Test

class ProjectChildComponentTest : UiUnitTestBase() {

    private val projectChildRobot = ProjectChildComponentRobot(composeTestRule)
    private val detailRobot = PropertyDetailComponentRobot(composeTestRule)

    @Test
    fun should_display_project_child() {
        projectChildRobot
            .setupComponent(
                data = ProjectChildComponentRobotInitData(
                    child = ProjectChild(
                        id = 2019256252,
                        bedroomCount = 3,
                        bathroomCount = 2,
                        carSpaceCount = 1,
                        price = "Starting from $2,000,000 with extra data",
                        propertyTypeDescription = "newApartments",
                        propertyUrl = "https://www.domain.com.au/13-crown-street-wollongong-nsw-2500-2019256252",
                        propertyImage = "https://bucket-api.domain.com.au/v1/bucket/image/2019256252_1_1_240521_034448-w3000-h1875",
                        lifecycleStatus = "New Home"
                    )
                )
            )
            .assertLayoutDisplayed()
            .assertProjectChildImageDisplayed()
            .assertLifecycle("New Home")
            .assertPriceDisplayed("Starting from \$2,000,000 with extra data")
            .clickProjectChild()
            .assertClickedProjectChildId(2019256252)

        detailRobot
            .assertNumberOfBedrooms(3)
            .assertNumberOfBathrooms(2)
            .assertNumberOfParkingSpaces(1)
    }
}