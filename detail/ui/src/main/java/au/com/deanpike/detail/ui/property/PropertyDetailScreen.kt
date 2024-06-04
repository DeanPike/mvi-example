package au.com.deanpike.detail.ui.property

import au.com.deanpike.uishared.R as RShared
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import au.com.deanpike.datashared.type.ListingType
import au.com.deanpike.detail.client.model.detail.Advertiser
import au.com.deanpike.detail.client.model.detail.Agent
import au.com.deanpike.detail.client.model.detail.Media
import au.com.deanpike.detail.client.model.detail.PhoneNumber
import au.com.deanpike.detail.client.model.detail.PropertyDetail
import au.com.deanpike.detail.client.model.type.MediaType
import au.com.deanpike.detail.client.model.type.PhoneNumberType
import au.com.deanpike.detail.ui.R
import au.com.deanpike.detail.ui.property.PropertyDetailScreenTestTags.PROPERTY_DETAILS_LAYOUT
import au.com.deanpike.detail.ui.property.PropertyDetailScreenTestTags.PROPERTY_DETAIL_ADDRESS
import au.com.deanpike.detail.ui.property.PropertyDetailScreenTestTags.PROPERTY_DETAIL_DESCRIPTION
import au.com.deanpike.detail.ui.property.PropertyDetailScreenTestTags.PROPERTY_DETAIL_HEADLINE
import au.com.deanpike.detail.ui.property.PropertyDetailScreenTestTags.PROPERTY_DETAIL_IMAGE_PAGER
import au.com.deanpike.detail.ui.property.PropertyDetailScreenTestTags.PROPERTY_DETAIL_PRICE
import au.com.deanpike.detail.ui.property.PropertyDetailScreenTestTags.PROPERTY_DETAIL_PROGRESS
import au.com.deanpike.detail.ui.shared.DetailAppBarComponent
import au.com.deanpike.uishared.base.ScreenStateType
import au.com.deanpike.uishared.component.AgentBanner
import au.com.deanpike.uishared.component.ExpandableText
import au.com.deanpike.uishared.component.LifecycleStatus
import au.com.deanpike.uishared.theme.Dimension.DIM_16
import au.com.deanpike.uishared.theme.Dimension.DIM_8
import au.com.deanpike.uishared.theme.MviExampleTheme
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun PropertyDetailScreen(
    viewModel: PropertyDetailViewModel = hiltViewModel<PropertyDetailViewModel>(),
    propertyId: Int,
    onCloseClicked: () -> Unit = {}
) {
    LaunchedEffect(Unit) {
        viewModel.setEvent(PropertyDetailScreenEvent.Initialise(propertyId = propertyId))
    }

    PropertyDetailScreenContent(
        state = viewModel.uiState,
        onCloseClicked = onCloseClicked
    )
}

@Composable
fun PropertyDetailScreenContent(
    state: PropertyDetailScreenState,
    onCloseClicked: () -> Unit = {}
) {

    BackHandler {
        onCloseClicked()
    }

    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .testTag(PROPERTY_DETAILS_LAYOUT)
    ) {
        DetailAppBarComponent(
            onCloseClicked = onCloseClicked
        )
        HorizontalDivider()
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
            ProjectDetailSuccess(
                state = state
            )
        }
    }
}

@Composable
fun ProjectDetailSuccess(
    state: PropertyDetailScreenState,
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        AgentBanner(
            agencyColour = state.propertyDetail?.advertiser?.preferredColorHex,
            logo = state.propertyDetail?.advertiser?.logoUrl
        )
        PropertyDetailImages(
            media = state.propertyDetail?.media ?: emptyList(),
            lifecycleStatus = state.propertyDetail?.lifecycleStatus
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
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }
        state.propertyDetail?.address?.let {
            Text(
                modifier = Modifier
                    .padding(
                        start = DIM_16,
                        end = DIM_16,
                        top = DIM_8
                    )
                    .testTag(PROPERTY_DETAIL_ADDRESS),
                text = it,
                style = MaterialTheme.typography.labelLarge
            )
        }
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
                    .padding(start = DIM_16, end = DIM_16, top = DIM_8)
                    .testTag(PROPERTY_DETAIL_DESCRIPTION),
                text = it,
                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                collapsedMaxLine = 3
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PropertyDetailImages(
    media: List<Media>,
    lifecycleStatus: String?
) {
    if (media.isNotEmpty()) {
        val pagerState = rememberPagerState(pageCount = { media.count() })
        Box {
            HorizontalPager(
                modifier = Modifier
                    .align(Alignment.Center)
                    .testTag(PROPERTY_DETAIL_IMAGE_PAGER),
                state = pagerState
            ) { page ->
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(media[page].url)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(id = RShared.drawable.gallery_placeholder),
                    contentScale = ContentScale.Crop,
                    contentDescription = stringResource(id = R.string.property_image),
                    alignment = Alignment.Center
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(bottom = DIM_16)
                    .align(Alignment.BottomCenter),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(pagerState.pageCount) { iteration ->
                    val color = if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray.copy(alpha = 0.5F)
                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .clip(CircleShape)
                            .background(color)
                            .size(16.dp)
                    )
                }
            }

            LifecycleStatus(
                lifecycleStatus = lifecycleStatus
            )
        }
    }
}

object PropertyDetailScreenTestTags {
    private const val PREFIX = "PROPERTY_DETAIL_"
    const val PROPERTY_DETAILS_LAYOUT = "${PREFIX}LAYOUT"
    const val PROPERTY_DETAIL_PROGRESS = "${PREFIX}PROGRESS"
    const val PROPERTY_DETAIL_IMAGE_PAGER = "${PREFIX}IMAGE_PAGER"
    const val PROPERTY_DETAIL_PRICE = "${PREFIX}PRICE"
    const val PROPERTY_DETAIL_ADDRESS = "${PREFIX}ADDRESS"
    const val PROPERTY_DETAIL_HEADLINE = "${PREFIX}HEADLINE"
    const val PROPERTY_DETAIL_DESCRIPTION = "${PREFIX}DESCRIPTION"
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