package au.com.deanpike.detail.ui.robot

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import au.com.deanpike.detail.client.model.detail.Advertiser
import au.com.deanpike.detail.ui.shared.AgencyComponent
import au.com.deanpike.detail.ui.shared.AgencyComponentTestTags.AGENCY_ADDRESS
import au.com.deanpike.detail.ui.shared.AgencyComponentTestTags.AGENCY_LAYOUT
import au.com.deanpike.detail.ui.shared.AgencyComponentTestTags.AGENCY_NAME
import au.com.deanpike.detail.ui.shared.AgencyComponentTestTags.AGENT_LABEL
import au.com.deanpike.uishared.theme.MviExampleTheme
import au.com.deanpike.uitestshared.base.TestRobotBase
import au.com.deanpike.uitestshared.base.TestRobotInitData
import au.com.deanpike.uitestshared.util.assertTagDisplayed
import au.com.deanpike.uitestshared.util.assertTextDisplayed
import au.com.deanpike.uitestshared.util.scrollTo

class AgencyComponentRobot(composeRule: ComposeContentTestRule) : TestRobotBase<AgencyComponentRobot, AgencyComponentRobotInitData>(composeRule) {
    override fun setupComponent(data: AgencyComponentRobotInitData?) = apply {
        composeRule.setContent {
            MviExampleTheme {
                AgencyComponent(
                    advertiser = data!!.advertiser
                )
            }
        }
    }

    override fun assertLayoutDisplayed() = apply {
        composeRule.assertTagDisplayed(AGENCY_LAYOUT)
    }

    fun assertAgentLabel() = apply {
        composeRule.assertTextDisplayed(tag = AGENT_LABEL, text = "Agent")
    }

    fun scrollToAgency() = apply {
        composeRule.scrollTo(AGENCY_LAYOUT)
    }

    fun assertAgencyName(name: String) = apply {
        composeRule.assertTextDisplayed(
            tag = AGENCY_NAME,
            text = name
        )
    }

    fun assertAgencyAddress(address: String) = apply {
        composeRule.assertTextDisplayed(
            tag = AGENCY_ADDRESS,
            text = address
        )
    }
}

data class AgencyComponentRobotInitData(
    val advertiser: Advertiser
) : TestRobotInitData