package au.com.deanpike.ui.screen.list.component

import au.com.deanpike.commonshared.model.ListingDetails
import au.com.deanpike.ui.framework.robot.ProjectChildListItemComponentRobot
import au.com.deanpike.ui.framework.robot.ProjectChildListItemComponentRobotInitData
import au.com.deanpike.uitestshared.base.UiUnitTestBase
import au.com.deanpike.uitestshared.robot.PropertyDetailComponentRobot
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ProjectChildListItemComponentTest : UiUnitTestBase() {

    private val childRobot = ProjectChildListItemComponentRobot(composeTestRule)
    private val detailRobot = PropertyDetailComponentRobot(composeTestRule)

    @Test
    fun should_show_project_child_list_item() {
        childRobot
            .setupComponent(
                data = ProjectChildListItemComponentRobotInitData(
                    id = 1234,
                    listingDetails = ListingDetails(
                        price = "Price",
                        numberOfBedrooms = 5,
                        numberOfBathrooms = 3,
                        numberOfCarSpaces = 2
                    ),
                    lifecycleStatus = "New",
                )
            )
            .assertLayoutDisplayed()
            .assertPriceDisplayed("Price")
            .assertLifecycleDisplayed("New")
            .clickCard()

        assertThat(childRobot.clickedId).isEqualTo(1234)

        detailRobot
            .assertLayoutDisplayed()
            .assertNumberOfBedrooms(5)
            .assertNumberOfBathrooms(3)
            .assertNumberOfParkingSpaces(2)
    }
}