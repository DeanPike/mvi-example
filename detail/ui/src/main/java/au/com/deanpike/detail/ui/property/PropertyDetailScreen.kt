package au.com.deanpike.detail.ui.property

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
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
import au.com.deanpike.detail.ui.property.PropertyDetailScreenTestTags.PROPERTY_DETAIL_ADDRESS
import au.com.deanpike.detail.ui.property.PropertyDetailScreenTestTags.PROPERTY_DETAIL_CLOSE
import au.com.deanpike.detail.ui.property.PropertyDetailScreenTestTags.PROPERTY_DETAIL_DESCRIPTION
import au.com.deanpike.detail.ui.property.PropertyDetailScreenTestTags.PROPERTY_DETAIL_HEADLINE
import au.com.deanpike.detail.ui.property.PropertyDetailScreenTestTags.PROPERTY_DETAIL_PRICE
import au.com.deanpike.detail.ui.property.PropertyDetailScreenTestTags.PROPERTY_DETAIL_PROGRESS
import au.com.deanpike.detail.ui.shared.AgencyComponent
import au.com.deanpike.uishared.base.ScreenStateType
import au.com.deanpike.uishared.base.drawableTestTag
import au.com.deanpike.uishared.component.AgencyBannerComponent
import au.com.deanpike.uishared.component.BedBathCarComponent
import au.com.deanpike.uishared.component.ErrorComponent
import au.com.deanpike.uishared.component.ExpandableText
import au.com.deanpike.uishared.component.ListingDetailImagesComponent
import au.com.deanpike.uishared.theme.Dimension.DIM_16
import au.com.deanpike.uishared.theme.Dimension.DIM_8
import au.com.deanpike.uishared.theme.MviExampleTheme
import au.com.deanpike.uishared.util.SetStatusBarAppearance

@Composable
fun PropertyDetailScreen(
    viewModel: PropertyDetailViewModel = hiltViewModel<PropertyDetailViewModel>(),
    isSinglePane: Boolean,
    propertyId: Long,
    onCloseClicked: () -> Unit = {}
) {
    LaunchedEffect(propertyId) {
        viewModel.setEvent(PropertyDetailScreenEvent.Initialise(propertyId = propertyId))
    }

    if (isSinglePane) {
        SetStatusBarAppearance(useDarkIcons = false)
    }

    PropertyDetailScreenContent(
        state = viewModel.uiState,
        onCloseClicked = onCloseClicked,
        onRetryClicked = {
            viewModel.setEvent(PropertyDetailScreenEvent.OnRetryClicked)
        }
    )
}

@Composable
fun PropertyDetailScreenContent(
    state: PropertyDetailScreenState,
    onCloseClicked: () -> Unit = {},
    onRetryClicked: () -> Unit = {}
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
                CircularProgressIndicator(
                    modifier = Modifier.testTag(PROPERTY_DETAIL_PROGRESS)
                )
            }
        } else if (state.screenState == ScreenStateType.SUCCESS) {
            PropertyDetailSuccess(
                state = state
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

    Box(
        contentAlignment = Alignment.TopStart
    ) {
        IconButton(
            onClick = { onCloseClicked() }) {
            Icon(
                modifier = Modifier
                    .drawableTestTag(
                        tag = PROPERTY_DETAIL_CLOSE,
                        id = R.drawable.clear_24
                    )
                    .background(color = MaterialTheme.colorScheme.background, shape = CircleShape),
                painter = painterResource(id = R.drawable.clear_24),
                contentDescription = stringResource(id = R.string.close)
            )
        }
    }
}

@Composable
fun PropertyDetailSuccess(
    state: PropertyDetailScreenState,
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        ListingDetailImagesComponent(
            media = state.propertyDetail?.media ?: emptyList()
        )
        AgencyBannerComponent(
            agencyColour = state.propertyDetail?.advertiser?.preferredColorHex,
            logo = state.propertyDetail?.advertiser?.logoUrl
        )
        state.propertyDetail?.price?.let {
            Text(
                modifier = Modifier
                    .padding(
                        start = DIM_16,
                        end = DIM_16,
                        top = DIM_8
                    )
                    .testTag(PROPERTY_DETAIL_PRICE),
                text = it,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
        state.propertyDetail?.address?.let {
            Text(
                modifier = Modifier
                    .padding(
                        start = DIM_16,
                        end = DIM_16,
                        top = DIM_8,
                        bottom = DIM_8
                    )
                    .testTag(PROPERTY_DETAIL_ADDRESS),
                text = it,
                style = MaterialTheme.typography.labelLarge
            )
        }
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
                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                collapsedMaxLine = 3
            )
        }

        HorizontalDivider(
            modifier = Modifier.padding(top = DIM_8, bottom = DIM_8)
        )

        state.propertyDetail?.advertiser?.let { advertiser ->
            AgencyComponent(advertiser = advertiser)
        }
    }
}

object PropertyDetailScreenTestTags {
    private const val PREFIX = "PROPERTY_DETAIL_"
    const val PROPERTY_DETAILS_LAYOUT = "${PREFIX}LAYOUT"
    const val PROPERTY_DETAIL_PROGRESS = "${PREFIX}PROGRESS"
    const val PROPERTY_DETAIL_PRICE = "${PREFIX}PRICE"
    const val PROPERTY_DETAIL_ADDRESS = "${PREFIX}ADDRESS"
    const val PROPERTY_DETAIL_HEADLINE = "${PREFIX}HEADLINE"
    const val PROPERTY_DETAIL_DESCRIPTION = "${PREFIX}DESCRIPTION"
    const val PROPERTY_DETAIL_CLOSE = "${PREFIX}CLOSE"
}

@Preview
@Composable
fun PropertyDetailScreenContentPreview() {
    MviExampleTheme {
        PropertyDetailScreenContent(
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
            state = PropertyDetailScreenState(
                screenState = ScreenStateType.LOADING
            )
        )
    }
}