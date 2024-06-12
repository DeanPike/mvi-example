package au.com.deanpike.uitestshared.ability

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import au.com.deanpike.uishared.component.AgencyBannerTestTags
import au.com.deanpike.uitestshared.util.assertTagDisplayed

class AgencyBannerAbility(private val composeTestRule: ComposeContentTestRule) {

    fun assertAgencyImageDisplayed(position: Int) {
        composeTestRule.assertTagDisplayed("${AgencyBannerTestTags.AGENCY_BANNER_IMAGE}_$position")
    }
}