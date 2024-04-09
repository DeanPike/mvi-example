package au.com.deanpike.uitestshared.base

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.test.platform.app.InstrumentationRegistry
import au.com.deanpike.uitestshared.util.FakeImageLoader
import coil.Coil
import org.junit.Before

abstract class UiTestBase {

    val context = InstrumentationRegistry.getInstrumentation().targetContext

    @Before
    fun setupFakeCoil() {
        Coil.setImageLoader(FakeImageLoader(context))
    }

    protected fun advanceTimeAndWait(composeTestRule: ComposeContentTestRule, delay: Long = 1000) {
        composeTestRule.mainClock.advanceTimeBy(delay)
        composeTestRule.waitForIdle()
    }
}