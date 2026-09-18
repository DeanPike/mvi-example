package au.com.deanpike.listings.ui.search

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import au.com.deanpike.listings.client.model.suggestedlocation.Location
import au.com.deanpike.listings.client.type.DwellingType
import au.com.deanpike.listings.client.type.StatusType
import au.com.deanpike.listings.ui.R
import au.com.deanpike.listings.ui.list.component.ListingTypeItem
import au.com.deanpike.listings.ui.list.component.updateDwellingTypes
import au.com.deanpike.listings.ui.search.SearchScreenTestTags.SEARCH_SCREEN_ALL_DWELLINGS
import au.com.deanpike.listings.ui.search.SearchScreenTestTags.SEARCH_SCREEN_APARTMENT
import au.com.deanpike.listings.ui.search.SearchScreenTestTags.SEARCH_SCREEN_BUY_BUTTON
import au.com.deanpike.listings.ui.search.SearchScreenTestTags.SEARCH_SCREEN_HOUSE
import au.com.deanpike.listings.ui.search.SearchScreenTestTags.SEARCH_SCREEN_LOCATION_FIELD
import au.com.deanpike.listings.ui.search.SearchScreenTestTags.SEARCH_SCREEN_LOCATION_SUGGESTION
import au.com.deanpike.listings.ui.search.SearchScreenTestTags.SEARCH_SCREEN_RENT_BUTTON
import au.com.deanpike.listings.ui.search.SearchScreenTestTags.SEARCH_SCREEN_SEARCH_BUTTON
import au.com.deanpike.listings.ui.search.SearchScreenTestTags.SEARCH_SCREEN_SOLD_BUTTON
import au.com.deanpike.listings.ui.search.SearchScreenTestTags.SEARCH_SCREEN_TOWNHOUSE
import au.com.deanpike.uishared.theme.AppTheme
import au.com.deanpike.uishared.theme.Dimension.DIM_16
import au.com.deanpike.uishared.util.ThemePreviews

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    onSearch: (Location?, StatusType, List<DwellingType>) -> Unit = { _, _, _ -> }
) {
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is SearchScreenEffect.OnSearchRequested -> {
                    onSearch(effect.location, effect.status, effect.dwellingTypes)
                }
            }
        }
    }

    SearchScreenContent(
        state = viewModel.uiState,
        onEvent = viewModel::setEvent
    )
}

