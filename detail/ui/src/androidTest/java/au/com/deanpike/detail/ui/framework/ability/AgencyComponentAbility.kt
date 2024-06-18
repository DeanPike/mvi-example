package au.com.deanpike.detail.ui.framework.ability

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import au.com.deanpike.detail.ui.shared.AgencyComponentTestTags.AGENCY_ADDRESS
import au.com.deanpike.detail.ui.shared.AgencyComponentTestTags.AGENCY_LAYOUT
import au.com.deanpike.detail.ui.shared.AgencyComponentTestTags.AGENCY_NAME
import au.com.deanpike.uitestshared.util.assertTagDisplayed
import au.com.deanpike.uitestshared.util.assertTextDisplayed
import au.com.deanpike.uitestshared.util.scrollTo

class AgencyComponentAbility(private val composeTestRule: ComposeContentTestRule) {

    fun scrollToAgency() {
        composeTestRule.scrollTo(AGENCY_LAYOUT)
    }

    fun assertAgencyLayoutDisplayed() {
        composeTestRule.assertTagDisplayed(AGENCY_LAYOUT)
    }

    fun assertAgencyNameDisplayed(name: String) {
        composeTestRule.assertTextDisplayed(
            tag = AGENCY_NAME,
            text = name
        )
    }

    fun assertAgencyAddressDisplayed(address: String) {
        composeTestRule.assertTextDisplayed(
            tag = AGENCY_ADDRESS,
            text = address
        )
    }
}