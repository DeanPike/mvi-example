package au.com.deanpike.ui.unit.screen.list.component

import au.com.deanpike.ui.R
import au.com.deanpike.ui.framework.ability.DetailItemAbility
import au.com.deanpike.ui.screen.list.component.DetailItem
import au.com.deanpike.uishared.theme.MviExampleTheme
import au.com.deanpike.uitestshared.base.UiUnitTestBase
import au.com.deanpike.uitestshared.util.advanceTimeAndWait
import org.junit.Test

class DetailItemTest : UiUnitTestBase() {

    private val ability = DetailItemAbility(composeTestRule)

    @Test
    fun check_that_details_items_are_displayed() {
        with(composeTestRule) {
            setContent {
                MviExampleTheme {
                    DetailItem(
                        icon = R.drawable.car_outline,
                        text = "Test Text",
                        description = R.string.number_of_parking_spaces,
                        testTag = "TEST_TAG",
                        position = 1
                    )
                }
            }
            advanceTimeAndWait()
        }

        with(ability) {
            assertTextDisplayed(
                position = 1,
                tag = "TEST_TAG",
                text = "Test Text"
            )

            assertIconContentDescription(
                position = 1,
                tag = "TEST_TAG",
                text = "Number of parking spaces"
            )

            assertIconDisplayed(
                position = 1,
                tag = "TEST_TAG",
                drawable = R.drawable.car_outline
            )
        }
    }
}