@Composable
fun SearchScreenContent(
    state: SearchScreenState,
    onEvent: (SearchScreenEvent) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = WindowInsets.statusBars.asPaddingValues(LocalDensity.current).calculateTopPadding())
            .padding(DIM_16)
    ) {
        val stateLocationText = state.selectedLocation?.displayName.orEmpty()
        var locationInput by remember { mutableStateOf(stateLocationText) }

        LaunchedEffect(stateLocationText) {
            if (stateLocationText != locationInput) {
                locationInput = stateLocationText
            }
        }

        LaunchedEffect(locationInput) {
            if (locationInput != stateLocationText) {
                onEvent(SearchScreenEvent.OnLocationChanged(locationInput))
            }
        }

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .testTag(SEARCH_SCREEN_LOCATION_FIELD),
        value = locationInput,
            onValueChange = {
                locationInput = it
            },
            label = {
                Text(text = stringResource(id = R.string.location))
            },
            singleLine = true
        )

        if (state.suggestedLocations.isNotEmpty()) {
            LocationSuggestions(
                suggestions = state.suggestedLocations,
                onSelected = {
                    onEvent(SearchScreenEvent.OnLocationSelected(it))
                }
            )
        }

        HorizontalDivider(
            modifier = Modifier.padding(vertical = DIM_16)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            StatusButton(
                text = stringResource(R.string.buy),
                isSelected = state.selectedStatus == StatusType.BUY,
                testTag = SEARCH_SCREEN_BUY_BUTTON,
                onClick = {
                    onEvent(SearchScreenEvent.OnStatusChanged(StatusType.BUY))
                }
            )
            StatusButton(
                text = stringResource(R.string.rent),
                isSelected = state.selectedStatus == StatusType.RENT,
                testTag = SEARCH_SCREEN_RENT_BUTTON,
                onClick = {
                    onEvent(SearchScreenEvent.OnStatusChanged(StatusType.RENT))
                }
            )
            StatusButton(
                text = stringResource(R.string.sold),
                isSelected = state.selectedStatus == StatusType.SOLD,
                testTag = SEARCH_SCREEN_SOLD_BUTTON,
                onClick = {
                    onEvent(SearchScreenEvent.OnStatusChanged(StatusType.SOLD))
                }
            )
        }

        HorizontalDivider(
            modifier = Modifier.padding(vertical = DIM_16)
        )

        ListingTypeItem(
            description = stringResource(id = R.string.all),
            isSelected = state.selectedDwellingTypes.contains(DwellingType.ALL),
            onValueChanged = {
                onEvent(
                    SearchScreenEvent.OnDwellingTypesChanged(
                        updateDwellingTypes(
                            currentTypes = state.selectedDwellingTypes,
                            type = DwellingType.ALL,
                            isChecked = it
                        )
                    )
                )
            },
            testTag = SEARCH_SCREEN_ALL_DWELLINGS
        )
        ListingTypeItem(
            description = stringResource(id = R.string.house),
            isSelected = state.selectedDwellingTypes.contains(DwellingType.HOUSE),
            onValueChanged = {
                onEvent(
                    SearchScreenEvent.OnDwellingTypesChanged(
                        updateDwellingTypes(
                            currentTypes = state.selectedDwellingTypes,
                            type = DwellingType.HOUSE,
                            isChecked = it
                        )
                    )
                )
            },
            testTag = SEARCH_SCREEN_HOUSE
        )
        ListingTypeItem(
            description = stringResource(id = R.string.townhouse),
            isSelected = state.selectedDwellingTypes.contains(DwellingType.TOWNHOUSE),
            onValueChanged = {
                onEvent(
                    SearchScreenEvent.OnDwellingTypesChanged(
                        updateDwellingTypes(
                            currentTypes = state.selectedDwellingTypes,
                            type = DwellingType.TOWNHOUSE,
                            isChecked = it
                        )
                    )
                )
            },
            testTag = SEARCH_SCREEN_TOWNHOUSE
        )
        ListingTypeItem(
            description = stringResource(id = R.string.apartment_unit_flat),
            isSelected = state.selectedDwellingTypes.contains(DwellingType.APARTMENT_UNIT_FLAT),
            onValueChanged = {
                onEvent(
                    SearchScreenEvent.OnDwellingTypesChanged(
                        updateDwellingTypes(
                            currentTypes = state.selectedDwellingTypes,
                            type = DwellingType.APARTMENT_UNIT_FLAT,
                            isChecked = it
                        )
                    )
                )
            },
            testTag = SEARCH_SCREEN_APARTMENT
        )

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = DIM_16)
                .testTag(SEARCH_SCREEN_SEARCH_BUTTON),
            onClick = {
                onEvent(SearchScreenEvent.OnSearchClicked)
            }
        ) {
            Text(
                text = stringResource(id = R.string.search),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun StatusButton(
    text: String,
    isSelected: Boolean,
    testTag: String,
    onClick: () -> Unit
) {
    OutlinedButton(
        modifier = Modifier.testTag(testTag),
        border = BorderStroke(
            width = if (isSelected) 2.dp else 1.dp,
            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
        ),
        colors = ButtonDefaults.outlinedButtonColors().copy(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceContainerLowest,
            contentColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
        ),
        onClick = onClick
    ) {
        Text(
            text = text,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Composable
private fun LocationSuggestions(
    suggestions: List<Location>,
    onSelected: (Location) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        LazyColumn {
            items(suggestions) { suggestion ->
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSelected(suggestion) }
                        .padding(DIM_16)
                        .testTag(SEARCH_SCREEN_LOCATION_SUGGESTION),
                    text = suggestion.displayName.orEmpty(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}

object SearchScreenTestTags {
    private const val PREFIX = "SEARCH_SCREEN_"
    const val SEARCH_SCREEN_LOCATION_FIELD = "${PREFIX}LOCATION_FIELD"
    const val SEARCH_SCREEN_LOCATION_SUGGESTION = "${PREFIX}LOCATION_SUGGESTION"
    const val SEARCH_SCREEN_BUY_BUTTON = "${PREFIX}BUY_BUTTON"
    const val SEARCH_SCREEN_RENT_BUTTON = "${PREFIX}RENT_BUTTON"
    const val SEARCH_SCREEN_SOLD_BUTTON = "${PREFIX}SOLD_BUTTON"
    const val SEARCH_SCREEN_ALL_DWELLINGS = "${PREFIX}ALL_DWELLINGS"
    const val SEARCH_SCREEN_HOUSE = "${PREFIX}HOUSE"
    const val SEARCH_SCREEN_TOWNHOUSE = "${PREFIX}TOWNHOUSE"
    const val SEARCH_SCREEN_APARTMENT = "${PREFIX}APARTMENT"
    const val SEARCH_SCREEN_SEARCH_BUTTON = "${PREFIX}SEARCH_BUTTON"
}

@ThemePreviews
@Composable
fun SearchScreenContentPreview() {
    AppTheme {
        SearchScreenContent(
            state = SearchScreenState()
        )
    }
}
