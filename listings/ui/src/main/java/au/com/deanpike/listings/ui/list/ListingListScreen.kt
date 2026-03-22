package au.com.deanpike.listings.ui.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
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
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import au.com.deanpike.listings.client.model.listing.response.Project
import au.com.deanpike.listings.client.model.listing.response.Property
import au.com.deanpike.listings.ui.R
import au.com.deanpike.listings.ui.list.ListingListScreenTestTags.LISTING_LIST
import au.com.deanpike.listings.ui.list.ListingListScreenTestTags.LISTING_LIST_HEADING
import au.com.deanpike.listings.ui.list.ListingListScreenTestTags.LISTING_LIST_TITLE
import au.com.deanpike.listings.ui.list.component.FilterComponent
import au.com.deanpike.listings.ui.list.component.ProjectListItem
import au.com.deanpike.listings.ui.list.component.PropertyListItem
import au.com.deanpike.listings.ui.listingType.ListingTypeScreen
import au.com.deanpike.uishared.base.ScreenStateType
import au.com.deanpike.uishared.component.ErrorComponent
import au.com.deanpike.uishared.theme.Dimension.DIM_16
import au.com.deanpike.uishared.theme.Dimension.DIM_8
import au.com.deanpike.uishared.theme.MviExampleTheme
import au.com.deanpike.uishared.util.GridOverlayPreviewComponent
import au.com.deanpike.uishared.util.SetStatusBarAppearance

@Composable
fun ListingListScreen(
    viewModel: ListingListViewModel = hiltViewModel(),
    onPropertyClicked: (Long, String) -> Unit = { _, _ -> },
    onProjectClicked: (Long, String) -> Unit = { _, _ -> }
) {
    LaunchedEffect(Unit) {
        viewModel.setEvent(
            ListingListScreenEvent.Initialise
        )
    }

    SetStatusBarAppearance(useDarkIcons = !isSystemInDarkTheme())

    ListingListScreenContent(
        state = viewModel.uiState,
        onEvent = {
            when (it) {
                is ListingListScreenEvent.OnPropertySelected -> {
                    onPropertyClicked(it.id, it.address)
                }
                is ListingListScreenEvent.OnProjectSelected -> {
                    onProjectClicked(it.id, it.address)
                }
                else -> {
                    viewModel.setEvent(it)
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListingListScreenContent(
    state: ListingListScreenState,
    onEvent: (ListingListScreenEvent) -> Unit = {}
) {

    val layoutDirection = LocalLayoutDirection.current

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = Color.Transparent,
        topBar = {
            TopAppBar(
                title = {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            modifier = Modifier
                                .align(alignment = Alignment.CenterHorizontally)
                                .testTag(LISTING_LIST_TITLE),
                            text = stringResource(id = R.string.list_heading)
                        )
                        Text(
                            modifier = Modifier
                                .padding(top = DIM_8)
                                .testTag(LISTING_LIST_HEADING),
                            text = when (state.screenState) {
                                ScreenStateType.LOADING -> {
                                    stringResource(id = R.string.loading)
                                }
                                ScreenStateType.SUCCESS -> {
                                    pluralStringResource(id = R.plurals.project_properties, state.listings.count(), state.listings.count())
                                }
                                else -> {
                                    ""
                                }
                            },
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            )

        }
    ) { innerPadding ->
        AnimatedVisibility(state.screenState != ScreenStateType.ERROR && state.screenState != ScreenStateType.INITIAL) {
            Column(
                modifier = Modifier
                    .padding(
                        top = innerPadding.calculateTopPadding(),
                        bottom = 0.dp,
                        start = innerPadding.calculateStartPadding(layoutDirection),
                        end = innerPadding.calculateEndPadding(layoutDirection)
                    )
            ) {
                HorizontalDivider()
                FilterComponent(
                    modifier = Modifier.padding(
                        top = DIM_8,
                        start = DIM_16,
                        end = DIM_16,
                        bottom = DIM_8
                    ),
                    selectedStatus = state.selectedStatus,
                    selectedListingTypes = state.selectedListingTypes,
                    onEvent = onEvent
                )
                HorizontalDivider()
                LazyColumn(
                    modifier = Modifier.testTag(LISTING_LIST),
                ) {
                    state.listings.forEachIndexed { _, listing ->
                        if (listing is Property) {
                            item(key = listing.id) {
                                PropertyListItem(
                                    property = listing,
                                    onEvent = onEvent
                                )
                            }
                        } else if (listing is Project) {
                            item(key = listing.id) {
                                ProjectListItem(
                                    project = listing,
                                    onEvent = onEvent
                                )
                            }
                        }
                    }
                }
            }

            if (state.showListingTypeScreen) {
                ModalBottomSheet(
                    onDismissRequest = {
                        onEvent(ListingListScreenEvent.OnBottomSheetDismissed)
                    }
                ) {
                    ListingTypeScreen(
                        selectedListingTypes = state.selectedListingTypes,
                        onEvent = onEvent
                    )
                }
            }
        }
        AnimatedVisibility(state.screenState == ScreenStateType.ERROR) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                ErrorComponent(
                    onRetryClicked = {
                        onEvent(ListingListScreenEvent.OnRetryClicked)
                    }
                )
            }
        }

        AnimatedVisibility(state.screenState == ScreenStateType.LOADING || state.screenState == ScreenStateType.REFRESHING) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.background.copy(alpha = 0.2F)),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator(
                )
            }
        }
    }
}

object ListingListScreenTestTags {
    private const val PREFIX = "LISTING_LIST_SCREEN_"
    const val LISTING_LIST_TITLE = "${PREFIX}TITLE"
    const val LISTING_LIST_HEADING = "${PREFIX}HEADING"
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

@Composable
@Preview
fun ListingListScreenContentErrorPreview() {
    MviExampleTheme {
        GridOverlayPreviewComponent {
            ListingListScreenContent(
                state = ListingListScreenState(
                    screenState = ScreenStateType.ERROR,
                    listings = emptyList(),
                )
            )
        }
    }
}