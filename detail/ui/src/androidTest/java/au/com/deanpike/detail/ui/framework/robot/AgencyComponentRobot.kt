package au.com.deanpike.detail.ui.framework.robot

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

class AgencyComponentRobot(private val composeRule: ComposeContentTestRule) : TestRobotBase<AgencyComponentRobot, AgencyComponentRobotInitData>(composeRule) {
    override fun setupComponent(data: AgencyComponentRobotInitData?): AgencyComponentRobot {
        composeRule.setContent {
            MviExampleTheme {
                AgencyComponent(
                    advertiser = data!!.advertiser
                )
            }
        }
        return this
    }

    override fun assertLayoutDisplayed(): AgencyComponentRobot {
        composeRule.assertTagDisplayed(AGENCY_LAYOUT)
        return this
    }

    fun assertAgentLabel(): AgencyComponentRobot {
        composeRule.assertTextDisplayed(tag = AGENT_LABEL, text = "Agent")
        return this
    }

    fun scrollToAgency(): AgencyComponentRobot {
        composeRule.scrollTo(AGENCY_LAYOUT)
        return this
    }

    fun assertAgencyName(name: String): AgencyComponentRobot {
        composeRule.assertTextDisplayed(
            tag = AGENCY_NAME,
            text = name
        )
        return this
    }

    fun assertAgencyAddress(address: String): AgencyComponentRobot {
        composeRule.assertTextDisplayed(
            tag = AGENCY_ADDRESS,
            text = address
        )
        return this
    }
}

data class AgencyComponentRobotInitData(
    val advertiser: Advertiser
) : TestRobotInitData