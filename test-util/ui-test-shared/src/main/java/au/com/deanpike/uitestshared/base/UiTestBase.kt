package au.com.deanpike.uitestshared.base

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Rule

class UiTestBase {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var idlingResource: IdlingResource

    // There is a bug with compose and espresso to do with idling resources
    // Thats why we are removing the idling resource
    // https://issuetracker.google.com/issues/223815266

    @Before
    fun removeIdlingResource() {
        idlingResource = IdlingRegistry.getInstance().resources.first { it.name == "Compose-Espresso link" }
        IdlingRegistry.getInstance().unregister(idlingResource)
    }

    @After
    fun addIdlingResource() {
        IdlingRegistry.getInstance().register(idlingResource)
    }

    protected fun advanceTimeAndWait(composeTestRule: ComposeContentTestRule, delay: Long = 1000) {
        composeTestRule.mainClock.advanceTimeBy(delay)
        composeTestRule.waitForIdle()
    }
}