package au.com.deanpike.detail.ui.property

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import au.com.deanpike.detail.client.model.detail.Media
import au.com.deanpike.detail.ui.R
import au.com.deanpike.detail.ui.property.PropertyDetailScreenTestTags.PROPERTY_DETAILS_LAYOUT
import au.com.deanpike.detail.ui.property.PropertyDetailScreenTestTags.PROPERTY_DETAIL_PROGRESS
import au.com.deanpike.detail.ui.shared.DetailAppBarComponent
import au.com.deanpike.uishared.base.ScreenStateType
import au.com.deanpike.uishared.theme.Dimension
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
            address = state.propertyDetail?.address ?: "",
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
            PropertyDetailImages(
                media = state.propertyDetail?.media ?: emptyList()
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PropertyDetailImages(media: List<Media>) {
    if (media.isNotEmpty()) {
        val pagerState = rememberPagerState(pageCount = { media.count() })
        Box {
            HorizontalPager(
                modifier = Modifier.align(Alignment.Center),
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
                    placeholder = painterResource(id = R.drawable.gallery_placeholder),
                    contentScale = ContentScale.Crop,
                    contentDescription = stringResource(id = R.string.property_image),
                    alignment = Alignment.Center
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(bottom = Dimension.DIM_16)
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
        }
    }
}

object PropertyDetailScreenTestTags {
    private const val PREFIX = "PROPERTY_DETAIL_"
    const val PROPERTY_DETAILS_LAYOUT = "${PREFIX}LAYOUT"
    const val PROPERTY_DETAIL_PROGRESS = "${PREFIX}PROGRESS"
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

@Preview
@Composable
fun PropertyDetailScreenContentPreview() {
    MviExampleTheme {
        PropertyDetailScreenContent(
            state = PropertyDetailScreenState(
                screenState = ScreenStateType.SUCCESS
            )
        )
    }
}