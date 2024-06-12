package au.com.deanpike.detail.ui.framework.ability

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import au.com.deanpike.detail.ui.shared.AgencyComponentTestTags.AGENCY_ADDRESS
import au.com.deanpike.detail.ui.shared.AgencyComponentTestTags.AGENCY_LAYOUT
import au.com.deanpike.detail.ui.shared.AgencyComponentTestTags.AGENCY_NAME
import au.com.deanpike.detail.ui.shared.AgentComponentTestTags.AGENT_CARD
import au.com.deanpike.detail.ui.shared.AgentComponentTestTags.AGENT_EMAIL
import au.com.deanpike.detail.ui.shared.AgentComponentTestTags.AGENT_FAX
import au.com.deanpike.detail.ui.shared.AgentComponentTestTags.AGENT_GENERAL
import au.com.deanpike.detail.ui.shared.AgentComponentTestTags.AGENT_IMAGE
import au.com.deanpike.detail.ui.shared.AgentComponentTestTags.AGENT_MOBILE
import au.com.deanpike.detail.ui.shared.AgentComponentTestTags.AGENT_NAME
import au.com.deanpike.uitestshared.util.assertTagDisplayed
import au.com.deanpike.uitestshared.util.assertTextDisplayed
import au.com.deanpike.uitestshared.util.scrollTo

class AgencyComponentAbility(private val composeTestRule: ComposeContentTestRule) {
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

    fun scrollToAgent(position: Int) {
        composeTestRule.scrollTo("${AGENT_CARD}_$position")
    }

    fun assertAgentNameDisplayed(
        position: Int,
        name: String
    ) {
        composeTestRule.assertTextDisplayed(
            tag = "${AGENT_NAME}_$position",
            text = name
        )
    }

    fun assertAgentImageDisplayed(
        position: Int
    ) {
        composeTestRule.assertTagDisplayed("${AGENT_IMAGE}_$position")
    }

    fun assertAgentMobileContactDisplayed(
        position: Int,
        number: String
    ) {
        composeTestRule.assertTextDisplayed(tag = "${AGENT_MOBILE}_${position}_LABEL", text = "Mobile")
        composeTestRule.assertTextDisplayed(tag = "${AGENT_MOBILE}_${position}_VALUE", text = number)
    }

    fun assertAgentGeneralContactDisplayed(
        position: Int,
        number: String
    ) {
        composeTestRule.assertTextDisplayed(tag = "${AGENT_GENERAL}_${position}_LABEL", text = "General")
        composeTestRule.assertTextDisplayed(tag = "${AGENT_GENERAL}_${position}_VALUE", text = number)
    }

    fun assertAgentFaxContactDisplayed(
        position: Int,
        number: String
    ) {
        composeTestRule.assertTextDisplayed(tag = "${AGENT_FAX}_${position}_LABEL", text = "Fax")
        composeTestRule.assertTextDisplayed(tag = "${AGENT_FAX}_${position}_VALUE", text = number)
    }

    fun assertAgentEmailContactDisplayed(
        position: Int,
        email: String
    ) {
        composeTestRule.assertTextDisplayed(tag = "${AGENT_EMAIL}_${position}_LABEL", text = "Email")
        composeTestRule.assertTextDisplayed(tag = "${AGENT_EMAIL}_${position}_VALUE", text = email)
    }
}