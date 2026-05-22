package au.com.deanpike.detail.ui.project

import au.com.deanpike.detail.client.model.detail.ProjectChild
import au.com.deanpike.detail.ui.robot.ProjectChildrenComponentRobot
import au.com.deanpike.detail.ui.robot.ProjectChildrenComponentRobotInitData
import au.com.deanpike.uitestshared.base.RobolectricTestBase
import org.junit.Test

class ProjectChildrenComponentTest : RobolectricTestBase() {
    private val childrenRobot = ProjectChildrenComponentRobot(composeTestRule)

    @Test
    fun should_show_project_child_listings() {
        childrenRobot
            .setupComponent(
                data = ProjectChildrenComponentRobotInitData(
                    childListings = listOf(
                        ProjectChild(
                            id = 100L,
                            bedroomCount = 1
                        ),
                        ProjectChild(
                            id = 200L,
                            bedroomCount = 2
                        )
                    )
                )
            )
            .assertLayoutDisplayed()
            .assertListingDisplayedAtPosition(
                position = 0,
                listingId = 100L
            )
            .scrollToPosition(1)
            .assertListingDisplayedAtPosition(
                position = 1,
                listingId = 200L
            )
    }
}