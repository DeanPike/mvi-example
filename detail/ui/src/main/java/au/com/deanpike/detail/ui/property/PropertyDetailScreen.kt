package au.com.deanpike.detail.ui.property

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

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

}