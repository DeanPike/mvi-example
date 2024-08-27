package au.com.deanpike.uitestshared.ability

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import au.com.deanpike.uishared.component.LifecycleStatusTestTags.LIFECYCLE_STATUS_TEXT
import au.com.deanpike.uitestshared.util.assertTextDisplayed

class LifecycleStatusAbility(private val composeTestRule: ComposeContentTestRule) {
    fun assertLifecycleDisplayed(position: Int, text: String) {
        composeTestRule.assertTextDisplayed(tag = LIFECYCLE_STATUS_TEXT, text = text)
    }
}