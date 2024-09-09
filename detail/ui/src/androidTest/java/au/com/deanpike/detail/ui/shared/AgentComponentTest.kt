package au.com.deanpike.detail.ui.shared

import au.com.deanpike.detail.client.model.detail.Agent
import au.com.deanpike.detail.client.model.detail.PhoneNumber
import au.com.deanpike.detail.client.model.type.PhoneNumberType
import au.com.deanpike.detail.ui.framework.robot.AgentComponentRobot
import au.com.deanpike.detail.ui.framework.robot.AgentComponentRobotInitData
import au.com.deanpike.uitestshared.base.UiUnitTestBase
import org.junit.Test

class AgentComponentTest : UiUnitTestBase() {
    private val robot = AgentComponentRobot(composeTestRule)

    @Test
    fun should_show_agent_data() {
        robot
            .setupComponent(
                data = AgentComponentRobotInitData(
                    agent = agent
                )
            )
            .assertLayoutDisplayed()
            .assertAgentName("Riccardo Romolo")
            .assertAgentImage()
            .assertAgentMobileContact("0452 184 976")
            .assertAgentGeneralContact("02 9609 7099")
            .assertAgentFaxContact("02 9609 2370")
            .assertAgentEmailContact("riccardo.romolo@raywhite.com")
    }


    private val agent = Agent(
        id = "1697102",
        address = """Shop 1H, 1183-1187 The Horsley Drive\nWetherill Park NSW 2164""",
        name = "Riccardo Romolo",
        imageUrl = """https://images.domain.com.au/img/2373/contact_1697102.jpeg?buster=2024-06-03""",
        emailAddress = "riccardo.romolo@raywhite.com",
        phoneNumbers = listOf(
            PhoneNumber(
                type = PhoneNumberType.MOBILE,
                label = "Mobile",
                number = "0452 184 976"
            ),
            PhoneNumber(
                type = PhoneNumberType.GENERAL,
                label = "General",
                number = "02 9609 7099"
            ),
            PhoneNumber(
                type = PhoneNumberType.FAX,
                label = "Fax",
                number = "02 9609 2370"
            )
        )
    )
}