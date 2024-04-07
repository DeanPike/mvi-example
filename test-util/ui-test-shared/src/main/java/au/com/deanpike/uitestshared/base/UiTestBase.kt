package au.com.deanpike.uitestshared.base

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.platform.app.InstrumentationRegistry
import au.com.deanpike.uitestshared.HiltTestActivity
import au.com.deanpike.uitestshared.util.FakeImageLoader
import coil.Coil
import dagger.hilt.android.testing.HiltAndroidRule
import org.junit.After
import org.junit.Before
import org.junit.Rule

abstract class UiTestBase {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()
    //val composeTestRule = createComposeRule()

//    @get:Rule(order = 3)
//    val dispatcherRule = TestDispatcherRule()

    val context = InstrumentationRegistry.getInstrumentation().targetContext

    private lateinit var idlingResource: IdlingResource

    @Before
    fun setupFakeCoil() {
        Coil.setImageLoader(FakeImageLoader(context))
    }

    // There is a bug with compose and espresso to do with idling resources
    // That is why we are removing the idling resource
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