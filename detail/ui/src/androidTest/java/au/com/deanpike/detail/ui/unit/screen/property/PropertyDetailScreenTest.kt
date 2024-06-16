package au.com.deanpike.detail.ui.unit.screen.property

import au.com.deanpike.commonshared.model.Media
import au.com.deanpike.commonshared.type.MediaType
import au.com.deanpike.datashared.type.ListingType
import au.com.deanpike.detail.client.model.detail.Advertiser
import au.com.deanpike.detail.client.model.detail.Agent
import au.com.deanpike.detail.client.model.detail.PhoneNumber
import au.com.deanpike.detail.client.model.detail.PropertyDetail
import au.com.deanpike.detail.client.model.type.PhoneNumberType
import au.com.deanpike.detail.ui.framework.ability.AgencyComponentAbility
import au.com.deanpike.detail.ui.framework.ability.AgentComponentAbility
import au.com.deanpike.detail.ui.framework.ability.PropertyDetailScreenAbility
import au.com.deanpike.detail.ui.property.PropertyDetailScreenContent
import au.com.deanpike.detail.ui.property.PropertyDetailScreenState
import au.com.deanpike.uishared.base.ScreenStateType
import au.com.deanpike.uishared.theme.MviExampleTheme
import au.com.deanpike.uitestshared.ability.AgencyBannerAbility
import au.com.deanpike.uitestshared.ability.ErrorComponentAbility
import au.com.deanpike.uitestshared.ability.ListingDetailImagesAbility
import au.com.deanpike.uitestshared.base.UiUnitTestBase
import au.com.deanpike.uitestshared.util.advanceTimeAndWait
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class PropertyDetailScreenTest : UiUnitTestBase() {
    private val propertyScreenAbility = PropertyDetailScreenAbility(composeTestRule)
    private val agencyBannerAbility = AgencyBannerAbility(composeTestRule)
    private val agencyAbility = AgencyComponentAbility(composeTestRule)
    private val agentAbility = AgentComponentAbility(composeTestRule)
    private val errorAbility = ErrorComponentAbility(composeTestRule)
    private val imagesAbility = ListingDetailImagesAbility(composeTestRule)

    @Test
    fun should_display_progress() {
        with(composeTestRule) {
            setContent {
                MviExampleTheme {
                    PropertyDetailScreenContent(
                        state = PropertyDetailScreenState(
                            screenState = ScreenStateType.LOADING
                        )
                    )
                }
            }
            advanceTimeAndWait()
        }

        with(propertyScreenAbility) {
            assertScreenDisplayed()
            assertProgressDisplayed()
        }
    }

    @Test
    fun should_handle_error() {
        var retryClicked = false
        with(composeTestRule) {
            setContent {
                MviExampleTheme {
                    PropertyDetailScreenContent(
                        state = PropertyDetailScreenState(
                            screenState = ScreenStateType.ERROR,
                            propertyId = 1234,
                            propertyDetail = null
                        ),
                        onCloseClicked = { },
                        onRetryClicked = {
                            retryClicked = true
                        }
                    )
                }
            }
            advanceTimeAndWait()
        }

        with(errorAbility) {
            assertComponentDisplayed()
            assertTitle()
            assertMessage()
            assertRetryButtonDisplayed()
            clickRetryButton()
        }

        assertThat(retryClicked).isTrue()
    }

    @Test
    fun should_show_property_detail() {
        var closeClicked = false
        with(composeTestRule) {
            setContent {
                MviExampleTheme {
                    PropertyDetailScreenContent(
                        state = PropertyDetailScreenState(
                            screenState = ScreenStateType.SUCCESS,
                            propertyId = 1234,
                            propertyDetail = getPropertyDetail()
                        ),
                        onCloseClicked = {
                            closeClicked = true
                        }
                    )
                }
            }
            advanceTimeAndWait()
        }

        with(propertyScreenAbility) {
            assertScreenDisplayed()
            assertProgressNotDisplayed()
            assertCloseIconDisplayed()
            assertPriceDisplayed("$1,000,000")
            assertAddressDisplayed("2 Glenton Street, Abbotsbury")

            assertBedroomDisplayed("4")
            assertBathroomDisplayed("3")
            assertCarSpaceDisplayed("2")
            assertDwellingDisplayed("House")

            assertHeadlineDisplayed("Neat Corner Block Home on 657sqm")
            assertDescriptionDisplayed()

            clickCloseIcon()
        }

        imagesAbility.assertImagesDisplayed()

        assertThat(closeClicked).isTrue()

        agencyBannerAbility.assertAgencyImageDisplayed(0)

        with(agencyAbility) {
            assertAgencyLayoutDisplayed()
            assertAgencyNameDisplayed("Ray White Wetherill Park")
            assertAgencyAddressDisplayed("Shop 1H, 1183-1187 The Horsley Drive\nWetherill Park NSW 2164")
        }

        with(agentAbility) {
            scrollToAgent(0)
            assertAgentNameDisplayed(position = 0, name = "Riccardo Romolo")
            assertAgentImageDisplayed(0)
            assertAgentMobileContactDisplayed(
                position = 0,
                number = "0452 184 976"
            )
            assertAgentGeneralContactDisplayed(
                position = 0,
                number = "02 9609 7099"
            )
            assertAgentFaxContactDisplayed(
                position = 0,
                number = "02 9609 2370"
            )
            assertAgentEmailContactDisplayed(
                position = 0,
                email = "riccardo.romolo@raywhite.com"
            )

            scrollToAgent(1)
            assertAgentNameDisplayed(position = 1, name = "Marcus Biasetto with added text to make the name wrap over two lines")
            assertAgentImageDisplayed(1)
            assertAgentMobileContactDisplayed(
                position = 1,
                number = "0414 246 947"
            )
            assertAgentGeneralContactDisplayed(
                position = 1,
                number = "02 9609 7099"
            )
            assertAgentFaxContactDisplayed(
                position = 1,
                number = "02 9609 2370"
            )
            assertAgentEmailContactDisplayed(
                position = 1,
                email = "marcus.biasetto@raywhite.com"
            )
        }
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