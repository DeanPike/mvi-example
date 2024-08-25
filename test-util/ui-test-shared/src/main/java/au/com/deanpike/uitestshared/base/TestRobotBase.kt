package au.com.deanpike.uitestshared.base

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import au.com.deanpike.uitestshared.util.advanceTimeAndWait

@Suppress("UNCHECKED_CAST")
abstract class TestRobotBase<T>(private val composeRule: ComposeContentTestRule) {

    fun waitForIdle(): T {
        composeRule.advanceTimeAndWait()
        return this as T
    }
}