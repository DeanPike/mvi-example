package au.com.deanpike.detail.ui.property

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import au.com.deanpike.detail.ui.property.PropertyDetailScreenTestTags.PROPERTY_DETAIL_PROGRESS
import au.com.deanpike.detail.ui.shared.DetailAppBarComponent
import au.com.deanpike.uishared.base.ScreenStateType
import au.com.deanpike.uishared.theme.MviExampleTheme

@Composable
fun PropertyDetailScreen(
    viewModel: PropertyDetailViewModel = hiltViewModel<PropertyDetailViewModel>(),
    propertyId: Int
) {
    LaunchedEffect(Unit) {
        viewModel.setEvent(PropertyDetailScreenEvent.Initialise(propertyId = propertyId))
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PropertyDetailScreenContent(state: PropertyDetailScreenState) {

    val pagerState = rememberPagerState(pageCount = { state.propertyDetail?.media?.count() ?: 0 })

    HorizontalPager(state = pagerState) { page ->
        // Our page content
        Text(
            text = "Page: $page",
            modifier = Modifier.fillMaxWidth()
        )
    }

    Column(
        modifier = Modifier.background(color = MaterialTheme.colorScheme.background)
    ) {
        DetailAppBarComponent(address = state.propertyDetail?.address ?: "")
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
        }
    }
}

object PropertyDetailScreenTestTags {
    private const val PREFIX = "PROPERTY_DETAIL_"
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