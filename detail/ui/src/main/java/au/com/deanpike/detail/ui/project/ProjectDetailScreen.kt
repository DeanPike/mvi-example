package au.com.deanpike.detail.ui.project

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import au.com.deanpike.commonshared.model.Media
import au.com.deanpike.commonshared.type.MediaType
import au.com.deanpike.datashared.type.ListingType
import au.com.deanpike.detail.client.model.detail.Advertiser
import au.com.deanpike.detail.client.model.detail.Agent
import au.com.deanpike.detail.client.model.detail.PhoneNumber
import au.com.deanpike.detail.client.model.detail.ProjectChild
import au.com.deanpike.detail.client.model.detail.ProjectDetail
import au.com.deanpike.detail.client.model.type.PhoneNumberType
import au.com.deanpike.detail.ui.R
import au.com.deanpike.detail.ui.project.ProjectDetailScreenTestTags.PROJECT_DETAILS_LAYOUT
import au.com.deanpike.detail.ui.project.ProjectDetailScreenTestTags.PROJECT_DETAIL_DESCRIPTION
import au.com.deanpike.detail.ui.project.ProjectDetailScreenTestTags.PROJECT_DETAIL_HEADLINE
import au.com.deanpike.detail.ui.project.ProjectDetailScreenTestTags.PROJECT_DETAIL_LOADING_ADDRESS
import au.com.deanpike.detail.ui.project.ProjectDetailScreenTestTags.PROJECT_DETAIL_LOADING_TITLE
import au.com.deanpike.detail.ui.project.ProjectDetailScreenTestTags.PROJECT_DETAIL_NAME
import au.com.deanpike.detail.ui.project.ProjectDetailScreenTestTags.PROJECT_DETAIL_PROGRESS
import au.com.deanpike.detail.ui.project.ProjectDetailScreenTestTags.PROJECT_LAYOUT
import au.com.deanpike.detail.ui.shared.AgencyComponent
import au.com.deanpike.uishared.base.ScreenStateType
import au.com.deanpike.uishared.component.AgencyBannerComponent
import au.com.deanpike.uishared.component.ErrorComponent
import au.com.deanpike.uishared.component.ExpandableText
import au.com.deanpike.uishared.component.ListingImagesComponent
import au.com.deanpike.uishared.component.ToolbarComponent
import au.com.deanpike.uishared.theme.Dimension.DIM_16
import au.com.deanpike.uishared.theme.Dimension.DIM_8
import au.com.deanpike.uishared.theme.MviExampleTheme
import au.com.deanpike.uishared.util.SetStatusBarAppearance

@Composable
fun ProjectDetailScreen(
    viewModel: ProjectDetailViewModel = hiltViewModel<ProjectDetailViewModel>(),
    isSinglePane: Boolean,
    projectId: Long,
    loadingAddress: String,
    onCloseClicked: () -> Unit = {},
    onProjectChildClicked: (Long) -> Unit = {}
) {
    LaunchedEffect(projectId) {
        viewModel.setEvent(ProjectDetailScreenEvent.Initialise(projectId = projectId))
    }

    if (isSinglePane) {
        SetStatusBarAppearance(useDarkIcons = false)
    }

    ProjectDetailScreenContent(
        state = viewModel.uiState,
        loadingAddress = loadingAddress,
        onCloseClicked = onCloseClicked,
        onRetryClicked = {
            viewModel.setEvent(ProjectDetailScreenEvent.OnRetryClicked)
        },
        onProjectChildClicked = onProjectChildClicked
    )
}

@Composable
fun ProjectDetailScreenContent(
    state: ProjectDetailScreenState,
    loadingAddress: String,
    onCloseClicked: () -> Unit = {},
    onRetryClicked: () -> Unit = {},
    onProjectChildClicked: (Long) -> Unit = {}
) {
    BackHandler {
        onCloseClicked()
    }

    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .testTag(PROJECT_LAYOUT)
    ) {
        if (state.screenState == ScreenStateType.LOADING) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag(PROJECT_DETAIL_LOADING_TITLE),
                        text = stringResource(R.string.loading_data_for),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(DIM_16))
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag(PROJECT_DETAIL_LOADING_ADDRESS),
                        text = loadingAddress,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.labelLarge
                    )
                    Spacer(modifier = Modifier.height(DIM_16))
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .testTag(PROJECT_DETAIL_PROGRESS)
                    )
                }
            }
        } else if (state.screenState == ScreenStateType.SUCCESS) {
            ProjectDetailSuccess(
                state = state,
                onProjectChildClicked = onProjectChildClicked,
                onBackClicked = onCloseClicked
            )
        } else if (state.screenState == ScreenStateType.ERROR) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                ErrorComponent(
                    onRetryClicked = onRetryClicked
                )
            }
        }
    }
}

