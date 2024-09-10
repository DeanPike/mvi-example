package au.com.deanpike.detail.ui.shared

import au.com.deanpike.detail.ui.framework.ability.DetailAppBarComponentAbility
import au.com.deanpike.uishared.theme.MviExampleTheme
import au.com.deanpike.uitestshared.base.UiUnitTestBase
import au.com.deanpike.uitestshared.util.advanceTimeAndWait
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class DetailAppBarComponentTest : UiUnitTestBase() {
    private val ability = DetailAppBarComponentAbility(composeTestRule)

    @Test
    fun should_display_detail_app_bar() {
        var closeClicked = false

        with(composeTestRule) {
            setContent {
                MviExampleTheme {
                    DetailAppBarComponent(
                        onCloseClicked = {
                            closeClicked = true
                        }
                    )
                }
            }
            advanceTimeAndWait()
        }

        with(ability) {
            assertCloseIconDisplayed()

            clickOnClose()
            composeTestRule.advanceTimeAndWait()

            assertThat(closeClicked).isTrue()
        }
    }
}