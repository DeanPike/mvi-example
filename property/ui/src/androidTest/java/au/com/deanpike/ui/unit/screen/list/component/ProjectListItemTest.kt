package au.com.deanpike.ui.unit.screen.list.component

import au.com.deanpike.listings.client.model.listing.response.ListingDetails
import au.com.deanpike.listings.client.model.listing.response.ListingType
import au.com.deanpike.listings.client.model.listing.response.Project
import au.com.deanpike.listings.client.model.listing.response.ProjectChild
import au.com.deanpike.ui.framework.ability.list.component.ProjectChildListItemComponentAbility
import au.com.deanpike.ui.framework.ability.list.component.ProjectListItemAbility
import au.com.deanpike.ui.framework.ability.shared.PropertyDetailComponentAbility
import au.com.deanpike.ui.screen.list.component.ProjectListItem
import au.com.deanpike.uishared.theme.MviExampleTheme
import au.com.deanpike.uitestshared.base.UiUnitTestBase
import au.com.deanpike.uitestshared.util.advanceTimeAndWait
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ProjectListItemTest : UiUnitTestBase() {

    private val ability = ProjectListItemAbility(composeTestRule)
    private val projectChildAbility = ProjectChildListItemComponentAbility(composeTestRule)
    private val detailComponentAbility = PropertyDetailComponentAbility(composeTestRule)

    @Test
    fun should_show_project_list_item() {
        var clickedProjectId: Long? = null
        var clickedChildId: Long? = null

        with(composeTestRule) {
            setContent {
                MviExampleTheme {
                    ProjectListItem(
                        position = 1,
                        project = getProject(),
                        onProjectClicked = {
                            clickedProjectId = it
                        },
                        onProjectChildClicked = {
                            clickedChildId = it
                        }
                    )
                }
            }
            advanceTimeAndWait()
        }

        with(ability) {
            assertProjectDisplayed(1)
            assertBannerImageDisplayed(1)
            assertProjectImageDisplayed(1)
            assertAgencyImageDisplayed(1)
            assertProjectName(
                position = 1,
                text = "Project name"
            )
            assertAddress(
                position = 1,
                text = "Project address"
            )
            assertProjectChildrenButtonText(
                position = 1,
                text = "2 Properties"
            )
            clickProjectChildButton(1)
            composeTestRule.advanceTimeAndWait()
        }

        with(projectChildAbility) {
            assertCardDisplayed(
                parentPosition = 1,
                position = 0
            )
            assertPriceDisplayed(
                parentPosition = 1,
                position = 0,
                text = "$100,000"
            )
            detailComponentAbility.assertGroupDisplayed(
                parentPosition = 1,
                position = 0
            )
            detailComponentAbility.assertBedroomDisplayed(
                parentPosition = 1,
                position = 0,
                text = "3"
            )
            detailComponentAbility.assertBathroomDisplayed(
                parentPosition = 1,
                position = 0,
                text = "1"
            )
            detailComponentAbility.assertCarSpaceDisplayed(
                parentPosition = 1,
                position = 0,
                text = "2"
            )

            assertCardDisplayed(
                parentPosition = 1,
                position = 1
            )
            assertPriceDisplayed(
                parentPosition = 1,
                position = 1,
                text = "$357,000"
            )
            detailComponentAbility.assertGroupDisplayed(
                parentPosition = 1,
                position = 1
            )
            detailComponentAbility.assertBedroomDisplayed(
                parentPosition = 1,
                position = 1, text = "5"
            )
            detailComponentAbility.assertBathroomDisplayed(
                parentPosition = 1,
                position = 1, text = "2"
            )
            detailComponentAbility.assertCarSpaceDisplayed(
                parentPosition = 1,
                position = 1,
                text = "4"
            )
        }

        ability.clickProject(1)
        composeTestRule.advanceTimeAndWait()

        assertThat(clickedProjectId).isEqualTo(1234)

        projectChildAbility.clickCard(
            parentPosition = 1,
            position = 0
        )
        assertThat(clickedChildId).isEqualTo(2222)

        projectChildAbility.clickCard(
            parentPosition = 1,
            position = 1
        )
        assertThat(clickedChildId).isEqualTo(3333)
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