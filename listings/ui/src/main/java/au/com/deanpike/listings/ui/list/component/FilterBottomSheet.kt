package au.com.deanpike.listings.ui.list.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import au.com.deanpike.listings.client.type.DwellingType
import au.com.deanpike.listings.client.type.StatusType
import au.com.deanpike.listings.ui.R
import au.com.deanpike.listings.ui.list.component.FilterBottomSheetTestTags.FILTER_BOTTOM_SHEET_ALL_DWELLINGS
import au.com.deanpike.listings.ui.list.component.FilterBottomSheetTestTags.FILTER_BOTTOM_SHEET_APARTMENT
import au.com.deanpike.listings.ui.list.component.FilterBottomSheetTestTags.FILTER_BOTTOM_SHEET_APPLY
import au.com.deanpike.listings.ui.list.component.FilterBottomSheetTestTags.FILTER_BOTTOM_SHEET_BUY_BUTTON
import au.com.deanpike.listings.ui.list.component.FilterBottomSheetTestTags.FILTER_BOTTOM_SHEET_HOUSE
import au.com.deanpike.listings.ui.list.component.FilterBottomSheetTestTags.FILTER_BOTTOM_SHEET_LAYOUT
import au.com.deanpike.listings.ui.list.component.FilterBottomSheetTestTags.FILTER_BOTTOM_SHEET_RENT_BUTTON
import au.com.deanpike.listings.ui.list.component.FilterBottomSheetTestTags.FILTER_BOTTOM_SHEET_SOLD_BUTTON
import au.com.deanpike.listings.ui.list.component.FilterBottomSheetTestTags.FILTER_BOTTOM_SHEET_TOWNHOUSE
import au.com.deanpike.uishared.theme.AppTheme
import au.com.deanpike.uishared.theme.Dimension.DIM_16
import au.com.deanpike.uishared.util.ThemePreviews

