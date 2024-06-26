package au.com.deanpike.uishared.component

import au.com.deanpike.uishared.theme.MviExampleTheme
import au.com.deanpike.uitestshared.ability.ErrorComponentAbility
import au.com.deanpike.uitestshared.base.UiUnitTestBase
import au.com.deanpike.uitestshared.util.advanceTimeAndWait
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ErrorComponentTest : UiUnitTestBase() {
    private val ability = ErrorComponentAbility(composeTestRule)

    @Test
    fun should_display_error_component() {
        var retryClicked = false
        with(composeTestRule) {
            setContent {
                MviExampleTheme {
                    ErrorComponent(
                        onRetryClicked = {
                            retryClicked = true
                        }
                    )
                }
            }
            advanceTimeAndWait()
        }

        with(ability) {
            assertComponentDisplayed()
            assertTitle()
            assertMessage()
            assertRetryButtonDisplayed()

            clickRetryButton()
            composeTestRule.advanceTimeAndWait()

            assertThat(retryClicked).isTrue()
        }
    }
}