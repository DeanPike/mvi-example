package au.com.deanpike.ui.screen.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import au.com.deanpike.ui.R
import au.com.deanpike.uishared.base.ScreenStateType
import au.com.deanpike.uishared.theme.Dimension.DIM_16
import au.com.deanpike.uishared.theme.MviExampleTheme

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
    onFabClicked: () -> Unit = {}
) {
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
                onClick = { onFabClicked() }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { innerPadding ->
        if (state.screenState != ScreenStateType.INITIAL) {
            Column(
                modifier = Modifier
                    .padding(innerPadding),
                verticalArrangement = Arrangement.spacedBy(DIM_16),
            ) {

            }
        }
    }
}

@Composable
@Preview
fun PersonListScreenContentPreview() {
    MviExampleTheme {
        PersonListScreenContent(
            state = PersonListScreenState()
        )
    }
}