package au.com.deanpike.detail.ui.project

import au.com.deanpike.commonshared.model.Media
import au.com.deanpike.commonshared.type.MediaType
import au.com.deanpike.datashared.type.ListingType
import au.com.deanpike.detail.client.model.detail.Advertiser
import au.com.deanpike.detail.client.model.detail.Agent
import au.com.deanpike.detail.client.model.detail.PhoneNumber
import au.com.deanpike.detail.client.model.detail.ProjectChild
import au.com.deanpike.detail.client.model.detail.ProjectDetail
import au.com.deanpike.detail.client.model.type.PhoneNumberType
import au.com.deanpike.detail.ui.framework.robot.AgencyComponentRobot
import au.com.deanpike.detail.ui.framework.robot.AgentComponentRobot
import au.com.deanpike.detail.ui.framework.robot.ProjectChildrenComponentRobot
import au.com.deanpike.detail.ui.framework.robot.ProjectDetailScreenRobot
import au.com.deanpike.detail.ui.framework.robot.ProjectDetailScreenRobotInitData
import au.com.deanpike.uishared.base.ScreenStateType
import au.com.deanpike.uitestshared.base.UiUnitTestBase
import au.com.deanpike.uitestshared.robot.AgencyBannerComponentRobot
import au.com.deanpike.uitestshared.robot.ErrorComponentRobot
import au.com.deanpike.uitestshared.robot.ListingDetailImagesComponentRobot
import au.com.deanpike.uitestshared.robot.ToolbarComponentRobot
import au.com.deanpike.uitestshared.util.disableAnimations
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ProjectDetailScreenTest : UiUnitTestBase() {
    private val projectDetailRobot = ProjectDetailScreenRobot(composeTestRule)
    private val agencyBannerRobot = AgencyBannerComponentRobot(composeTestRule)
    private val imageRobot = ListingDetailImagesComponentRobot(composeTestRule)
    private val errorRobot = ErrorComponentRobot(composeTestRule)
    private val projectChildrenRobot = ProjectChildrenComponentRobot(composeTestRule)
    private val agencyRobot = AgencyComponentRobot(composeTestRule)
    private val agentRobot = AgentComponentRobot(composeTestRule)
    private val toolbarRobot = ToolbarComponentRobot(composeTestRule)

    @Test
    fun should_display_project_details() {
        composeTestRule.disableAnimations()
        projectDetailRobot
            .setupComponent(
                data = ProjectDetailScreenRobotInitData(
                    project = projectDetail,
                    loadingAddress = "Loading address"
                )
            )
            .assertLayoutDisplayed()
            .scrollProjectChildToPosition(0)
            .assertLoadingScreenNotDisplayed()
            .assertDetailsLayoutDisplayed()
            .assertProjectName("Easterly Wollongong")
            .assertProjectHeadline("Easterly Wollongong Headline")
            .assertProjectDetailDescription("Introducing Easterly, a prestigious collection of 21 elegantly designed 2 & 3 bedroom residences")

        toolbarRobot
            .assertNavigationIconDisplayed()
            .assertToolbarTitle("13 Crown Street, Wollongong")

        errorRobot.assertLayoutNotDisplayed()

        imageRobot
            .assertImage(position = 0)
            .assertImageIndicatorNotDisplayed()

        agencyBannerRobot
            .assertLayoutDisplayed()
            .assertImageDisplayed()

        projectChildrenRobot
            .assertLayoutDisplayed()
            .scrollToPosition(0)
            .assertListingDisplayedAtPosition(
                position = 0,
                listingId = 2019256252
            )

        projectChildrenRobot
            .assertLayoutDisplayed()
            .scrollToPosition(1)
            .assertListingDisplayedAtPosition(
                position = 1,
                listingId = 2019256302
            )

        projectDetailRobot.swipeUp()

        agencyRobot
            .assertLayoutDisplayed()
            .assertAgentLabel()
            .assertAgencyName("Ray White Wetherill Park")
            .assertAgencyAddress("Shop 1H, 1183-1187 The Horsley Drive\nWetherill Park NSW 2164")

        projectDetailRobot.swipeUp()

        agentRobot
            .assertAgentName(
                name = "Riccardo Romolo",
                position = 0
            )
            .assertAgentImage(0)
            .assertAgentMobileContact(
                value = "0452 184 976",
                position = 0
            )
            .assertAgentGeneralContact(
                value = "",
                position = 0
            )
            .assertAgentFaxContact(
                value = "",
                position = 0
            )
            .assertAgentEmailContact(
                email = "riccardo.romolo@raywhite.com",
                position = 0
            )

        toolbarRobot.clickBack()

        assertThat(projectDetailRobot.closeClicked).isTrue()

        projectChildrenRobot.scrollToPosition(1)
        projectChildrenRobot.clickOnChild(1)
        assertThat(projectDetailRobot.selectedProjectChildId).isEqualTo(2019256302)
    }

    @Test
    fun should_display_project_loading_screen() {
        composeTestRule.disableAnimations()

        projectDetailRobot
            .setupComponent(
                data = ProjectDetailScreenRobotInitData(
                    project = ProjectDetailScreenState(
                        screenState = ScreenStateType.LOADING,
                        projectId = 6303,
                        projectDetail = null
                    ),
                    loadingAddress = "Loading address"
                )
            )
            .assertLayoutDisplayed()
            .assertLoadingScreenDisplayed()
            .assertLoadingTitleDisplayed()
            .assertLoadingAddressDisplayed("Loading address")
            .assertDetailsLayoutNotDisplayed()

        errorRobot
            .assertLayoutNotDisplayed()
    }

    @Test
    fun should_display_project_details_error() {
        projectDetailRobot
            .setupComponent(
                data = ProjectDetailScreenRobotInitData(
                    project = ProjectDetailScreenState(
                        screenState = ScreenStateType.ERROR,
                        projectId = 6303,
                        projectDetail = null
                    ),
                    loadingAddress = "Loading address"
                )
            )
            .assertLayoutDisplayed()
            .assertLoadingScreenNotDisplayed()
            .assertDetailsLayoutNotDisplayed()

        errorRobot
            .assertLayoutDisplayed()
            .assertTitle("Something went wrong")
            .assertMessage("Please check your network connection and try again")
            .assertButtonText("Retry")
            .clickRetryButton()

        assertThat(projectDetailRobot.retryClicked).isTrue()
    }

    private val projectDetail = ProjectDetailScreenState(
        screenState = ScreenStateType.SUCCESS,
        projectId = 6303,
        projectDetail = ProjectDetail(
            id = 6303,
            listingType = ListingType.PROJECT,
            address = "13 Crown Street, Wollongong",
            headline = "Easterly Wollongong Headline",
            description = "Introducing Easterly, a prestigious collection of 21 elegantly designed 2 & 3 bedroom residences, accompanied by 3 spectacular penthouses, located at 13 Crown Street, Wollongong. \n\nAwaken to the gentle rhythm of waves, bask in sweeping ocean panoramas, and savour the refined sophistication of your impeccably crafted residence. Positioned directly opposite WIN Stadium and mere steps from the beach, Easterly offers an unparalleled coastal lifestyle.\n\nExplore Wollongong's premier attractions nearby, from the historic Flagstaff Point Lighthouse to the serene Wollongong Botanic Gardens, & Blue Mile Pathway. Delight in the vibrant local dining scene, with charming cafes, stylish bars, and gourmet restaurants just moments away, enriching the allure of this iconic location.\n\nDeveloped by Level 33, Easterly epitomizes coastal living with its commitment to privacy, exclusivity, and unparalleled craftsmanship. In collaboration with Turner Architects, the architectural design seamlessly integrates the natural allure of the ocean with the enduring strength of the land, capturing the essence of coastal beauty in both structure and interiors.\n\nExperience coastal living redefined with Easterly's expansive balconies, floor-to-ceiling windows, premium Gaggenau appliances, luxurious stone finishes, bespoke joinery, spa-inspired bathrooms with freestanding baths, and tranquil bedrooms finished with custom wardrobes.\n\nWelcome to elevated coastal living – welcome to Easterly.",
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
    )
}