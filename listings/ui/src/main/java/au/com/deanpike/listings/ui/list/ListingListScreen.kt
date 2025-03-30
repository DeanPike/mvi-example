package au.com.deanpike.listings.ui.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import au.com.deanpike.listings.client.model.listing.response.Project
import au.com.deanpike.listings.client.model.listing.response.Property
import au.com.deanpike.listings.client.type.DwellingType
import au.com.deanpike.listings.client.type.StatusType
import au.com.deanpike.listings.ui.R
import au.com.deanpike.listings.ui.list.ListingListScreenTestTags.LISTING_LIST
import au.com.deanpike.listings.ui.list.ListingListScreenTestTags.LISTING_LIST_HEADING
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
import kotlinx.coroutines.launch

@Composable
fun ListingListScreen(
    viewModel: ListingListViewModel = hiltViewModel<ListingListViewModel>(),
    isSinglePane: Boolean = true,
    refreshStatusBar: Boolean = false,
    onPropertyClicked: (Long, String) -> Unit = { _, _ -> },
    onProjectClicked: (Long, String) -> Unit = { _, _ -> },
    onProjectChildClicked: (Long, Long, String) -> Unit = { _, _, _ -> },
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val scope = rememberCoroutineScope()

    val refresh by rememberUpdatedState(refreshStatusBar)

    if (refresh) {
        SetStatusBarAppearance(useDarkIcons = true)
    }

    DisposableEffect(lifecycleOwner) {
        val job = scope.launch {
            viewModel.effect.collect { effect ->
                when (effect) {
                    is ListingListScreenEffect.OnPropertySelected -> {
                        onPropertyClicked(effect.id, effect.address)
                    }

                    is ListingListScreenEffect.OnProjectSelected -> {
                        onProjectClicked(effect.id, effect.address)
                    }
                    is ListingListScreenEffect.OnProjectChildSelected -> {
                        onProjectChildClicked(effect.projectId, effect.projectChildId, effect.address)
                    }
                }
            }
        }
        onDispose { job.cancel() }
    }
    LaunchedEffect(isSinglePane) {
        viewModel.setEvent(
            ListingListScreenEvent.Initialise(
                isSinglePane = isSinglePane
            )
        )
    }
    ListingListScreenContent(
        state = viewModel.uiState,
        onStatusSelected = {
            viewModel.setEvent(ListingListScreenEvent.OnStatusSelected(it))
        },
        onListingTypeSelected = {
            viewModel.setEvent(ListingListScreenEvent.OnListingTypeClicked)
        },
        onBottomSheetDismissed = {
            viewModel.setEvent(ListingListScreenEvent.OnBottomSheetDismissed)
        },
        onListingTypesApplied = {
            viewModel.setEvent(ListingListScreenEvent.OnListingTypesApplied(it))
        },
        onRetryClicked = {
            viewModel.setEvent(ListingListScreenEvent.OnRetryClicked)
        },
        onPropertyClicked = { id, address ->
            viewModel.setEvent(ListingListScreenEvent.OnPropertySelected(id, address))
        },
        onProjectClicked = { id, address ->
            viewModel.setEvent(ListingListScreenEvent.OnProjectSelected(id, address))
        },
        onProjectChildClicked = { projectId, projectChildId, address ->
            viewModel.setEvent(
                ListingListScreenEvent.OnProjectChildSelected(
                    projectId = projectId,
                    projectChildId = projectChildId,
                    address = address
                )
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListingListScreenContent(
    state: ListingListScreenState,
    onStatusSelected: (StatusType) -> Unit = {},
    onListingTypeSelected: () -> Unit = {},
    onBottomSheetDismissed: () -> Unit = {},
    onListingTypesApplied: (List<DwellingType>) -> Unit = {},
    onRetryClicked: () -> Unit = {},
    onPropertyClicked: (Long, String) -> Unit = { _, _ -> },
    onProjectClicked: (Long, String) -> Unit = { _, _ -> },
    onProjectChildClicked: (Long, Long, String) -> Unit = { _, _, _ -> }
) {

    val layoutDirection = LocalLayoutDirection.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier.testTag(LISTING_LIST_HEADING),
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
                        }
                    )
                }
            )

        }
    ) { padding ->
        if (state.screenState == ScreenStateType.SUCCESS) {
            Column(
                modifier = Modifier.padding(
                    start = padding.calculateStartPadding(layoutDirection),
                    end = padding.calculateEndPadding(layoutDirection),
                    top = padding.calculateTopPadding(),
                    bottom = padding.calculateBottomPadding()
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
                    onStatusSelected = onStatusSelected,
                    onListingTypeSelected = onListingTypeSelected
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
                                    onItemClicked = {
                                        onPropertyClicked(
                                            listing.id,
                                            listing.address
                                        )
                                    }
                                )
                            }
                        } else if (listing is Project) {
                            item(key = listing.id) {
                                ProjectListItem(
                                    project = listing,
                                    onProjectClicked = {
                                        onProjectClicked(it, listing.address)
                                    },
                                    onProjectChildClicked = {
                                        onProjectChildClicked(listing.id, it, listing.address)
                                    }
                                )
                            }
                        }
                    }
                }
            }

            if (state.showListingTypeScreen) {
                ModalBottomSheet(
                    onDismissRequest = { onBottomSheetDismissed() }
                ) {
                    ListingTypeScreen(
                        selectedListingTypes = state.selectedListingTypes,
                        onApplyClicked = onListingTypesApplied
                    )
                }
            }
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
        } else if (state.screenState == ScreenStateType.LOADING || state.screenState == ScreenStateType.REFRESHING) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                )
            }
        } else {
            // We need to have a composable or the scaffold will crash the app
            Text(text = "")
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