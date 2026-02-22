package au.com.deanpike.detail.ui.property

import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import au.com.deanpike.commonshared.model.Media
import au.com.deanpike.commonshared.type.MediaType
import au.com.deanpike.datashared.type.ListingType
import au.com.deanpike.detail.client.model.detail.Advertiser
import au.com.deanpike.detail.client.model.detail.Agent
import au.com.deanpike.detail.client.model.detail.PhoneNumber
import au.com.deanpike.detail.client.model.detail.PropertyDetail
import au.com.deanpike.detail.client.model.type.PhoneNumberType
import au.com.deanpike.detail.ui.R
import au.com.deanpike.detail.ui.property.PropertyDetailScreenTestTags.PROPERTY_DETAILS_LAYOUT
import au.com.deanpike.detail.ui.property.PropertyDetailScreenTestTags.PROPERTY_DETAIL_BACK_BUTTON
import au.com.deanpike.detail.ui.property.PropertyDetailScreenTestTags.PROPERTY_DETAIL_DESCRIPTION
import au.com.deanpike.detail.ui.property.PropertyDetailScreenTestTags.PROPERTY_DETAIL_HEADLINE
import au.com.deanpike.detail.ui.property.PropertyDetailScreenTestTags.PROPERTY_DETAIL_LOADING_ADDRESS
import au.com.deanpike.detail.ui.property.PropertyDetailScreenTestTags.PROPERTY_DETAIL_LOADING_TITLE
import au.com.deanpike.detail.ui.property.PropertyDetailScreenTestTags.PROPERTY_DETAIL_PROGRESS
import au.com.deanpike.detail.ui.property.PropertyDetailScreenTestTags.PROPERTY_DETAIL_SUCCESS_ADDRESS
import au.com.deanpike.detail.ui.shared.AgencyComponent
import au.com.deanpike.uishared.base.ScreenStateType
import au.com.deanpike.uishared.component.AgencyBannerComponent
import au.com.deanpike.uishared.component.BedBathCarComponent
import au.com.deanpike.uishared.component.ErrorComponent
import au.com.deanpike.uishared.component.ExpandableText
import au.com.deanpike.uishared.component.ListingImagesComponent
import au.com.deanpike.uishared.component.PriceComponent
import au.com.deanpike.uishared.theme.Dimension.DIM_16
import au.com.deanpike.uishared.theme.Dimension.DIM_4
import au.com.deanpike.uishared.theme.Dimension.DIM_8
import au.com.deanpike.uishared.theme.MviExampleTheme
import au.com.deanpike.uishared.util.NavigationBarScrim
import au.com.deanpike.uishared.util.SetStatusBarAppearance
import au.com.deanpike.uishared.util.SetupStatusBar
import au.com.deanpike.uishared.util.StatusBarGradient

@Composable
fun PropertyDetailScreen(
    viewModel: PropertyDetailViewModel = hiltViewModel<PropertyDetailViewModel>(),
    isSinglePane: Boolean,
    propertyId: Long,
    loadingAddress: String,
    onCloseClicked: () -> Unit = {}
) {
    LaunchedEffect(propertyId) {
        viewModel.setEvent(PropertyDetailScreenEvent.Initialise(propertyId = propertyId))
    }

    if (isSinglePane) {
        SetStatusBarAppearance(useDarkIcons = !isSystemInDarkTheme())
    }

    PropertyDetailScreenContent(
        state = viewModel.uiState,
        onEvent = {
            viewModel.setEvent(it)
        },
        loadingAddress = loadingAddress,
        onCloseClicked = onCloseClicked
    )
}

@Composable
fun PropertyDetailScreenContent(
    state: PropertyDetailScreenState,
    loadingAddress: String,
    onEvent: (PropertyDetailScreenEvent) -> Unit = {},
    onCloseClicked: () -> Unit = {}
) {

    BackHandler {
        onCloseClicked()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.background)
            .testTag(PROPERTY_DETAILS_LAYOUT)
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
                            .testTag(PROPERTY_DETAIL_LOADING_TITLE),
                        text = stringResource(R.string.loading_data_for),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(DIM_16))
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag(PROPERTY_DETAIL_LOADING_ADDRESS),
                        text = loadingAddress,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.labelLarge
                    )
                    Spacer(modifier = Modifier.height(DIM_16))
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .testTag(PROPERTY_DETAIL_PROGRESS)
                    )
                }
            }
        } else if (state.screenState == ScreenStateType.SUCCESS) {
            PropertyDetailSuccess(
                state = state,
                onBackClicked = onCloseClicked
            )
        } else if (state.screenState == ScreenStateType.ERROR) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                ErrorComponent(
                    onRetryClicked = {
                        onEvent(PropertyDetailScreenEvent.OnRetryClicked)
                    }
                )
            }
        }
    }
}