@Composable
fun ProjectDetailSuccess(
    state: ProjectDetailScreenState,
    onProjectChildClicked: (Long) -> Unit = {},
    onBackClicked: () -> Unit = {}
) {
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()
    var screenWidth by remember { mutableIntStateOf(0) }

    SetStatusBarAppearance(useDarkIcons = true)
    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            ToolbarComponent(
                title = state.projectDetail?.address ?: "",
                onBackClicked = onBackClicked
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(scrollState)
                .onGloballyPositioned { coordinates ->
                    screenWidth = coordinates.size.width
                }
                .testTag(PROJECT_DETAILS_LAYOUT)
        ) {
            ListingImagesComponent(
                screenState = state.screenState,
                scope = scope,
                media = state.projectDetail?.media ?: emptyList()
            )

            AgencyBannerComponent(
                agencyColour = state.projectDetail?.advertiser?.preferredColorHex,
                logo = state.projectDetail?.advertiser?.logoUrl
            )

            state.projectDetail?.projectName?.let {
                Text(
                    modifier = Modifier
                        .padding(
                            start = DIM_16,
                            end = DIM_16,
                            top = DIM_8
                        )
                        .testTag(PROJECT_DETAIL_NAME),
                    text = it,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            HorizontalDivider(
                modifier = Modifier.padding(top = DIM_8, bottom = DIM_8)
            )

            state.projectDetail?.headline?.let {
                Text(
                    modifier = Modifier
                        .padding(start = DIM_16, end = DIM_16, top = DIM_8)
                        .testTag(PROJECT_DETAIL_HEADLINE),
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            state.projectDetail?.description?.let {
                ExpandableText(
                    modifier = Modifier
                        .padding(start = DIM_16, end = DIM_16, top = DIM_8, bottom = DIM_8)
                        .testTag(PROJECT_DETAIL_DESCRIPTION),
                    text = it,
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                    collapsedMaxLine = 3,
                    showMoreStyle = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.Bold
                    ).toSpanStyle(),
                    showLessStyle = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.Bold
                    ).toSpanStyle()
                )
            }

            HorizontalDivider(
                modifier = Modifier.padding(top = DIM_8, bottom = DIM_8)
            )

            state.projectDetail?.childListings?.let {
                ProjectChildrenComponent(
                    childListings = it,
                    screenWidth = screenWidth,
                    onProjectChildClicked = onProjectChildClicked
                )
            }

            HorizontalDivider(
                modifier = Modifier.padding(top = DIM_8, bottom = DIM_8)
            )

            state.projectDetail?.advertiser?.let {
                AgencyComponent(advertiser = it)
            }
        }
    }
}

object ProjectDetailScreenTestTags {
    private const val PREFIX = "PROJECT_DETAIL_"
    const val PROJECT_LAYOUT = "${PREFIX}LAYOUT"
    const val PROJECT_DETAIL_PROGRESS = "${PREFIX}PROGRESS"
    const val PROJECT_DETAILS_LAYOUT = "${PREFIX}_DETAIL_LAYOUT"
    const val PROJECT_DETAIL_NAME = "${PREFIX}NAME"
    const val PROJECT_DETAIL_HEADLINE = "${PREFIX}HEADLINE"
    const val PROJECT_DETAIL_DESCRIPTION = "${PREFIX}DESCRIPTION"
    const val PROJECT_DETAIL_LOADING_TITLE = "${PREFIX}LOADING_TITLE"
    const val PROJECT_DETAIL_LOADING_ADDRESS = "${PREFIX}LOADING_ADDRESS"
}

@Preview
@Composable
fun ProjectDetailScreenContentPreview() {
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
                            lifecycleStatus = "New Home"
                        )
                    ),
                    projectName = "Easterly Wollongong",
                    projectColorHex = "#C8BDB1",
                    projectLogoImageUrl = "https://images.domain.com.au/img/Agencys/devproject/logo_6303_240606_070452",
                    showroomAddress = "49 Denison Street, Wollongong"
                )
            ),
            loadingAddress = ""
        )
    }
}

@Preview
@Composable
fun ProjectDetailScreenProgressPreview() {
    MviExampleTheme {
        ProjectDetailScreenContent(
            state = ProjectDetailScreenState(
                screenState = ScreenStateType.LOADING
            ),
            loadingAddress = "13 Crown Street, Wollongong"
        )
    }
}