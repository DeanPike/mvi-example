package au.com.deanpike.ui.unit.screen.list.component

import au.com.deanpike.client.model.listing.response.ListingDetails
import au.com.deanpike.ui.framework.ability.list.component.ProjectChildListItemComponentAbility
import au.com.deanpike.ui.framework.ability.shared.PropertyDetailComponentAbility
import au.com.deanpike.ui.screen.list.component.ProjectChildListItemComponent
import au.com.deanpike.uishared.theme.MviExampleTheme
import au.com.deanpike.uitestshared.base.UiUnitTestBase
import au.com.deanpike.uitestshared.util.advanceTimeAndWait
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ProjectChildListItemComponentTest : UiUnitTestBase() {

    private val ability = ProjectChildListItemComponentAbility(composeTestRule)
    private val detailAbility = PropertyDetailComponentAbility(composeTestRule)

    @Test
    fun should_show_project_child_list_item() {
        var clickedId: Long? = null
        with(composeTestRule) {
            setContent {
                MviExampleTheme {
                    ProjectChildListItemComponent(
                        parentPosition = 0,
                        position = 1,
                        id = 1234,
                        listingDetails = ListingDetails(
                            price = "Price",
                            numberOfBedrooms = 5,
                            numberOfBathrooms = 3,
                            numberOfCarSpaces = 2
                        ),
                        onProjectChildClicked = {
                            clickedId = it
                        }
                    )
                }
            }
            advanceTimeAndWait()
        }

        with(ability) {
            assertCardDisplayed(
                parentPosition = 0,
                position = 1
            )
            assertPriceDisplayed(
                parentPosition = 0,
                position = 1,
                text = "Price"
            )
        }

        with(detailAbility) {
            assertGroupDisplayed(
                parentPosition = 0,
                position = 1
            )
            assertBedroomDisplayed(
                parentPosition = 0,
                position = 1,
                text = "5"
            )
            assertBathroomDisplayed(
                parentPosition = 0,
                position = 1,
                text = "3"
            )
            assertCarSpaceDisplayed(
                parentPosition = 0,
                position = 1,
                text = "2"
            )
            assertDwellingTypeNotDisplayed(
                parentPosition = 0,
                position = 1
            )
        }

        ability.clickCard(
            parentPosition = 0,
            position = 1
        )
        composeTestRule.advanceTimeAndWait()

        assertThat(1234L).isEqualTo(clickedId)
    }
}