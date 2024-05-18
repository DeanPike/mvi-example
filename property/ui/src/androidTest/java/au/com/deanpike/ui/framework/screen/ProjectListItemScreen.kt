package au.com.deanpike.ui.framework.screen

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import au.com.deanpike.listings.client.model.listing.response.Project
import au.com.deanpike.ui.framework.ability.list.component.ProjectChildListItemComponentAbility
import au.com.deanpike.ui.framework.ability.list.component.ProjectListItemAbility
import au.com.deanpike.ui.framework.ability.shared.PropertyDetailComponentAbility
import au.com.deanpike.uitestshared.util.advanceTimeAndWait

class ProjectListItemScreen(private val composeTestRule: ComposeContentTestRule) {
    private val ability = ProjectListItemAbility(composeTestRule)
    private val childAbility = ProjectChildListItemComponentAbility(composeTestRule)
    private val detailAbility = PropertyDetailComponentAbility(composeTestRule)

    fun assertProjectDisplayed(
        position: Int,
        project: Project
    ) {
        with(ability) {
            scrollTo(position)
            assertProjectDisplayed(position)
            assertBannerImageDisplayed(position)
            assertProjectImageDisplayed(position)
            assertAgencyImageDisplayed(position)
            assertProjectName(position = position, text = project.projectName!!)
            assertAddress(position = position, text = project.address)
            assertProjectChildrenButtonText(position = position, text = if (project.properties.size == 1) "1 Property" else "${project.properties.size} Properties")
            clickProjectChildButton(position)
            composeTestRule.advanceTimeAndWait()
        }

        with(childAbility) {
            project.properties.forEachIndexed { index, projectChild ->
                scrollTo(
                    parentPosition = position,
                    position = index
                )
                assertCardDisplayed(
                    parentPosition = position,
                    position = index
                )
                assertPriceDisplayed(
                    parentPosition = position,
                    position = index,
                    text = projectChild.listingDetails.price!!
                )
                assertLifecycleDisplayed(
                    parentPosition = position,
                    position = index,
                    text = projectChild.lifecycleStatus ?: ""
                )

                detailAbility.scrollTo(
                    parentPosition = position,
                    childPosition = index
                )
                detailAbility.assertGroupDisplayed(
                    parentPosition = position,
                    position = index
                )
                detailAbility.assertBedroomDisplayed(
                    parentPosition = position,
                    position = index,
                    text = "${projectChild.listingDetails.numberOfBedrooms}"
                )
                detailAbility.assertBathroomDisplayed(
                    parentPosition = position,
                    position = index,
                    text = "${projectChild.listingDetails.numberOfBathrooms}"
                )
                detailAbility.assertCarSpaceDisplayed(
                    parentPosition = position,
                    position = index,
                    text = "${projectChild.listingDetails.numberOfCarSpaces}"
                )
                detailAbility.assertDwellingTypeNotDisplayed(
                    parentPosition = position,
                    position = index
                )
            }
        }
    }
}