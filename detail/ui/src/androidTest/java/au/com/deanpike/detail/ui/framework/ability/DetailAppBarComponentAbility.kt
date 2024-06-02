package au.com.deanpike.detail.ui.framework.ability

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import au.com.deanpike.detail.ui.shared.DetailAppBarComponentTestTags.DETAIL_APP_BAR_ADDRESS
import au.com.deanpike.detail.ui.shared.DetailAppBarComponentTestTags.DETAIL_APP_BAR_CLOSE_ICON
import au.com.deanpike.uitestshared.util.assertTagDisplayed
import au.com.deanpike.uitestshared.util.assertTextDisplayed
import au.com.deanpike.uitestshared.util.clickOn

class DetailAppBarComponentAbility(private val composeTestRule: ComposeContentTestRule) {
    fun assertCloseIconDisplayed() {
        composeTestRule.assertTagDisplayed(DETAIL_APP_BAR_CLOSE_ICON)
    }

    fun assertAddressDisplayed(text: String) {
        composeTestRule.assertTextDisplayed(
            tag = DETAIL_APP_BAR_ADDRESS,
            text = text
        )
    }

    fun clickOnClose() {
        composeTestRule.clickOn(DETAIL_APP_BAR_CLOSE_ICON)
    }
}