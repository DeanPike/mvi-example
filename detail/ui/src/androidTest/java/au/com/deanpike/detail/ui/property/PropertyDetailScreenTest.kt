package au.com.deanpike.detail.ui.property

import au.com.deanpike.commonshared.model.Media
import au.com.deanpike.commonshared.type.MediaType
import au.com.deanpike.datashared.type.ListingType
import au.com.deanpike.detail.client.model.detail.Advertiser
import au.com.deanpike.detail.client.model.detail.Agent
import au.com.deanpike.detail.client.model.detail.PhoneNumber
import au.com.deanpike.detail.client.model.detail.PropertyDetail
import au.com.deanpike.detail.client.model.type.PhoneNumberType
import au.com.deanpike.detail.ui.framework.robot.AgencyComponentRobot
import au.com.deanpike.detail.ui.framework.robot.AgentComponentRobot
import au.com.deanpike.detail.ui.framework.robot.PropertyDetailScreenRobot
import au.com.deanpike.detail.ui.framework.robot.PropertyDetailScreenRobotInitData
import au.com.deanpike.uishared.base.ScreenStateType
import au.com.deanpike.uitestshared.base.UiUnitTestBase
import au.com.deanpike.uitestshared.robot.AgencyBannerComponentRobot
import au.com.deanpike.uitestshared.robot.ErrorComponentRobot
import au.com.deanpike.uitestshared.robot.ListingDetailImagesComponentRobot
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class PropertyDetailScreenTest : UiUnitTestBase() {

    private val propertyDetailRobot = PropertyDetailScreenRobot(composeTestRule)
    private val errorRobot = ErrorComponentRobot(composeTestRule)
    private val imageRobot = ListingDetailImagesComponentRobot(composeTestRule)
    private val agencyBannerRobot = AgencyBannerComponentRobot(composeTestRule)
    private val agencyRobot = AgencyComponentRobot(composeTestRule)
    private val agentRobot = AgentComponentRobot(composeTestRule)

    @Test
    fun should_display_progress() {
        propertyDetailRobot
            .setupComponent(
                data = PropertyDetailScreenRobotInitData(
                    state = PropertyDetailScreenState(
                        screenState = ScreenStateType.LOADING
                    )
                )
            )
            .assertLayoutDisplayed()
            .assertProgressDisplayed()
    }

    @Test
    fun should_handle_error() {
        propertyDetailRobot
            .setupComponent(
                data = PropertyDetailScreenRobotInitData(
                    state = PropertyDetailScreenState(
                        screenState = ScreenStateType.ERROR,
                        propertyId = 1234,
                        propertyDetail = null
                    )
                )
            )
            .assertLayoutDisplayed()

        errorRobot
            .assertTitle("Something went wrong")
            .assertMessage("Please check your network connection and try again")
            .assertButtonText("Retry")
            .clickRetryButton()

        assertThat(propertyDetailRobot.retryClicked).isTrue()
    }

    @Test
    fun should_show_property_detail() {

        propertyDetailRobot
            .setupComponent(
                data = PropertyDetailScreenRobotInitData(
                    state = PropertyDetailScreenState(
                        screenState = ScreenStateType.SUCCESS,
                        propertyId = 1234,
                        propertyDetail = getPropertyDetail()
                    )
                )
            )
            .assertLayoutDisplayed()
            .assertProgressNotDisplayed()
            .assertCloseIconDisplayed()
            .assertPriceDisplayed("$1,000,000")
            .assertAddressDisplayed("2 Glenton Street, Abbotsbury")
            .assertBedroomDisplayed("4")
            .assertBathroomDisplayed("3")
            .assertCarSpaceDisplayed("2")
            .assertDwellingDisplayed("House")
            .assertHeadlineDisplayed("Neat Corner Block Home on 657sqm")
            .assertDescriptionDisplayed()
            .clickCloseIcon()

        assertThat(propertyDetailRobot.closeClicked).isTrue()

        imageRobot.assertImage(0)

        agencyBannerRobot
            .assertLayoutDisplayed()
            .assertImageDisplayed()

        propertyDetailRobot.swipeUp()

        agencyRobot
            .assertLayoutDisplayed()
            .assertAgentLabel()
            .assertAgencyName("Ray White Wetherill Park")
            .assertAgencyAddress("Shop 1H, 1183-1187 The Horsley Drive\nWetherill Park NSW 2164")

        propertyDetailRobot.swipeUp()

        agentRobot
            .assertLayoutDisplayed()
            .assertAgentName(
                name = "Riccardo Romolo",
                position = 0
            )
            .assertAgentImage(position = 0)
            .assertAgentMobileContact(
                value = "0452 184 976",
                position = 0
            )
            .assertAgentGeneralContact(
                value = "02 9609 7099",
                position = 0
            )
            .assertAgentFaxContact(
                value = "02 9609 2370",
                position = 0
            )
            .assertAgentEmailContact(
                email = "riccardo.romolo@raywhite.com",
                position = 0
            )
            .assertAgentImage(position = 1)
            .assertAgentName(
                name = "Marcus Biasetto with added text to make the name wrap over two lines",
                position = 1
            )
            .assertAgentImage(position = 1)
            .assertAgentMobileContact(
                value = "0414 246 947",
                position = 1
            )
            .assertAgentGeneralContact(
                value = "02 9609 7099",
                position = 1
            )
            .assertAgentFaxContact(
                value = "02 9609 2370",
                position = 1
            )
            .assertAgentEmailContact(
                email = "marcus.biasetto@raywhite.com",
                position = 1
            )
    }


    private fun getPropertyDetail(): PropertyDetail {
        val advertiser = Advertiser(
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
            ),
        )

        return PropertyDetail(
            id = 1234,
            listingType = ListingType.PROPERTY,
            address = "2 Glenton Street, Abbotsbury",
            headline = "Neat Corner Block Home on 657sqm",
            description = "Description",
            lifecycleStatus = "Status",
            media = listOf(
                Media(
                    mediaType = MediaType.PHOTO,
                    url = """https://bucket-api.domain.com.au/v1/bucket/image/2018868051_4_1_231030_075432-w5000-h3333"""
                )
            ),
            advertiser = advertiser,
            schools = emptyList(),
            dwellingType = "House",
            price = "$1,000,000",
            bedroomCount = 4,
            bathroomCount = 3,
            carSpaceCount = 2,
            dateSold = "21/05/2024",
            saleType = "Auction"
        )
    }
}