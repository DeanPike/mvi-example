package au.com.deanpike.listings.ui.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import au.com.deanpike.listings.client.model.listing.response.Project
import au.com.deanpike.listings.client.model.listing.response.Property
import au.com.deanpike.listings.ui.R
import au.com.deanpike.listings.ui.list.ListingListScreenTestTags.LISTING_LIST
import au.com.deanpike.listings.ui.list.ListingListScreenTestTags.LISTING_LIST_HEADING
import au.com.deanpike.listings.ui.list.ListingListScreenTestTags.LISTING_LIST_TITLE
import au.com.deanpike.listings.ui.list.component.FilterBottomSheet
import au.com.deanpike.listings.ui.list.component.FilterComponent
import au.com.deanpike.listings.ui.list.component.ProjectCard
import au.com.deanpike.listings.ui.list.component.PropertyCard
import au.com.deanpike.uishared.base.ScreenStateType
import au.com.deanpike.uishared.component.ErrorComponent
import au.com.deanpike.uishared.theme.AppTheme
import au.com.deanpike.uishared.theme.Dimension.DIM_16
import au.com.deanpike.uishared.theme.Dimension.DIM_4
import au.com.deanpike.uishared.theme.Dimension.DIM_8
import au.com.deanpike.uishared.util.SetStatusBarAppearance
import au.com.deanpike.uishared.util.ThemePreviews

@Composable
fun ListingListScreen(
    viewModel: ListingListViewModel = hiltViewModel(),
    onPropertyClicked: (Long, String) -> Unit = { _, _ -> },
    onProjectClicked: (Long, String) -> Unit = { _, _ -> }
) {
    LaunchedEffect(viewModel.uiState.screenState) {
        if (viewModel.uiState.screenState == ScreenStateType.INITIAL) {
            viewModel.setEvent(
                ListingListScreenEvent.Initialise
            )
        }
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .padding(top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding())
    ) {
        TopBar(
            screenState = state.screenState,
            listingCount = state.listings.count()
        )
        AnimatedVisibility(
            modifier = Modifier.fillMaxSize(),
            visible = state.screenState != ScreenStateType.ERROR && state.screenState != ScreenStateType.INITIAL
        ) {
            SuccessContent(
                state = state,
                onEvent = onEvent
            )
        }
        AnimatedVisibility(
            modifier = Modifier.fillMaxSize(),
            visible = state.screenState == ScreenStateType.ERROR
        ) {
            ErrorContent(onEvent)
        }
    }

    AnimatedVisibility(
        modifier = Modifier.fillMaxSize(),
        visible = state.screenState == ScreenStateType.LOADING || state.screenState == ScreenStateType.REFRESHING
    ) {
        LoadingContent()
    }
}

@Composable
private fun TopBar(
    screenState: ScreenStateType,
    listingCount: Int
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = DIM_16)
            .padding(bottom = DIM_4)
    ) {
        Text(
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .testTag(LISTING_LIST_TITLE),
            text = stringResource(id = R.string.list_heading),
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            modifier = Modifier
                .padding(top = DIM_8)
                .testTag(LISTING_LIST_HEADING),
            text = when (screenState) {
                ScreenStateType.LOADING -> {
                    stringResource(id = R.string.loading)
                }

                ScreenStateType.SUCCESS -> {
                    pluralStringResource(id = R.plurals.project_properties, listingCount, listingCount)
                }

                else -> {
                    ""
                }
            },
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SuccessContent(
    state: ListingListScreenState,
    onEvent: (ListingListScreenEvent) -> Unit = {}
) {
    var showFilters by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        HorizontalDivider()
        FilterComponent(
            selectedStatus = state.selectedStatus,
            selectedDwellingTypes = state.selectedDwellingTypes,
            onEvent = {
                showFilters = true
            }
        )
        HorizontalDivider()
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp)
                .testTag(LISTING_LIST),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item(key = "header") {
                Spacer(modifier = Modifier.height(4.dp))
            }
            state.listings.forEachIndexed { _, listing ->
                if (listing is Property) {
                    item(key = listing.id) {
                        PropertyCard(
                            property = listing,
                            onEvent = onEvent
                        )
                    }
                } else if (listing is Project) {
                    item(key = listing.id) {
                        ProjectCard(
                            project = listing,
                            onEvent = onEvent
                        )
                    }
                }
            }
            item(key = "footer") {
                Spacer(modifier = Modifier.height(WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()))
            }
        }
    }

    if (showFilters) {
        ModalBottomSheet(
            onDismissRequest = {
                showFilters = false
            }
        ) {
            FilterBottomSheet(
                statusType = state.selectedStatus,
                dwellingTypes = state.selectedDwellingTypes,
                onApply = { status, listingTypes ->
                    showFilters = false
                    onEvent(ListingListScreenEvent.OnFilterApplied(status, listingTypes))
                }
            )
        }
    }
}

@Composable
private fun ErrorContent(
    onEvent: (ListingListScreenEvent) -> Unit = {}
) {
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

@Composable
private fun LoadingContent() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background.copy(alpha = 0.2F)),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}

object ListingListScreenTestTags {
    private const val PREFIX = "LISTING_LIST_SCREEN_"
    const val LISTING_LIST_TITLE = "${PREFIX}TITLE"
    const val LISTING_LIST_HEADING = "${PREFIX}HEADING"
    const val LISTING_LIST = "${PREFIX}LIST"
}

@ThemePreviews
@Composable
fun ListingListScreen1ContentSuccessPreview() {
    AppTheme {
        ListingListScreenContent(
            state = ListingListScreenState(
                screenState = ScreenStateType.SUCCESS,
                listings = emptyList(),
            )
        )
    }
}

@ThemePreviews
@Composable
fun ListingListScreen1ContentLoadingPreview() {
    AppTheme {
        ListingListScreenContent(
            state = ListingListScreenState(
                screenState = ScreenStateType.LOADING,
                listings = emptyList(),
            )
        )
    }
}

@ThemePreviews
@Composable
fun TopBarPreview() {
    AppTheme {
        TopBar(
            screenState = ScreenStateType.SUCCESS,
            listingCount = 10
        )
    }
}