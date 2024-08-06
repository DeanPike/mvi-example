package au.com.deanpike.detail.ui.unit.screen.project

import au.com.deanpike.detail.client.model.detail.ProjectChild
import au.com.deanpike.detail.ui.framework.ability.ProjectChildComponentAbility
import au.com.deanpike.detail.ui.project.ProjectChildComponent
import au.com.deanpike.uishared.theme.MviExampleTheme
import au.com.deanpike.uitestshared.ability.PropertyDetailComponentAbility
import au.com.deanpike.uitestshared.base.UiUnitTestBase
import au.com.deanpike.uitestshared.util.advanceTimeAndWait
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ProjectChildComponentTest : UiUnitTestBase() {
    private val ability = ProjectChildComponentAbility(composeTestRule)
    private val detailAbility = PropertyDetailComponentAbility(composeTestRule)

    @Test
    fun should_display_project_child() {
        var onChildClicked: Long? = null

        with(composeTestRule) {
            setContent {
                MviExampleTheme {
                    ProjectChildComponent(
                        position = 0,
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
                        ),
                        screenWidth = 1000,
                        onProjectChildClicked = {
                            onChildClicked = it
                        }
                    )
                }
            }
            advanceTimeAndWait()
        }

        with(ability) {
            assertChildListingDisplayed(position = 0)
            assertChildImageDisplayed(position = 0)
            assertChildPriceDisplayed(position = 0, text = "Starting from $2,000,000 with extra data")
            assertLifecycleDisplayed(position = 0, text = "New Home")

            clickChild(0)
            composeTestRule.advanceTimeAndWait()
            assertThat(onChildClicked).isEqualTo(2019256252)
        }

        with(detailAbility) {
            assertGroupDisplayed(parentPosition = 0, position = 0)
            assertBedroomDisplayed(
                parentPosition = 0,
                position = 0,
                text = "3"
            )
            assertBathroomDisplayed(
                parentPosition = 0,
                position = 0,
                text = "2"
            )
            assertCarSpaceDisplayed(
                parentPosition = 0,
                position = 0,
                text = "1"
            )
        }
    }
}