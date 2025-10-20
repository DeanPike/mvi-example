package au.com.deanpike.uishared.component

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import au.com.deanpike.commonshared.model.Media
import au.com.deanpike.commonshared.type.MediaType
import au.com.deanpike.uishared.R
import au.com.deanpike.uishared.base.ScreenStateType
import au.com.deanpike.uishared.component.ListingDetailImagesTestTags.LISTING_DETAIL_IMAGES_IMAGE
import au.com.deanpike.uishared.component.ListingDetailImagesTestTags.LISTING_DETAIL_IMAGES_PAGER
import au.com.deanpike.uishared.component.ListingDetailImagesTestTags.LISTING_DETAIL_IMAGES_POSITION_INDICATOR
import au.com.deanpike.uishared.theme.Dimension.DIM_16
import au.com.deanpike.uishared.theme.MviExampleTheme
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kotlin.math.absoluteValue
import kotlinx.coroutines.CoroutineScope

@Composable
fun ListingDetailImagesComponent(media: List<Media>) {
    if (media.isNotEmpty()) {
        val pagerState = rememberPagerState(
            initialPage = 0,
            pageCount = { media.count() }
        )
        Box {
            HorizontalPager(
                modifier = Modifier
                    .align(Alignment.Center)
                    .testTag(LISTING_DETAIL_IMAGES_PAGER),
                state = pagerState,
                beyondViewportPageCount = 2
            ) { page ->
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .testTag("${LISTING_DETAIL_IMAGES_IMAGE}_${page}"),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(media[page].url)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(id = R.drawable.gallery_placeholder),
                    fallback = painterResource(id = R.drawable.gallery_placeholder),
                    error = painterResource(id = R.drawable.gallery_placeholder),
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
                    .align(Alignment.BottomCenter)
                    .testTag(LISTING_DETAIL_IMAGES_POSITION_INDICATOR),
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

@Composable
fun ListingImagesComponent(
    screenState: ScreenStateType,
    scope: CoroutineScope,
    media: List<Media>
) {
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { media.count() })

    LaunchedEffect(key1 = screenState) {
        if (screenState == ScreenStateType.LOADING || screenState == ScreenStateType.REFRESHING) {
            pagerState.scrollToPage(0)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    ) {
        HorizontalPager(
            modifier = Modifier
                .align(Alignment.Center)
                .testTag(LISTING_DETAIL_IMAGES_PAGER),
            state = pagerState,
            pageSize = PageSize.Fill,
            pageSpacing = 4.dp,
            beyondViewportPageCount = 2,
            snapPosition = SnapPosition.Center,
            flingBehavior = PagerDefaults.flingBehavior(state = pagerState)
        ) { page ->
            AsyncImage(
                modifier = Modifier
                    .graphicsLayer {
                        // Calculate the absolute offset for the current page from the
                        // scroll position. We use the absolute value which allows us to mirror
                        // any effects for both directions
                        val pageOffset = (
                            (pagerState.currentPage - page) + pagerState
                                .currentPageOffsetFraction
                            ).absoluteValue

                        // We animate the alpha, between 50% and 100%
                        alpha = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                    }
                    .testTag("${LISTING_DETAIL_IMAGES_IMAGE}_${page}"),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(media[page].url)
                    .crossfade(true)
                    .error(R.drawable.gallery_placeholder)
                    .placeholder(R.drawable.gallery_placeholder)
                    .build(),
                placeholder = painterResource(id = R.drawable.gallery_placeholder),
                fallback = painterResource(id = R.drawable.gallery_placeholder),
                error = painterResource(id = R.drawable.gallery_placeholder),
                contentScale = ContentScale.Crop,
                contentDescription = stringResource(id = R.string.property_image),
                alignment = Alignment.Center
            )
        }

        AnimatedPagerIndicator(
            modifier = Modifier
                .align(alignment = Alignment.BottomCenter)
                .padding(DIM_16),
            pagerState = pagerState,
            pageCount = pagerState.pageCount
        )
    }
}

object ListingDetailImagesTestTags {
    private const val PREFIX = "LISTING_DETAIL_IMAGES_"
    const val LISTING_DETAIL_IMAGES_PAGER = "${PREFIX}PAGER"
    const val LISTING_DETAIL_IMAGES_IMAGE = "${PREFIX}IMAGE"
    const val LISTING_DETAIL_IMAGES_POSITION_INDICATOR = "${PREFIX}POSITION_INDICATOR"
}

@Preview
@Composable
fun ListingDetailImagesComponentPreview() {
    MviExampleTheme {
        ListingDetailImagesComponent(
            media = listOf(
                Media(
                    mediaType = MediaType.PHOTO,
                    url = "https://bucket-api.domain.com.au/v1/bucket/image/2019096805_1_1_240305_054335-w2048-h1365"
                ),
                Media(
                    mediaType = MediaType.PHOTO,
                    url = "https://bucket-api.domain.com.au/v1/bucket/image/2019096805_2_1_240305_054335-w2048-h1365"
                )
            )
        )
    }
}