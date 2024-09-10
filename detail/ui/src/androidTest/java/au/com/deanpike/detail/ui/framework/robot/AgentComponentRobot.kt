package au.com.deanpike.detail.ui.framework.robot

import androidx.compose.ui.test.assertAny
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.filterToOne
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import au.com.deanpike.detail.client.model.detail.Agent
import au.com.deanpike.detail.ui.shared.AgentComponent
import au.com.deanpike.detail.ui.shared.AgentComponentTestTags.AGENT_CARD_LAYOUT
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
import au.com.deanpike.uitestshared.util.scrollTo

class AgentComponentRobot(private val composeRule: ComposeContentTestRule) : TestRobotBase<AgentComponentRobot, AgentComponentRobotInitData>(composeRule) {
    override fun setupComponent(data: AgentComponentRobotInitData?): AgentComponentRobot {

        composeRule.setContent {
            MviExampleTheme {
                AgentComponent(
                    agents = data!!.agents
                )
            }
        }
        return this
    }

    override fun assertLayoutDisplayed(): AgentComponentRobot {
        composeRule.assertTagDisplayed("${AGENT_CARD_LAYOUT}_0")
        return this
    }

    fun scrollToPosition(position: Int): AgentComponentRobot {
        composeRule.scrollTo("${AGENT_CARD_LAYOUT}_$position")
        return this
    }

    fun assertAgentName(
        name: String,
        position: Int
    ): AgentComponentRobot {
        composeRule.onNodeWithTag(
            testTag = "${AGENT_CARD_LAYOUT}_$position"
        ).onChildren()
            .assertAny(hasTestTag("${AGENT_NAME}_$position") and hasText(name))
        return this
    }

    fun assertAgentImage(
        position: Int
    ): AgentComponentRobot {
        composeRule.onNodeWithTag(
            testTag = "${AGENT_CARD_LAYOUT}_$position"
        ).onChildren()
            .assertAny(hasTestTag("${AGENT_IMAGE}_$position"))
        return this
    }

    fun assertAgentMobileContact(
        number: String,
        position: Int
    ): AgentComponentRobot {
        composeRule.onNodeWithTag(
            testTag = "${AGENT_MOBILE}_${position}"
        ).onChildren()
            .filterToOne(hasTestTag("${AGENT_MOBILE}_${position}_LABEL"))
            .assertTextEquals("Mobile")
        composeRule.onNodeWithTag(
            testTag = "${AGENT_MOBILE}_${position}"
        ).onChildren()
            .filterToOne(hasTestTag("${AGENT_MOBILE}_${position}_VALUE"))
            .assertTextEquals(number)

        return this
    }

    fun assertAgentMobileContactNotDisplayed(
        position: Int
    ): AgentComponentRobot {
        composeRule.assertTagDoesNotExist(tag = "${AGENT_MOBILE}_$position")
        return this
    }

    fun assertAgentGeneralContact(
        number: String,
        position: Int
    ): AgentComponentRobot {
        composeRule.onNodeWithTag(
            testTag = "${AGENT_GENERAL}_${position}"
        ).onChildren()
            .filterToOne(hasTestTag("${AGENT_GENERAL}_${position}_LABEL"))
            .assertTextEquals("General")
        composeRule.onNodeWithTag(
            testTag = "${AGENT_GENERAL}_${position}"
        ).onChildren()
            .filterToOne(hasTestTag("${AGENT_GENERAL}_${position}_VALUE"))
            .assertTextEquals(number)

        return this
    }

    fun assertAgentGeneralContactNotDisplayed(position: Int): AgentComponentRobot {
        composeRule.assertTagDoesNotExist(tag = "${AGENT_GENERAL}_$position")
        return this
    }

    fun assertAgentFaxContact(
        number: String,
        position: Int
    ): AgentComponentRobot {
        composeRule.onNodeWithTag(
            testTag = "${AGENT_FAX}_${position}"
        ).onChildren()
            .filterToOne(hasTestTag("${AGENT_FAX}_${position}_LABEL"))
            .assertTextEquals("Fax")
        composeRule.onNodeWithTag(
            testTag = "${AGENT_FAX}_${position}"
        ).onChildren()
            .filterToOne(hasTestTag("${AGENT_FAX}_${position}_VALUE"))
            .assertTextEquals(number)

        return this
    }

    fun assertAgentFaxContactNotDisplayed(position: Int): AgentComponentRobot {
        composeRule.assertTagDoesNotExist(tag = "${AGENT_FAX}_$position")
        return this
    }

    fun assertAgentEmailContact(
        email: String,
        position: Int
    ): AgentComponentRobot {
        composeRule.onNodeWithTag(
            testTag = "${AGENT_EMAIL}_${position}"
        ).onChildren()
            .filterToOne(hasTestTag("${AGENT_EMAIL}_${position}_LABEL"))
            .assertTextEquals("Email")
        composeRule.onNodeWithTag(
            testTag = "${AGENT_EMAIL}_${position}"
        ).onChildren()
            .filterToOne(hasTestTag("${AGENT_EMAIL}_${position}_VALUE"))
            .assertTextEquals(email)
        return this
    }

    fun assertAgentEmailContactNotDisplayed(position: Int): AgentComponentRobot {
        composeRule.assertTagDoesNotExist(tag = "${AGENT_EMAIL}_$position")
        return this
    }
}

data class AgentComponentRobotInitData(
    val agents: List<Agent>
) : TestRobotInitData