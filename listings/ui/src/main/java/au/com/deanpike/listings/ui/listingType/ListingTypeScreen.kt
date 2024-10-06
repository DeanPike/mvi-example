package au.com.deanpike.listings.ui.listingType

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import au.com.deanpike.listings.client.type.DwellingType
import au.com.deanpike.listings.ui.R
import au.com.deanpike.listings.ui.listingType.ListingTypeScreenTestTags.LISTING_TYPE_SCREEN_ALL
import au.com.deanpike.listings.ui.listingType.ListingTypeScreenTestTags.LISTING_TYPE_SCREEN_APARTMENT
import au.com.deanpike.listings.ui.listingType.ListingTypeScreenTestTags.LISTING_TYPE_SCREEN_APPLY
import au.com.deanpike.listings.ui.listingType.ListingTypeScreenTestTags.LISTING_TYPE_SCREEN_HOUSE
import au.com.deanpike.listings.ui.listingType.ListingTypeScreenTestTags.LISTING_TYPE_SCREEN_LAYOUT
import au.com.deanpike.listings.ui.listingType.ListingTypeScreenTestTags.LISTING_TYPE_SCREEN_TOWNHOUSE
import au.com.deanpike.uishared.theme.Dimension.DIM_16
import au.com.deanpike.uishared.theme.MviExampleTheme
import kotlinx.coroutines.launch

@Composable
fun ListingTypeScreen(
    viewModel: ListingTypeViewModel = viewModel(),
    selectedListingTypes: List<DwellingType>,
    onApplyClicked: (List<DwellingType>) -> Unit = {}
) {
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.setEvent(ListingTypeEvent.Initialise(selectedListingTypes))
    }

    DisposableEffect(key1 = viewModel.effect) {
        val job = scope.launch {
            viewModel.effect.collect { effect ->
                when (effect) {
                    is ListingTypeEffect.OnApplyClicked -> {
                        onApplyClicked(effect.listingTypes)
                    }
                }
            }
        }
        onDispose {
            job.cancel()
        }
    }

    ListingTypeScreenContent(
        state = viewModel.uiState,
        onItemSelected = { listingType, selected ->
            viewModel.setEvent(
                ListingTypeEvent.OnItemSelected(
                    listingType = listingType,
                    selected = selected
                )
            )
        },
        onApplyClicked = {
            viewModel.setEvent(ListingTypeEvent.OnApplyClicked)
        }
    )
}

@Composable
fun ListingTypeScreenContent(
    state: ListingTypeState,
    onItemSelected: (DwellingType, Boolean) -> Unit = { _, _ -> },
    onApplyClicked: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(DIM_16)
            .testTag(LISTING_TYPE_SCREEN_LAYOUT)
    ) {
        ListingTypeItem(
            description = stringResource(id = R.string.all),
            isSelected = state.selectedListingTypes.isEmpty(),
            onValueChanged = { onItemSelected(DwellingType.ALL, it) },
            testTag = LISTING_TYPE_SCREEN_ALL
        )
        ListingTypeItem(
            description = stringResource(id = R.string.house),
            isSelected = state.selectedListingTypes.contains(DwellingType.HOUSE),
            onValueChanged = { onItemSelected(DwellingType.HOUSE, it) },
            testTag = LISTING_TYPE_SCREEN_HOUSE
        )
        ListingTypeItem(
            description = stringResource(id = R.string.townhouse),
            isSelected = state.selectedListingTypes.contains(DwellingType.TOWNHOUSE),
            onValueChanged = { onItemSelected(DwellingType.TOWNHOUSE, it) },
            testTag = LISTING_TYPE_SCREEN_TOWNHOUSE
        )
        ListingTypeItem(
            description = stringResource(id = R.string.apartment_unit_flat),
            isSelected = state.selectedListingTypes.contains(DwellingType.APARTMENT_UNIT_FLAT),
            onValueChanged = { onItemSelected(DwellingType.APARTMENT_UNIT_FLAT, it) },
            testTag = LISTING_TYPE_SCREEN_APARTMENT
        )

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(DIM_16)
                .testTag(LISTING_TYPE_SCREEN_APPLY),
            onClick = { onApplyClicked() }
        ) {
            Text(
                text = stringResource(id = R.string.apply),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun ListingTypeItem(
    description: String,
    isSelected: Boolean,
    onValueChanged: (Boolean) -> Unit = {},
    testTag: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier.testTag("${testTag}_TEXT"),
            text = description,
            style = MaterialTheme.typography.bodyMedium
        )
        Checkbox(
            modifier = Modifier.testTag("${testTag}_CHECKBOX"),
            checked = isSelected,
            onCheckedChange = {
                onValueChanged(it)
            }
        )

    }
}

object ListingTypeScreenTestTags {
    private const val PREFIX = "LISTING_TYPE_SCREEN_"
    const val LISTING_TYPE_SCREEN_LAYOUT = "${PREFIX}LAYOUT"
    const val LISTING_TYPE_SCREEN_ALL = "${PREFIX}ALL"
    const val LISTING_TYPE_SCREEN_HOUSE = "${PREFIX}HOUSE"
    const val LISTING_TYPE_SCREEN_TOWNHOUSE = "${PREFIX}TOWNHOUSE"
    const val LISTING_TYPE_SCREEN_APARTMENT = "${PREFIX}APARTMENT"
    const val LISTING_TYPE_SCREEN_APPLY = "${PREFIX}APPLY"
}

@Preview(showBackground = true)
@Composable
fun ListingTypeScreenPreview() {
    MviExampleTheme {
        ListingTypeScreenContent(
            state = ListingTypeState()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ListingTypeItemPreview() {
    MviExampleTheme {
        ListingTypeItem(
            description = "Description",
            isSelected = true,
            testTag = "TEST_TAG"
        )
    }
}