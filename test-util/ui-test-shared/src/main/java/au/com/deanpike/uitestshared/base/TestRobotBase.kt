package au.com.deanpike.uitestshared.base

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import au.com.deanpike.uitestshared.util.advanceTimeAndWait

@Suppress("UNCHECKED_CAST")
abstract class TestRobotBase<R, D : TestRobotInitData>(private val composeRule: ComposeContentTestRule) {

    fun waitForIdle(): R {
        composeRule.advanceTimeAndWait()
        return this as R
    }

    abstract fun setupComponent(data: D? = null): R

    abstract fun assertLayoutDisplayed(): R
}

interface TestRobotInitData