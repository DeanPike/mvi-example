package au.com.deanpike.ui.unit.screen.shared

import au.com.deanpike.ui.R
import au.com.deanpike.ui.framework.ability.shared.DetailItemComponentAbility
import au.com.deanpike.ui.screen.shared.DetailItemComponent
import au.com.deanpike.uishared.theme.MviExampleTheme
import au.com.deanpike.uitestshared.base.UiUnitTestBase
import au.com.deanpike.uitestshared.util.advanceTimeAndWait
import org.junit.Test

class DetailItemComponentTest : UiUnitTestBase() {

    private val ability = DetailItemComponentAbility(composeTestRule)

    @Test
    fun check_that_details_items_are_displayed() {
        with(composeTestRule) {
            setContent {
                MviExampleTheme {
                    DetailItemComponent(
                        icon = R.drawable.car_outline,
                        text = "Test Text",
                        description = R.string.number_of_parking_spaces,
                        testTag = "TEST_TAG",
                        parentPosition = 0,
                        position = 1
                    )
                }
            }
            advanceTimeAndWait()
        }

        with(ability) {
            assertTextDisplayed(
                parentPosition = 0,
                position = 1,
                tag = "TEST_TAG",
                text = "Test Text"
            )

            assertIconContentDescription(
                parentPosition = 0,
                position = 1,
                tag = "TEST_TAG",
                text = "Number of parking spaces"
            )

            assertIconDisplayed(
                parentPosition = 0,
                position = 1,
                tag = "TEST_TAG",
                drawable = R.drawable.car_outline
            )
        }
    }
}