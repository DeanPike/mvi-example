package au.com.deanpike.ui.screen.list.component

import androidx.compose.ui.test.assertAny
import androidx.compose.ui.test.filterToOne
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import au.com.deanpike.commonshared.model.ListingDetails
import au.com.deanpike.datashared.type.ListingType
import au.com.deanpike.listings.client.model.listing.response.Project
import au.com.deanpike.listings.client.model.listing.response.ProjectChild
import au.com.deanpike.ui.framework.robot.ProjectChildListItemComponentRobot
import au.com.deanpike.ui.framework.robot.ProjectListItemRobot
import au.com.deanpike.ui.framework.robot.ProjectListItemRobotInitData
import au.com.deanpike.ui.screen.list.component.ProjectChildListItemComponentTestTags.PROJECT_CHILD_LIST_ITEM_LAYOUT
import au.com.deanpike.uishared.component.DetailListItemTestTags.DETAIL_ITEM_BEDROOMS
import au.com.deanpike.uishared.component.DetailListItemTestTags.DETAIL_ITEM_GROUP
import au.com.deanpike.uitestshared.base.UiUnitTestBase
import au.com.deanpike.uitestshared.robot.AgencyBannerComponentRobot
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ProjectListItemTest : UiUnitTestBase() {

    private val robot = ProjectListItemRobot(composeTestRule)
    private val agencyBannerRobot = AgencyBannerComponentRobot(composeTestRule)
    private val projectChildRobot = ProjectChildListItemComponentRobot(composeTestRule)

    @Test
    fun should_show_project_list_item() {

        robot
            .setupComponent(
                data = ProjectListItemRobotInitData(
                    project = getProject()
                )
            )
            .assertLayoutDisplayed()
            .assertBannerImageDisplayed()
            .assertProjectImageDisplayed()
            .assertProjectName(text = "Project name")
            .assertAddress(text = "Project address")
            .assertProjectChildrenButtonText(text = "2 Properties")
            .clickProject()
            .clickProjectChildButton()

        assertThat(robot.clickedProjectId).isEqualTo(1234)

        agencyBannerRobot.assertImageDisplayed()

        projectChildRobot
            .assertChildLayoutDisplayed(2222)
            .assertPriceDisplayed(id = 2222, text = "$100,000")
            .assertLifecycleDisplayed(id = 2222, text = "New")
            .assertBedroomDisplayed(
                id = 2222,
                bedrooms = "3"
            )
            .assertBathroomDisplayed(
                id = 2222,
                bathrooms = "1"
            )
            .assertCarSpacesDisplayed(
                id = 2222,
                carSpaces = "2"
            )
            .clickCard(2222)

        assertThat(robot.clickedProjectChildId).isEqualTo(2222)

        projectChildRobot
            .assertChildLayoutDisplayed(3333)
            .assertPriceDisplayed(id = 3333, text = "$357,000")
            .assertLifecycleDisplayed(id = 3333, text = "Sold")
            .assertBedroomDisplayed(
                id = 3333,
                bedrooms = "5"
            )
            .assertBathroomDisplayed(
                id = 3333,
                bathrooms = "2"
            )
            .assertCarSpacesDisplayed(
                id = 3333,
                carSpaces = "4"
            )
            .clickCard(3333)

        assertThat(robot.clickedProjectChildId).isEqualTo(3333)
    }

    private fun getProject(): Project {
        val childOne = ProjectChild(
            id = 2222,
            listingType = ListingType.PROPERTY,
            lifecycleStatus = "New",
            listingDetails = ListingDetails(
                price = "$100,000",
                numberOfBedrooms = 3,
                numberOfBathrooms = 1,
                numberOfCarSpaces = 2
            )
        )
        val childTwo = ProjectChild(
            id = 3333,
            listingType = ListingType.PROPERTY,
            lifecycleStatus = "Sold",
            listingDetails = ListingDetails(
                price = "$357,000",
                numberOfBedrooms = 5,
                numberOfBathrooms = 2,
                numberOfCarSpaces = 4
            )
        )

        val project = Project(
            id = 1234,
            listingType = ListingType.PROJECT,
            address = "Project address",
            listingImage = "http://listing.image",
            bannerImage = "http://banner.image",
            logoImage = "http://logo.image",
            projectName = "Project name",
            projectColour = "White",
            properties = listOf(childOne, childTwo)
        )

        return project
    }
}