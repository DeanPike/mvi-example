package au.com.deanpike.uishared.component

import au.com.deanpike.commonshared.model.ListingDetails
import au.com.deanpike.uishared.theme.MviExampleTheme
import au.com.deanpike.uitestshared.ability.PropertyDetailComponentAbility
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
                        parentPosition = 0,
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
            assertDwellingTypeDisplayed(
                parentPosition = 0,
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
                        parentPosition = 0,
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
            assertBedroomNotDisplayed(
                parentPosition = 0,
                position = 1
            )
            assertBathroomNotDisplayed(
                parentPosition = 0,
                position = 1
            )
            assertCarSpaceNotDisplayed(
                parentPosition = 0,
                position = 1
            )
            assertDwellingTypeNotDisplayed(
                parentPosition = 0,
                position = 1
            )
        }
    }
}