package au.com.deanpike.ui.screen.list

import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import au.com.deanpike.client.model.listing.response.Project
import au.com.deanpike.client.model.listing.response.Property
import au.com.deanpike.client.type.StatusType
import au.com.deanpike.ui.R
import au.com.deanpike.ui.screen.list.ListingListScreenTestTags.LISTING_LIST
import au.com.deanpike.ui.screen.list.ListingListScreenTestTags.LISTING_LIST_HEADING
import au.com.deanpike.ui.screen.list.component.FilterComponent
import au.com.deanpike.ui.screen.list.component.ProjectListItem
import au.com.deanpike.ui.screen.list.component.PropertyListItem
import au.com.deanpike.uishared.base.ScreenStateType
import au.com.deanpike.uishared.theme.Dimension.DIM_16
import au.com.deanpike.uishared.theme.Dimension.DIM_8
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
        state = viewModel.uiState,
        onStatusSelected = {
            viewModel.setEvent(ListingListScreenEvent.OnStatusSelected(it))
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListingListScreenContent(
    state: ListingListScreenState,
    onItemClicked: (UUID) -> Unit = {},
    onStatusSelected: (StatusType) -> Unit = {}
) {

    val layoutDirection = LocalLayoutDirection.current
    Scaffold(
        topBar = {
            if (state.screenState == ScreenStateType.SUCCESS) {
                TopAppBar(
                    title = {
                        Text(
                            modifier = Modifier.testTag(LISTING_LIST_HEADING),
                            text = pluralStringResource(id = R.plurals.project_properties, state.listings.count(), state.listings.count())
                        )
                    }
                )
            }
        },
    ) { innerPadding ->
        if (state.screenState == ScreenStateType.SUCCESS) {
            Column(
                modifier = Modifier.padding(
                    start = innerPadding.calculateStartPadding(layoutDirection),
                    end = innerPadding.calculateEndPadding(layoutDirection),
                    top = innerPadding.calculateTopPadding(),
                    bottom = innerPadding.calculateBottomPadding()
                )
            ) {
                HorizontalDivider()
                FilterComponent(
                    modifier = Modifier.padding(
                        top = DIM_8,
                        start = DIM_16,
                        end = DIM_16
                    ),
                    selectedStatus = state.selectedStatus,
                    selectedListingTypes = state.selectedListingTypes,
                    onStatusSelected = onStatusSelected,
                    onListingTypeSelected = {}
                )
                Spacer(modifier = Modifier.height(DIM_8))
                LazyColumn(
                    modifier = Modifier
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