package au.com.deanpike.ui.unit.screen.shared

import au.com.deanpike.client.model.listing.response.ListingDetails
import au.com.deanpike.ui.framework.ability.PropertyDetailComponentAbility
import au.com.deanpike.ui.screen.shared.PropertyDetailComponent
import au.com.deanpike.uishared.theme.MviExampleTheme
import au.com.deanpike.uitestshared.base.UiUnitTestBase
import au.com.deanpike.uitestshared.util.advanceTimeAndWait
import org.junit.Test

class PropertyDetailComponentTest : UiUnitTestBase() {

    private val ability = PropertyDetailComponentAbility(composeTestRule)

    @Test
    fun check_that_property_details_are_displayed() {
        with(composeTestRule) {
            setContent {
                MviExampleTheme {
                    PropertyDetailComponent(
                        position = 1,
                        details = ListingDetails(
                            price = "$100,000",
                            numberOfBedrooms = 5,
                            numberOfBathrooms = 3,
                            numberOfCarSpaces = 2
                        ),
                        dwellingType = "House"
                    )
                }
            }
            advanceTimeAndWait()
        }

        with(ability) {
            assertGroupDisplayed(1)
            assertBedroomDisplayed(
                position = 1,
                text = "5"
            )
            assertBathroomDisplayed(
                position = 1,
                text = "3"
            )
            assertCarSpaceDisplayed(
                position = 1,
                text = "2"
            )
            assertDwellingTypeDisplayed(
                position = 1,
                text = "House"
            )
        }
    }

    @Test
    fun check_that_nothing_is_displayed_for_empty_values() {
        with(composeTestRule) {
            setContent {
                MviExampleTheme {
                    PropertyDetailComponent(
                        position = 1,
                        details = ListingDetails(
                            price = "100",
                            numberOfBedrooms = null,
                            numberOfBathrooms = null,
                            numberOfCarSpaces = null
                        ),
                        dwellingType = null
                    )
                }
            }
            advanceTimeAndWait()
        }

        with(ability) {
            assertBedroomNotDisplayed(1)
            assertBathroomNotDisplayed(1)
            assertCarSpaceNotDisplayed(1)
            assertDwellingTypeNotDisplayed(1)
        }
    }
}