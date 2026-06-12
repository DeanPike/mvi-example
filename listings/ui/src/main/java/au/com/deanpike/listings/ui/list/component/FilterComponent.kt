package au.com.deanpike.listings.ui.list.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import au.com.deanpike.listings.client.type.DwellingType
import au.com.deanpike.listings.client.type.StatusType
import au.com.deanpike.listings.ui.R
import au.com.deanpike.listings.ui.list.component.FilterComponentTestTags.FILTER_COMPONENT_DWELLING_TYPE_LABEL
import au.com.deanpike.listings.ui.list.component.FilterComponentTestTags.FILTER_COMPONENT_DWELLING_TYPE_TEXT
import au.com.deanpike.listings.ui.list.component.FilterComponentTestTags.FILTER_COMPONENT_FILTER_BUTTON
import au.com.deanpike.listings.ui.list.component.FilterComponentTestTags.FILTER_COMPONENT_FILTER_IMAGE
import au.com.deanpike.listings.ui.list.component.FilterComponentTestTags.FILTER_COMPONENT_LAYOUT
import au.com.deanpike.listings.ui.list.component.FilterComponentTestTags.FILTER_COMPONENT_STATUS_LABEL
import au.com.deanpike.listings.ui.list.component.FilterComponentTestTags.FILTER_COMPONENT_STATUS_TEXT
import au.com.deanpike.listings.ui.util.StringUtils.getPropertyTypeDescription
import au.com.deanpike.listings.ui.util.StringUtils.getStatusDescription
import au.com.deanpike.uishared.base.drawableTestTag
import au.com.deanpike.uishared.theme.AppTheme
import au.com.deanpike.uishared.theme.Dimension.DIM_16
import au.com.deanpike.uishared.theme.Dimension.DIM_4
import au.com.deanpike.uishared.util.ThemePreviews

@Composable
fun FilterComponent(
    selectedStatus: StatusType,
    selectedDwellingTypes: List<DwellingType>,
    onEvent: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = DIM_16)
            .testTag(FILTER_COMPONENT_LAYOUT),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .padding(end = DIM_4)
                .testTag(FILTER_COMPONENT_STATUS_LABEL),
            text = stringResource(id = R.string.status),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            modifier = Modifier
                .padding(end = DIM_16)
                .testTag(FILTER_COMPONENT_STATUS_TEXT),
            text = getStatusDescription(selectedStatus),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            modifier = Modifier
                .padding(end = DIM_4)
                .testTag(FILTER_COMPONENT_DWELLING_TYPE_LABEL),
            text = stringResource(id = R.string.property_type),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            modifier = Modifier.testTag(FILTER_COMPONENT_DWELLING_TYPE_TEXT),
            text = if (selectedDwellingTypes.size == 1) {
                getPropertyTypeDescription(selectedDwellingTypes.first())
            } else {
                stringResource(id = R.string.multiple_property_types)
            },
            style = MaterialTheme.typography.bodyMedium,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.weight(1f))

        IconButton(
            modifier = Modifier
//                .clip(RoundedCornerShape(48.dp))
//                .background(color = Color.Red)
                .testTag(FILTER_COMPONENT_FILTER_BUTTON),
            colors = IconButtonDefaults.iconButtonColors().copy(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ),
            onClick = onEvent
        ) {
            Image(
                modifier = Modifier.drawableTestTag(
                    tag = FILTER_COMPONENT_FILTER_IMAGE,
                    id = R.drawable.outline_format_list_bulleted_24
                ),
                painter = painterResource(R.drawable.outline_format_list_bulleted_24),
                contentDescription = stringResource(id = R.string.filter),
                colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.secondary)
            )
        }
    }
}

object FilterComponentTestTags {
    private const val PREFIX = "FILTER_COMPONENT_"
    const val FILTER_COMPONENT_LAYOUT = "${PREFIX}LAYOUT"
    const val FILTER_COMPONENT_STATUS_LABEL = "${PREFIX}STATUS_LABEL"
    const val FILTER_COMPONENT_STATUS_TEXT = "${PREFIX}STATUS_TEXT"
    const val FILTER_COMPONENT_DWELLING_TYPE_LABEL = "${PREFIX}DWELLING_TYPE_LABEL"
    const val FILTER_COMPONENT_DWELLING_TYPE_TEXT = "${PREFIX}DWELLING_TYPE_TEXT"
    const val FILTER_COMPONENT_FILTER_BUTTON = "${PREFIX}FILTER_BUTTON"
    const val FILTER_COMPONENT_FILTER_IMAGE = "${PREFIX}FILTER_IMAGE"

}

@ThemePreviews
@Composable
fun FilterComponent1Preview() {
    AppTheme {
        FilterComponent(
            selectedStatus = StatusType.BUY,
            selectedDwellingTypes = listOf(DwellingType.APARTMENT_UNIT_FLAT),
            onEvent = {}
        )
    }
}

@ThemePreviews
@Composable
fun FilterComponent1MultipleListingTypesPreview() {
    AppTheme {
        FilterComponent(
            selectedStatus = StatusType.BUY,
            selectedDwellingTypes = listOf(
                DwellingType.APARTMENT_UNIT_FLAT,
                DwellingType.TOWNHOUSE
            ),
            onEvent = {}
        )
    }
}