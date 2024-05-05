package au.com.deanpike.ui.screen.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import au.com.deanpike.client.model.listing.response.Project
import au.com.deanpike.client.model.listing.response.Property
import au.com.deanpike.ui.R
import au.com.deanpike.ui.screen.list.ListingListScreenTestTags.LISTING_LIST
import au.com.deanpike.ui.screen.list.ListingListScreenTestTags.LISTING_LIST_HEADING
import au.com.deanpike.ui.screen.list.component.ProjectListItem
import au.com.deanpike.ui.screen.list.component.PropertyListItem
import au.com.deanpike.uishared.base.ScreenStateType
import au.com.deanpike.uishared.theme.Dimension.DIM_16
import au.com.deanpike.uishared.theme.MviExampleTheme
import java.util.UUID

@Composable
fun ListingListScreen(
    viewModel: ListingListViewModel = viewModel()
) {

    LaunchedEffect(Unit) {
        viewModel.setEvent(ListingListScreenEvent.Initialise)

    }
    ListingListScreenContent(
        state = viewModel.uiState
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListingListScreenContent(
    state: ListingListScreenState,
    onItemClicked: (UUID) -> Unit = {}
) {

    val layoutDirection = LocalLayoutDirection.current
    Scaffold(
        topBar = {
            if (state.screenState == ScreenStateType.SUCCESS) {
                TopAppBar(
                    title = {
                        Text(
                            modifier = Modifier.testTag(LISTING_LIST_HEADING),
                            text = stringResource(id = R.string.list_heading, state.listings.count())
                        )
                    }
                )
            }
        },
    ) { innerPadding ->
        if (state.screenState == ScreenStateType.SUCCESS) {
            Column {
                LazyColumn(
                    modifier = Modifier
                        .padding(
                            start = innerPadding.calculateStartPadding(layoutDirection),
                            end = innerPadding.calculateEndPadding(layoutDirection),
                            top = innerPadding.calculateTopPadding(),
                            bottom = innerPadding.calculateBottomPadding()
                        )
                        .testTag(LISTING_LIST),
                    verticalArrangement = Arrangement.spacedBy(DIM_16),
                ) {
                    state.listings.forEachIndexed { index, listing ->
                        if (listing is Property) {
                            item(key = listing.id) {
                                PropertyListItem(
                                    position = index,
                                    property = listing
                                )
                            }
                        } else if (listing is Project) {
                            item(key = listing.id) {
                                ProjectListItem(
                                    position = index,
                                    project = listing
                                )
                            }
                        }

                        item {
                            HorizontalDivider(
                                modifier = Modifier.fillMaxWidth(),
                                thickness = 1.dp,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }
        } else {
            if (state.screenState == ScreenStateType.LOADING) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                    )
                }
            }
        }
    }
}

object ListingListScreenTestTags {
    private const val PREFIX = "LISTING_LIST_SCREEN_"
    const val LISTING_LIST_HEADING = "${PREFIX}_HEADING"
    const val LISTING_LIST = "${PREFIX}LIST"
}

@Composable
@Preview
fun ListingListScreenContentSuccessPreview() {
    MviExampleTheme {
        ListingListScreenContent(
            state = ListingListScreenState(
                screenState = ScreenStateType.SUCCESS,
                listings = emptyList(),
            )
        )
    }
}

@Composable
@Preview
fun ListingListScreenContentLoadingPreview() {
    MviExampleTheme {
        ListingListScreenContent(
            state = ListingListScreenState(
                screenState = ScreenStateType.LOADING,
                listings = emptyList(),
            )
        )
    }
}