package au.com.deanpike.detail.ui.unit.screen.project

import au.com.deanpike.commonshared.model.Media
import au.com.deanpike.commonshared.type.MediaType
import au.com.deanpike.datashared.type.ListingType
import au.com.deanpike.detail.client.model.detail.Advertiser
import au.com.deanpike.detail.client.model.detail.Agent
import au.com.deanpike.detail.client.model.detail.PhoneNumber
import au.com.deanpike.detail.client.model.detail.ProjectChild
import au.com.deanpike.detail.client.model.detail.ProjectDetail
import au.com.deanpike.detail.client.model.type.PhoneNumberType
import au.com.deanpike.detail.ui.framework.ability.AgencyComponentAbility
import au.com.deanpike.detail.ui.framework.ability.AgentComponentAbility
import au.com.deanpike.detail.ui.framework.ability.ProjectChildComponentAbility
import au.com.deanpike.detail.ui.framework.ability.ProjectChildrenComponentAbility
import au.com.deanpike.detail.ui.framework.ability.ProjectDetailScreenAbility
import au.com.deanpike.detail.ui.project.ProjectDetailScreenContent
import au.com.deanpike.detail.ui.project.ProjectDetailScreenState
import au.com.deanpike.uishared.base.ScreenStateType
import au.com.deanpike.uishared.theme.MviExampleTheme
import au.com.deanpike.uitestshared.ability.AgencyBannerAbility
import au.com.deanpike.uitestshared.ability.ErrorComponentAbility
import au.com.deanpike.uitestshared.ability.ListingDetailImagesAbility
import au.com.deanpike.uitestshared.ability.PropertyDetailComponentAbility
import au.com.deanpike.uitestshared.base.UiUnitTestBase
import au.com.deanpike.uitestshared.util.advanceTimeAndWait
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ProjectDetailScreenTest : UiUnitTestBase() {
    private val ability = ProjectDetailScreenAbility(composeTestRule)
    private val imagesAbility = ListingDetailImagesAbility(composeTestRule)
    private val agencyBannerAbility = AgencyBannerAbility(composeTestRule)
    private val projectChildrenAbility = ProjectChildrenComponentAbility(composeTestRule)
    private val projectChildAbility = ProjectChildComponentAbility(composeTestRule)
    private val propertyDetailAbility = PropertyDetailComponentAbility(composeTestRule)
    private val agencyAbility = AgencyComponentAbility(composeTestRule)
    private val agentAbility = AgentComponentAbility(composeTestRule)
    private val errorAbility = ErrorComponentAbility(composeTestRule)

    @Test
    fun should_display_project_loading_screen() {
        with(composeTestRule) {
            setContent {
                MviExampleTheme {
                    ProjectDetailScreenContent(
                        state = ProjectDetailScreenState(
                            screenState = ScreenStateType.LOADING,
                            projectId = 6303,
                            projectDetail = null
                        )
                    )
                }
            }
            advanceTimeAndWait()
        }

        with(ability) {
            assertProjectLayoutDisplayed()
            assertProgressDisplayed()
            assertErrorNotDisplayed()
            assertProjectDetailsNotDisplayed()
        }
    }

    @Test
    fun should_display_project_details() {
        var closeClicked = false
        var projectChildClicked: Long? = null
        with(composeTestRule) {
            setContent {
                MviExampleTheme {
                    ProjectDetailScreenContent(
                        state = ProjectDetailScreenState(
                            screenState = ScreenStateType.SUCCESS,
                            projectId = 6303,
                            projectDetail = ProjectDetail(
                                id = 6303,
                                listingType = ListingType.PROJECT,
                                address = "13 Crown Street, Wollongong",
                                headline = "Easterly Wollongong",
                                description = "Introducing ‘Easterly’, a prestigious collection of 21 elegantly designed 2 & 3 bedroom residences, accompanied by 3 spectacular penthouses, located at 13 Crown Street, Wollongong. \n\nAwaken to the gentle rhythm of waves, bask in sweeping ocean panoramas, and savour the refined sophistication of your impeccably crafted residence. Positioned directly opposite WIN Stadium and mere steps from the beach, Easterly offers an unparalleled coastal lifestyle.\n\nExplore Wollongong's premier attractions nearby, from the historic Flagstaff Point Lighthouse to the serene Wollongong Botanic Gardens, & Blue Mile Pathway. Delight in the vibrant local dining scene, with charming cafes, stylish bars, and gourmet restaurants just moments away, enriching the allure of this iconic location.\n\nDeveloped by Level 33, Easterly epitomizes coastal living with its commitment to privacy, exclusivity, and unparalleled craftsmanship. In collaboration with Turner Architects, the architectural design seamlessly integrates the natural allure of the ocean with the enduring strength of the land, capturing the essence of coastal beauty in both structure and interiors.\n\nExperience coastal living redefined with Easterly's expansive balconies, floor-to-ceiling windows, premium Gaggenau appliances, luxurious stone finishes, bespoke joinery, spa-inspired bathrooms with freestanding baths, and tranquil bedrooms finished with custom wardrobes.\n\nWelcome to elevated coastal living – welcome to Easterly.",
                                media = listOf(
                                    Media(
                                        mediaType = MediaType.PHOTO,
                                        url = "https://bucket-api.domain.com.au/v1/bucket/image/915bae83-b78c-449d-a646-8c29a786e0c6-w2500-h906"
                                    )
                                ),
                                advertiser = Advertiser(
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
                                        )
                                    ),
                                ),
                                schools = emptyList(),
                                dwellingType = "New Apartments / Off the Plan",
                                childListings = listOf(
                                    ProjectChild(
                                        id = 2019256252,
                                        bedroomCount = 2,
                                        bathroomCount = 2,
                                        carSpaceCount = 1,
                                        price = "Starting from $2,000,000",
                                        propertyTypeDescription = "newApartments",
                                        propertyUrl = "https://www.domain.com.au/13-crown-street-wollongong-nsw-2500-2019256252",
                                        propertyImage = "https://bucket-api.domain.com.au/v1/bucket/image/2019256252_1_1_240521_034448-w3000-h1875",
                                        lifecycleStatus = "New Home"
                                    ),
                                    ProjectChild(
                                        id = 2019256302,
                                        bedroomCount = 3,
                                        bathroomCount = 2,
                                        carSpaceCount = 1,
                                        price = "Starting from $3,250,000",
                                        propertyTypeDescription = "newApartments",
                                        propertyUrl = "https://www.domain.com.au/13-crown-street-wollongong-nsw-2500-2019256302",
                                        propertyImage = "https://bucket-api.domain.com.au/v1/bucket/image/2019256252_5_1_240521_034450-w2500-h1468",
                                        lifecycleStatus = "Apartment"
                                    )
                                ),
                                projectName = "Easterly Wollongong",
                                projectColorHex = "#C8BDB1",
                                projectLogoImageUrl = "https://images.domain.com.au/img/Agencys/devproject/logo_6303_240606_070452",
                                showroomAddress = "49 Denison Street, Wollongong"
                            )
                        ),
                        onCloseClicked = {
                            closeClicked = true
                        },
                        onProjectChildClicked = { projectId ->
                            projectChildClicked = projectId
                        }
                    )
                }
            }
            advanceTimeAndWait()
        }

        with(ability) {
            assertErrorNotDisplayed()
            assertProgressNotDisplayed()
            assertProjectDetailsDisplayed()
            assertCloseDisplayed()
            assertProjectNameDisplayed("Easterly Wollongong")
            assertProjectAddressDisplayed("13 Crown Street, Wollongong")
            assertProjectHeadlineDisplayed("Easterly Wollongong")
            assertProjectDetailDescriptionDisplayed()
            assertProjectChildrenDisplayed()
            assertAgentLabelDisplayed()
            clickOnClose()
        }

        assertThat(closeClicked).isTrue()

        imagesAbility.assertImagesDisplayed()

        agencyBannerAbility.assertAgencyImageDisplayed(position = 0)

        projectChildrenAbility.assertProjectChildrenDisplayed()

        with(projectChildAbility) {
            assertChildListingDisplayed(position = 0)
            assertChildImageDisplayed(position = 0)
            assertLifecycleDisplayed(position = 0, text = "New Home")
            assertChildPriceDisplayed(position = 0, text = "Starting from $2,000,000")
            clickChild(0)
        }
        assertThat(projectChildClicked).isEqualTo(2019256252)
        with(propertyDetailAbility) {
            assertGroupDisplayed(parentPosition = 0, position = 0)
            assertBedroomDisplayed(
                parentPosition = 0,
                position = 0,
                text = "2"
            )
            assertBathroomDisplayed(
                parentPosition = 0,
                position = 0,
                text = "2"
            )
            assertCarSpaceDisplayed(
                parentPosition = 0,
                position = 0,
                text = "1"
            )
            assertDwellingTypeNotDisplayed(
                parentPosition = 0,
                position = 0
            )
        }


        with(projectChildAbility) {
            projectChildrenAbility.scrollToChild(1)
            assertChildListingDisplayed(1)
            assertChildImageDisplayed(position = 1)
            assertLifecycleDisplayed(position = 1, text = "Apartment")
            assertChildPriceDisplayed(position = 1, text = "Starting from $3,250,000")
            clickChild(1)
        }

        assertThat(projectChildClicked).isEqualTo(2019256302)

        with(propertyDetailAbility) {
            assertGroupDisplayed(parentPosition = 0, position = 1)
            assertBedroomDisplayed(
                parentPosition = 0,
                position = 1,
                text = "3"
            )
            assertBathroomDisplayed(
                parentPosition = 0,
                position = 1,
                text = "2"
            )
            assertCarSpaceDisplayed(
                parentPosition = 0,
                position = 1,
                text = "1"
            )
            assertDwellingTypeNotDisplayed(
                parentPosition = 0,
                position = 0
            )
        }

        with(agencyAbility) {
            scrollToAgency()
            assertAgencyLayoutDisplayed()
            assertAgencyNameDisplayed("Ray White Wetherill Park")
            assertAgencyAddressDisplayed("Shop 1H, 1183-1187 The Horsley Drive\nWetherill Park NSW 2164")
        }

        with(agentAbility) {
            scrollToAgent(position = 0)
            assertAgentCardDisplayed(position = 0)
            assertAgentNameDisplayed(position = 0, name = "Riccardo Romolo")
            assertAgentImageDisplayed(position = 0)
            assertAgentMobileContactDisplayed(position = 0, "0452 184 976")
            assertGeneralContactNotDisplayed(position = 0)
            assertFaxContactNotDisplayed(position = 0)
        }
    }

    @Test
    fun should_display_project_details_error() {
        var retryClicked = false
        with(composeTestRule) {
            setContent {
                MviExampleTheme {
                    ProjectDetailScreenContent(
                        state = ProjectDetailScreenState(
                            screenState = ScreenStateType.ERROR,
                            projectId = 6303,
                            projectDetail = null
                        ),
                        onRetryClicked = {
                            retryClicked = true
                        }
                    )
                }
            }
            advanceTimeAndWait()
        }

        with(ability) {
            assertErrorDisplayed()
            assertProgressNotDisplayed()
            assertProjectDetailsNotDisplayed()
        }

        with(errorAbility) {
            assertTitle()
            assertMessage()
            assertRetryButtonDisplayed()
            clickRetryButton()
        }

        assertThat(retryClicked).isTrue()
    }
}