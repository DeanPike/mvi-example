package au.com.deanpike.detail.ui.framework.robot

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import au.com.deanpike.detail.client.model.detail.Agent
import au.com.deanpike.detail.ui.shared.AgentComponent
import au.com.deanpike.detail.ui.shared.AgentComponentTestTags.AGENT_CARD
import au.com.deanpike.detail.ui.shared.AgentComponentTestTags.AGENT_EMAIL
import au.com.deanpike.detail.ui.shared.AgentComponentTestTags.AGENT_FAX
import au.com.deanpike.detail.ui.shared.AgentComponentTestTags.AGENT_GENERAL
import au.com.deanpike.detail.ui.shared.AgentComponentTestTags.AGENT_IMAGE
import au.com.deanpike.detail.ui.shared.AgentComponentTestTags.AGENT_MOBILE
import au.com.deanpike.detail.ui.shared.AgentComponentTestTags.AGENT_NAME
import au.com.deanpike.uishared.theme.MviExampleTheme
import au.com.deanpike.uitestshared.base.TestRobotBase
import au.com.deanpike.uitestshared.base.TestRobotInitData
import au.com.deanpike.uitestshared.util.assertTagDisplayed
import au.com.deanpike.uitestshared.util.assertTagDoesNotExist
import au.com.deanpike.uitestshared.util.assertTextDisplayed

class AgentComponentRobot(private val composeRule: ComposeContentTestRule) : TestRobotBase<AgentComponentRobot, AgentComponentRobotInitData>(composeRule) {
    override fun setupComponent(data: AgentComponentRobotInitData?): AgentComponentRobot {

        composeRule.setContent {
            MviExampleTheme {
                AgentComponent(
                    agent = data!!.agent
                )
            }
        }
        return this
    }

    override fun assertLayoutDisplayed(): AgentComponentRobot {
        composeRule.assertTagDisplayed(AGENT_CARD)
        return this
    }

    fun assertAgentName(name: String): AgentComponentRobot {
        composeRule.assertTextDisplayed(
            tag = AGENT_NAME,
            text = name
        )
        return this
    }

    fun assertAgentImage(): AgentComponentRobot {
        composeRule.assertTagDisplayed(AGENT_IMAGE)
        return this
    }

    fun assertAgentMobileContact(number: String): AgentComponentRobot {
        composeRule.assertTextDisplayed(tag = "${AGENT_MOBILE}_LABEL", text = "Mobile")
        composeRule.assertTextDisplayed(tag = "${AGENT_MOBILE}_VALUE", text = number)
        return this
    }

    fun assertAgentMobileContactNotDisplayed(): AgentComponentRobot {
        composeRule.assertTagDoesNotExist(tag = AGENT_MOBILE)
        return this
    }

    fun assertAgentGeneralContact(number: String): AgentComponentRobot {
        composeRule.assertTextDisplayed(tag = "${AGENT_GENERAL}_LABEL", text = "General")
        composeRule.assertTextDisplayed(tag = "${AGENT_GENERAL}_VALUE", text = number)
        return this
    }

    fun assertAgentGeneralContactNotDisplayed(): AgentComponentRobot {
        composeRule.assertTagDoesNotExist(tag = AGENT_GENERAL)
        return this
    }

    fun assertAgentFaxContact(number: String): AgentComponentRobot {
        composeRule.assertTextDisplayed(tag = "${AGENT_FAX}_LABEL", text = "Fax")
        composeRule.assertTextDisplayed(tag = "${AGENT_FAX}_VALUE", text = number)
        return this
    }

    fun assertAgentFaxContactNotDisplayed(): AgentComponentRobot {
        composeRule.assertTagDoesNotExist(tag = AGENT_FAX)
        return this
    }

    fun assertAgentEmailContact(email: String): AgentComponentRobot {
        composeRule.assertTextDisplayed(tag = "${AGENT_EMAIL}_LABEL", text = "Email")
        composeRule.assertTextDisplayed(tag = "${AGENT_EMAIL}_VALUE", text = email)
        return this
    }

    fun assertAgentEmailContactNotDisplayed(): AgentComponentRobot {
        composeRule.assertTagDoesNotExist(tag = AGENT_EMAIL)
        return this
    }
}

data class AgentComponentRobotInitData(
    val agent: Agent
) : TestRobotInitData