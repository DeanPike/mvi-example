package au.com.deanpike.detail.ui.unit.screen.shared

import au.com.deanpike.detail.client.model.detail.Agent
import au.com.deanpike.detail.client.model.detail.PhoneNumber
import au.com.deanpike.detail.client.model.type.PhoneNumberType
import au.com.deanpike.detail.ui.framework.ability.AgentComponentAbility
import au.com.deanpike.detail.ui.shared.AgentComponent
import au.com.deanpike.uishared.theme.MviExampleTheme
import au.com.deanpike.uitestshared.base.UiUnitTestBase
import au.com.deanpike.uitestshared.util.advanceTimeAndWait
import org.junit.Test

class AgentComponentTest : UiUnitTestBase() {
    private val ability = AgentComponentAbility(composeTestRule)

    @Test
    fun should_show_agent_data() {
        with(composeTestRule) {
            setContent {
                MviExampleTheme {
                    AgentComponent(
                        position = 0,
                        agent =
                        Agent(
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
                    )

                }
            }
            advanceTimeAndWait()

            with(ability) {
                assertAgentCardDisplayed(0)
                assertAgentNameDisplayed(position = 0, name = "Riccardo Romolo")
                assertAgentImageDisplayed(0)
                assertAgentMobileContactDisplayed(position = 0, number = "0452 184 976")
                assertAgentGeneralContactDisplayed(position = 0, number = "02 9609 7099")
                assertAgentFaxContactDisplayed(position = 0, number = "02 9609 2370")
                assertAgentEmailContactDisplayed(position = 0, email = "riccardo.romolo@raywhite.com")
            }
        }
    }
}