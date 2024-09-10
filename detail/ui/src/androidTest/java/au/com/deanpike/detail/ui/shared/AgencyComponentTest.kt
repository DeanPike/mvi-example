package au.com.deanpike.detail.ui.shared

import au.com.deanpike.detail.client.model.detail.Advertiser
import au.com.deanpike.detail.client.model.detail.Agent
import au.com.deanpike.detail.client.model.detail.PhoneNumber
import au.com.deanpike.detail.client.model.type.PhoneNumberType
import au.com.deanpike.detail.ui.framework.robot.AgencyComponentRobot
import au.com.deanpike.detail.ui.framework.robot.AgencyComponentRobotInitData
import au.com.deanpike.uitestshared.base.UiUnitTestBase
import org.junit.Test

class AgencyComponentTest : UiUnitTestBase() {
    private val agencyRobot = AgencyComponentRobot(composeTestRule)

    @Test
    fun should_show_agency_component() {
        agencyRobot
            .setupComponent(
                data = AgencyComponentRobotInitData(
                    advertiser = advertiser
                )
            )
            .assertLayoutDisplayed()
            .assertAgentLabel("Agent")
            .assertAgencyName("Ray White Wetherill Park")
            .assertAgencyAddress("Shop 1H, 1183-1187 The Horsley Drive\nWetherill Park NSW 2164")
            .assertAgentName(
                id = "1697102",
                name = "Riccardo Romolo"
            )
            .assertAgentName(
                id = "1350251",
                name = "Marcus Biasetto with added text to make the name wrap over two lines"
            )
    }

    private val advertiser = Advertiser(
        id = 2373,
        name = "Ray White Wetherill Park",
        address = "Shop 1H, 1183-1187 The Horsley Drive\nWetherill Park NSW 2164",
        logoUrl = """https://images.domain.com.au/img/Agencys/2373/logo_2373.jpg?buster=2024-06-03""",
        agencyBannerImageUrl = """https://images.domain.com.au/img/Agencys/2373/banner_2373.jpg?buster=2024-06-03""",
        preferredColorHex = "#FEE536",
        agencyListingContacts = listOf(
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
                    )
                )
            ),
            Agent(
                id = "1350251",
                address = """Shop 1H, 1183-1187 The Horsley Drive\nWetherill Park NSW 2164""",
                name = "Marcus Biasetto with added text to make the name wrap over two lines",
                imageUrl = """https://images.domain.com.au/img/2373/contact_1350251.png?buster=2024-06-05""",
                emailAddress = "marcus.biasetto@raywhite.com",
                phoneNumbers = listOf(
                    PhoneNumber(
                        type = PhoneNumberType.MOBILE,
                        label = "Mobile",
                        number = "0414 246 947"
                    )
                )
            )
        )
    )
}