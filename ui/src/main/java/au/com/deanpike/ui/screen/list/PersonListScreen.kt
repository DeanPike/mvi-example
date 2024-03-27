package au.com.deanpike.ui.screen.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import au.com.deanpike.client.model.PersonDTO
import au.com.deanpike.ui.R
import au.com.deanpike.ui.screen.list.PersonListScreenTestTags.PERSON_LIST
import au.com.deanpike.ui.screen.list.PersonListScreenTestTags.PERSON_LIST_FAB
import au.com.deanpike.ui.screen.list.component.PersonListItem
import au.com.deanpike.uishared.base.ScreenStateType
import au.com.deanpike.uishared.theme.Dimension.DIM_16
import au.com.deanpike.uishared.theme.Dimension.DIM_4
import au.com.deanpike.uishared.theme.MviExampleTheme
import java.util.UUID

@Composable
fun PersonListScreen(
    viewModel: PersonListViewModel = viewModel()
) {

    LaunchedEffect(Unit) {
        viewModel.setEvent(PersonListScreenEvent.Initialise)

    }
    PersonListScreenContent(
        state = viewModel.uiState
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonListScreenContent(
    state: PersonListScreenState,
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
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.testTag(PERSON_LIST_FAB),
                onClick = { onFabClicked() }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
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
                    .testTag(PERSON_LIST),
                verticalArrangement = Arrangement.spacedBy(DIM_16),
            ) {
                state.people.forEachIndexed { index, person ->
                    item(key = person.id) {
                        PersonListItem(
                            position = index,
                            person = person,
                            onItemClicked = onItemClicked
                        )
                        Spacer(modifier = Modifier.height(DIM_4))
                    }
                }
            }
        }
    }
}

object PersonListScreenTestTags {
    private const val PREFIX = "PERSON_LIST_SCREEN_"
    const val PERSON_LIST = "${PREFIX}_LIST"
    const val PERSON_LIST_FAB = "${PREFIX}_FAB"
}

@Composable
@Preview
fun PersonListScreenContentPreview() {
    MviExampleTheme {
        PersonListScreenContent(
            state = PersonListScreenState(
                screenState = ScreenStateType.SUCCESS,
                people = listOf(
                    PersonDTO(
                        id = UUID.randomUUID(),
                        name = "Name One",
                        surname = "Surname One",
                        age = 11
                    ),
                    PersonDTO(
                        id = UUID.randomUUID(),
                        name = "An unusually long first name 1234567890 1234567890 1234567890",
                        surname = "An unusually long second name 1234567890 1234567890 1234567890",
                        age = 22
                    )
                )
            )
        )
    }
}