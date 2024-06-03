package au.com.deanpike.uitestshared.ability

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import au.com.deanpike.uishared.component.LifecycleStatusTestTags
import au.com.deanpike.uitestshared.util.assertTextDisplayed

class LifecycleStatusAbility(private val composeTestRule: ComposeContentTestRule) {
    fun assertLifecycleDisplayed(position: Int, text: String) {
        composeTestRule.assertTextDisplayed(tag = "${LifecycleStatusTestTags.LIFECYCLE_STATUS}$position", text = text)
    }
}