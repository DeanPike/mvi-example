package au.com.deanpike.ui.framework.screen

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import au.com.deanpike.ui.framework.ability.shared.ErrorComponentAbility

class ErrorComponentScreen(private val composeTestRule: ComposeContentTestRule) {

    val ability = ErrorComponentAbility(composeTestRule)

    fun assertErrorComponentDisplayed(){
        with(ability){
            assertComponentDisplayed()
            assertTitle()
            assertMessage()
            assertRetryButtonDisplayed()
        }
    }

    fun clickRetryButton(){
        ability.clickRetryButton()
    }
}