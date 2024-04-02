package au.com.deanpike.ui.screen.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import au.com.deanpike.ui.R
import au.com.deanpike.ui.screen.list.ListingListScreenTestTags.LISTING_LIST
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
    onFabClicked: () -> Unit = {},
    onItemClicked: (UUID) -> Unit = {}
) {

    val layoutDirection = LocalLayoutDirection.current
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    text = stringResource(id = R.string.mvi_example)
                )
            })
        },
    ) { innerPadding ->
        if (state.screenState != ScreenStateType.INITIAL) {
            LazyColumn(
                modifier = Modifier
                    .padding(
                        start = innerPadding.calculateStartPadding(layoutDirection) + DIM_16,
                        end = innerPadding.calculateEndPadding(layoutDirection) + DIM_16,
                        top = innerPadding.calculateTopPadding(),
                        bottom = innerPadding.calculateBottomPadding()
                    )
                    .testTag(LISTING_LIST),
                verticalArrangement = Arrangement.spacedBy(DIM_16),
            ) {
//                state.people.forEachIndexed { index, person ->
//                    item(key = person.id) {
//                        PersonListItem(
//                            position = index,
//                            person = person,
//                            onItemClicked = onItemClicked
//                        )
//                        Spacer(modifier = Modifier.height(DIM_4))
//                    }
//                }
            }
        }
    }
}

object ListingListScreenTestTags {
    private const val PREFIX = "PERSON_LIST_SCREEN_"
    const val LISTING_LIST = "${PREFIX}_LIST"
}

@Composable
@Preview
fun ListingListScreenContentPreview() {
    MviExampleTheme {
        ListingListScreenContent(
            state = ListingListScreenState(
                screenState = ScreenStateType.SUCCESS,
                listings = emptyList(),
            )
        )
    }
}