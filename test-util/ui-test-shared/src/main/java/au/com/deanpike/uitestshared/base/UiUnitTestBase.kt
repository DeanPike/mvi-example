package au.com.deanpike.uitestshared.base

import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule

abstract class UiUnitTestBase : UiTestBase() {

    @get:Rule
    val composeTestRule = createComposeRule()

}