@Composable
fun FilterBottomSheet(
    statusType: StatusType,
    dwellingTypes: List<DwellingType>,
    onApply: (StatusType, List<DwellingType>) -> Unit = { _, _ -> }
) {

    var selectedStatusType by remember {
        mutableStateOf(statusType)
    }
    var selectedDwellingTypes by remember {
        mutableStateOf(dwellingTypes)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(DIM_16)
            .testTag(FILTER_BOTTOM_SHEET_LAYOUT)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            OutlinedButton(
                modifier = Modifier.testTag(FILTER_BOTTOM_SHEET_BUY_BUTTON),
                border = if (selectedStatusType == StatusType.BUY) BorderStroke(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.onPrimary
                ) else ButtonDefaults.outlinedButtonBorder(true),
                colors = ButtonDefaults.outlinedButtonColors().copy(
                    containerColor = if (selectedStatusType == StatusType.BUY) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
                ),
                onClick = {
                    selectedStatusType = StatusType.BUY
                }
            ) {
                Text(
                    text = stringResource(R.string.buy),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
            OutlinedButton(
                modifier = Modifier.testTag(FILTER_BOTTOM_SHEET_RENT_BUTTON),
                border = if (selectedStatusType == StatusType.RENT) BorderStroke(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.onPrimary
                ) else ButtonDefaults.outlinedButtonBorder(true),
                colors = ButtonDefaults.outlinedButtonColors().copy(
                    containerColor = if (selectedStatusType == StatusType.RENT) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
                ),
                onClick = {
                    selectedStatusType = StatusType.RENT
                }
            ) {
                Text(
                    text = stringResource(R.string.rent),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
            OutlinedButton(
                modifier = Modifier.testTag(FILTER_BOTTOM_SHEET_SOLD_BUTTON),
                border = if (selectedStatusType == StatusType.SOLD) BorderStroke(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.onPrimary
                ) else ButtonDefaults.outlinedButtonBorder(true),
                colors = ButtonDefaults.outlinedButtonColors().copy(
                    containerColor = if (selectedStatusType == StatusType.SOLD) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
                ),
                onClick = {
                    selectedStatusType = StatusType.SOLD
                }
            ) {
                Text(
                    text = stringResource(R.string.sold),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

        HorizontalDivider(
            modifier = Modifier.padding(vertical = DIM_16)
        )

        ListingTypeItem(
            description = stringResource(id = R.string.all),
            isSelected = selectedDwellingTypes.contains(DwellingType.ALL),
            onValueChanged = {
                selectedDwellingTypes = updateDwellingTypes(
                    currentTypes = selectedDwellingTypes,
                    type = DwellingType.ALL,
                    isChecked = it
                )
            },
            testTag = FILTER_BOTTOM_SHEET_ALL_DWELLINGS
        )
        ListingTypeItem(
            description = stringResource(id = R.string.house),
            isSelected = selectedDwellingTypes.contains(DwellingType.HOUSE),
            onValueChanged = { isChecked ->
                selectedDwellingTypes = updateDwellingTypes(
                    currentTypes = selectedDwellingTypes,
                    type = DwellingType.HOUSE,
                    isChecked = isChecked
                )
            },
            testTag = FILTER_BOTTOM_SHEET_HOUSE
        )
        ListingTypeItem(
            description = stringResource(id = R.string.townhouse),
            isSelected = selectedDwellingTypes.contains(DwellingType.TOWNHOUSE),
            onValueChanged = { isChecked ->
                selectedDwellingTypes = updateDwellingTypes(
                    currentTypes = selectedDwellingTypes,
                    type = DwellingType.TOWNHOUSE,
                    isChecked = isChecked
                )
            },
            testTag = FILTER_BOTTOM_SHEET_TOWNHOUSE
        )
        ListingTypeItem(
            description = stringResource(id = R.string.apartment_unit_flat),
            isSelected = selectedDwellingTypes.contains(DwellingType.APARTMENT_UNIT_FLAT),
            onValueChanged = { isChecked ->
                selectedDwellingTypes = updateDwellingTypes(
                    currentTypes = selectedDwellingTypes,
                    type = DwellingType.APARTMENT_UNIT_FLAT,
                    isChecked = isChecked
                )
            },
            testTag = FILTER_BOTTOM_SHEET_APARTMENT
        )

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(DIM_16)
                .testTag(FILTER_BOTTOM_SHEET_APPLY),
            onClick = {
                onApply(
                    selectedStatusType,
                    selectedDwellingTypes
                )
            }
        ) {
            Text(
                modifier = Modifier.testTag("${FILTER_BOTTOM_SHEET_APPLY}_TEXT"),
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
        modifier = Modifier
            .fillMaxWidth()
            .testTag(testTag),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier.testTag("${testTag}_TEXT"),
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
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

private fun updateDwellingTypes(
    currentTypes: List<DwellingType>,
    type: DwellingType,
    isChecked: Boolean
): List<DwellingType> {
    var selectedTypes = if (isChecked) {
        currentTypes + type
    } else {
        currentTypes - type
    }

    if (selectedTypes.size >= 2 && type != DwellingType.ALL) {
        selectedTypes = selectedTypes.filterNot { it == DwellingType.ALL }
    } else if (type == DwellingType.ALL && isChecked) {
        selectedTypes = listOf(DwellingType.ALL)
    }

    return selectedTypes.ifEmpty {
        listOf(DwellingType.ALL)
    }
}

object FilterBottomSheetTestTags {
    private const val PREFIX = "FILTER_BOTTOM_SHEET_"
    const val FILTER_BOTTOM_SHEET_LAYOUT = "${PREFIX}LAYOUT"
    const val FILTER_BOTTOM_SHEET_BUY_BUTTON = "${PREFIX}BUY_BUTTON"
    const val FILTER_BOTTOM_SHEET_RENT_BUTTON = "${PREFIX}RENT_BUTTON"
    const val FILTER_BOTTOM_SHEET_SOLD_BUTTON = "${PREFIX}SOLD_BUTTON"
    const val FILTER_BOTTOM_SHEET_ALL_DWELLINGS = "${PREFIX}ALL_DWELLINGS"
    const val FILTER_BOTTOM_SHEET_HOUSE = "${PREFIX}HOUSE"
    const val FILTER_BOTTOM_SHEET_TOWNHOUSE = "${PREFIX}TOWNHOUSE"
    const val FILTER_BOTTOM_SHEET_APARTMENT = "${PREFIX}APARTMENT"
    const val FILTER_BOTTOM_SHEET_APPLY = "${PREFIX}APPLY"
}


@ThemePreviews
@Composable
fun FilterBottomSheetPreview() {
    AppTheme {
        FilterBottomSheet(
            statusType = StatusType.BUY,
            dwellingTypes = listOf(DwellingType.HOUSE, DwellingType.TOWNHOUSE)
        )
    }
}