@Composable
fun PropertyDetailSuccess(
    state: PropertyDetailScreenState,
    onBackClicked: () -> Unit = {}
) {
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()
    val activity = LocalActivity.current

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(
                    start = 0.dp,
                    end = 0.dp,
                    top = 0.dp,
                    bottom = 0.dp
                )
                .verticalScroll(scrollState)
                .align(Alignment.Center)
        ) {
            ListingImagesComponent(
                screenState = state.screenState,
                scope = scope,
                media = state.propertyDetail?.media ?: emptyList()
            )

            AgencyBannerComponent(
                agencyColour = state.propertyDetail?.advertiser?.preferredColorHex,
                logo = state.propertyDetail?.advertiser?.logoUrl
            )

            Text(
                modifier = Modifier
                    .padding(
                        start = DIM_16,
                        end = DIM_16,
                        top = DIM_4
                    )
                    .testTag(PROPERTY_DETAIL_SUCCESS_ADDRESS),
                text = state.propertyDetail?.address ?: "",
                style = MaterialTheme.typography.labelLarge
            )

            HorizontalDivider(
                modifier = Modifier.padding(top = DIM_8, bottom = DIM_8)
            )


            PriceComponent(
                modifier = Modifier.padding(horizontal = DIM_16, vertical = DIM_8),
                price = state.propertyDetail?.price ?: ""
            )
            BedBathCarComponent(
                modifier = Modifier.padding(start = DIM_16, end = DIM_16),
                bedrooms = state.propertyDetail?.bedroomCount,
                bathrooms = state.propertyDetail?.bathroomCount,
                carSpaces = state.propertyDetail?.carSpaceCount
            )
            HorizontalDivider(
                modifier = Modifier.padding(top = DIM_8, bottom = DIM_8)
            )
            state.propertyDetail?.headline?.let {
                Text(
                    modifier = Modifier
                        .padding(start = DIM_16, end = DIM_16, top = DIM_8)
                        .testTag(PROPERTY_DETAIL_HEADLINE),
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            state.propertyDetail?.description?.let {
                ExpandableText(
                    modifier = Modifier
                        .padding(start = DIM_16, end = DIM_16, top = DIM_8, bottom = DIM_8)
                        .testTag(PROPERTY_DETAIL_DESCRIPTION),
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    fontStyle = MaterialTheme.typography.bodyMedium.fontStyle,
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

            state.propertyDetail?.advertiser?.let { advertiser ->
                AgencyComponent(advertiser = advertiser)
            }
        }

        activity?.let {
            SetupStatusBar(it)
        }
        StatusBarGradient()
        NavigationBarScrim()

        IconButton(
            modifier = Modifier
                .windowInsetsPadding(WindowInsets.statusBars)
                .align(Alignment.TopStart),
            onClick = { onBackClicked() },
            shape = CircleShape,
            colors = IconButtonDefaults.iconButtonColors().copy(
                containerColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4F)
            )
        ) {
            Icon(
                modifier = Modifier.testTag(PROPERTY_DETAIL_BACK_BUTTON),
                painter = painterResource(au.com.deanpike.uishared.R.drawable.arrow_back_24),
                contentDescription = stringResource(au.com.deanpike.uishared.R.string.back),
                tint = MaterialTheme.colorScheme.background,
            )
        }
    }
}

object PropertyDetailScreenTestTags {
    private const val PREFIX = "PROPERTY_DETAIL_"
    const val PROPERTY_DETAILS_LAYOUT = "${PREFIX}LAYOUT"
    const val PROPERTY_DETAIL_PROGRESS = "${PREFIX}PROGRESS"
    const val PROPERTY_DETAIL_HEADLINE = "${PREFIX}HEADLINE"
    const val PROPERTY_DETAIL_DESCRIPTION = "${PREFIX}DESCRIPTION"
    const val PROPERTY_DETAIL_LOADING_TITLE = "${PREFIX}LOADING_TITLE"
    const val PROPERTY_DETAIL_LOADING_ADDRESS = "${PREFIX}LOADING_ADDRESS"
    const val PROPERTY_DETAIL_BACK_BUTTON = "${PREFIX}BACK_BUTTON"
    const val PROPERTY_DETAIL_SUCCESS_ADDRESS = "${PREFIX}SUCCESS_ADDRESS"


}

@Preview
@Composable
fun PropertyDetailScreenContentPreview() {
    MviExampleTheme {
        PropertyDetailScreenContent(
            loadingAddress = "",
            state = PropertyDetailScreenState(
                screenState = ScreenStateType.SUCCESS,
                propertyDetail = PropertyDetail(
                    id = 1234,
                    listingType = ListingType.PROPERTY,
                    address = "2 Glenton Street, Abbotsbury",
                    headline = "Neat Corner Block Home on 657sqm",
                    description = """Rare opportunity to secure this single storey corner block home on 657sqm. Perfect for a downsizer or someone looking for a canvas to knock down & build their dream home! With four bedrooms, two bathrooms & a two car garage this home will cater your needs.\r\n\r\nOutside, the low-maintenance yard is perfect for those who want to spend more time enjoying their home and less time maintaining it. With plenty of natural light throughout the home. Dont miss this chance to get into the Abbotsbury market!\r\n\r\nWhat the dwelling offers;\r\n• Four well appointed bedrooms all with built in wardrobes\r\n• Two Bathrooms (ensuite to main)\r\n• Double garage\r\n• Split system air conditioning \r\n• Separate & spacious kitchen and dining area\r\n• Large outdoor alfresco over looking backyard\r\n• Granny Flat potential (STCA)""",
                    lifecycleStatus = "Sold",
                    media = listOf(
                        Media(
                            mediaType = MediaType.PHOTO,
                            url = "https://bucket-api.domain.com.au/v1/bucket/image/2018868051_1_1_231030_075432-w5000-h3333"
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
                    dwellingType = "House",
                    price = "$1,320,000",
                    bedroomCount = 4,
                    bathroomCount = 3,
                    carSpaceCount = 2,
                    dateSold = "2023-11-25T00:00:00",
                    saleType = "Auction"
                )
            )
        )
    }
}

@Preview
@Composable
fun PropertyDetailScreenProgressPreview() {
    MviExampleTheme {
        PropertyDetailScreenContent(
            loadingAddress = "2 Glenton Street, Abbotsbury",
            state = PropertyDetailScreenState(
                screenState = ScreenStateType.LOADING
            )
        )
    }
}