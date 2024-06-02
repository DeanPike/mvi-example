package au.com.deanpike.detail.ui.unit.screen.property

import au.com.deanpike.detail.ui.framework.ability.PropertyDetailScreenAbility
import au.com.deanpike.detail.ui.property.PropertyDetailScreenContent
import au.com.deanpike.detail.ui.property.PropertyDetailScreenState
import au.com.deanpike.uishared.base.ScreenStateType
import au.com.deanpike.uishared.theme.MviExampleTheme
import au.com.deanpike.uitestshared.base.UiUnitTestBase
import au.com.deanpike.uitestshared.util.advanceTimeAndWait
import org.junit.Test

class PropertyDetailScreenTest : UiUnitTestBase() {
    private val propertyScreenAbility = PropertyDetailScreenAbility(composeTestRule)

    @Test
    fun should_display_progress() {
        with(composeTestRule) {
            setContent {
                MviExampleTheme {
                    PropertyDetailScreenContent(
                        state = PropertyDetailScreenState(
                            screenState = ScreenStateType.LOADING
                        )
                    )
                }
            }
            advanceTimeAndWait()

            with(propertyScreenAbility) {
                assertProgressDisplayed()
            }
        }
    }
}