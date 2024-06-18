package au.com.deanpike.detail.ui.project

import au.com.deanpike.commonshared.model.Media
import au.com.deanpike.commonshared.type.MediaType
import au.com.deanpike.commonshared.util.ResponseWrapper
import au.com.deanpike.datashared.dispatcher.DispatcherProvider
import au.com.deanpike.datashared.type.ListingType
import au.com.deanpike.detail.client.model.detail.Advertiser
import au.com.deanpike.detail.client.model.detail.Agent
import au.com.deanpike.detail.client.model.detail.PhoneNumber
import au.com.deanpike.detail.client.model.detail.ProjectChild
import au.com.deanpike.detail.client.model.detail.ProjectDetail
import au.com.deanpike.detail.client.model.type.PhoneNumberType
import au.com.deanpike.detail.client.usecase.ProjectDetailUseCase
import au.com.deanpike.testshared.extension.TestDispatcherExtension
import au.com.deanpike.uishared.base.ScreenStateType
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import okio.IOException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(TestDispatcherExtension::class)
class ProjectDetailViewModelTest {

    private val useCase: ProjectDetailUseCase = mockk()
    private lateinit var viewModel: ProjectDetailViewModel

    @BeforeEach
    fun setupTest(dispatcherProvider: DispatcherProvider) {
        viewModel = ProjectDetailViewModel(
            dispatcher = dispatcherProvider,
            useCase = useCase
        )
    }

    @Test
    fun `should load project details`() = runTest {
        coEvery { useCase.getProjectDetails(6303) } returns ResponseWrapper.Success(getProjectDetails())

        viewModel.setEvent(ProjectDetailScreenEvent.Initialise(projectId = 6303))
        advanceUntilIdle()

        with(viewModel.uiState) {
            assertThat(screenState).isEqualTo(ScreenStateType.SUCCESS)
            assertThat(projectId).isEqualTo(6303)
            assertThat(projectDetail).isNotNull
            assertThat(projectDetail!!.media.count()).isEqualTo(1)
            assertThat(projectDetail!!.advertiser).isNotNull
            assertThat(projectDetail!!.advertiser!!.agencyListingContacts.count()).isEqualTo(1)
            assertThat(projectDetail!!.childListings.count()).isEqualTo(2)
            assertThat(projectDetail!!.childListings[0].id).isEqualTo(2019256252)
            assertThat(projectDetail!!.childListings[1].id).isEqualTo(2019256302)

        }
    }

    @Test
    fun `should handle error when loading project details`() = runTest{
        coEvery { useCase.getProjectDetails(6303) } returns ResponseWrapper.Error(IOException("Error"))

        viewModel.setEvent(ProjectDetailScreenEvent.Initialise(projectId = 6303))
        advanceUntilIdle()

        with(viewModel.uiState) {
            assertThat(screenState).isEqualTo(ScreenStateType.ERROR)
            assertThat(projectId).isEqualTo(6303)
            assertThat(projectDetail).isNull()

        }
    }

    @Test
    fun `should handle retry`() = runTest{
        // Setup
        coEvery { useCase.getProjectDetails(6303) } returns ResponseWrapper.Error(IOException("Error"))

        viewModel.setEvent(ProjectDetailScreenEvent.Initialise(projectId = 6303))
        advanceUntilIdle()

        coVerify(exactly = 1) { useCase.getProjectDetails(6303) }

        clearMocks(useCase)
        // Test
        coEvery { useCase.getProjectDetails(6303) } returns ResponseWrapper.Success(getProjectDetails())

        viewModel.setEvent(ProjectDetailScreenEvent.OnRetryClicked)
        advanceUntilIdle()

        with(viewModel.uiState) {
            assertThat(screenState).isEqualTo(ScreenStateType.SUCCESS)
            assertThat(projectId).isEqualTo(6303)
            assertThat(projectDetail).isNotNull
            assertThat(projectDetail!!.media.count()).isEqualTo(1)
            assertThat(projectDetail!!.advertiser).isNotNull
            assertThat(projectDetail!!.advertiser!!.agencyListingContacts.count()).isEqualTo(1)
            assertThat(projectDetail!!.childListings.count()).isEqualTo(2)
            assertThat(projectDetail!!.childListings[0].id).isEqualTo(2019256252)
            assertThat(projectDetail!!.childListings[1].id).isEqualTo(2019256302)
        }

        coVerify(exactly = 1) { useCase.getProjectDetails(6303) }
    }

    private fun getProjectDetails(): ProjectDetail {
        return ProjectDetail(
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
    }
}