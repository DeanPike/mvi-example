package au.com.deanpike.listings.ui.list.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import au.com.deanpike.listings.client.type.DwellingType
import au.com.deanpike.uishared.theme.Dimension.DIM_12

@Composable
fun ListingTypeItem(
    description: String,
    isSelected: Boolean,
    onValueChanged: (Boolean) -> Unit = {},
    testTag: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .toggleable(
                value = isSelected,
                role = Role.Checkbox,
                onValueChange = onValueChanged
            )
            // 12dp above and below the 24dp checkbox gives the Material 3 48dp minimum touch target.
            .padding(vertical = DIM_12)
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
            // The whole row handles the toggle, so the label is tappable too.
            onCheckedChange = null
        )

    }
}

fun updateDwellingTypes(
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
