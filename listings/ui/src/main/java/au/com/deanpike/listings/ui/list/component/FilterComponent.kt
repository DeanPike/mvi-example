package au.com.deanpike.listings.ui.list.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import au.com.deanpike.listings.client.type.DwellingType
import au.com.deanpike.listings.client.type.StatusType
import au.com.deanpike.listings.ui.R
import au.com.deanpike.listings.ui.list.ListingListScreenEvent
import au.com.deanpike.listings.ui.list.component.FilterComponentTestTags.FILTER_COMPONENT_LAYOUT
import au.com.deanpike.listings.ui.list.component.FilterComponentTestTags.LISTING_TYPE
import au.com.deanpike.listings.ui.list.component.FilterComponentTestTags.STATUS_BUTTON
import au.com.deanpike.listings.ui.list.component.FilterComponentTestTags.STATUS_ITEM
import au.com.deanpike.listings.ui.list.component.FilterComponentTestTags.STATUS_ITEM_MENU
import au.com.deanpike.listings.ui.util.StringUtils.getStatusDescription
import au.com.deanpike.uishared.base.drawableTestTag
import au.com.deanpike.uishared.theme.Dimension.DIM_8
import au.com.deanpike.uishared.theme.MviExampleTheme

@Composable
fun FilterComponent(
    modifier: Modifier = Modifier,
    selectedStatus: StatusType,
    selectedListingTypes: List<DwellingType>,
    onEvent: (ListingListScreenEvent) -> Unit = {},
    onListingTypeSelected: () -> Unit
) {
    var statusExpanded by remember {
        mutableStateOf(false)
    }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .testTag(FILTER_COMPONENT_LAYOUT),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Row {
            OutlinedButton(
                modifier = Modifier.testTag(STATUS_BUTTON),
                onClick = { statusExpanded = true }
            ) {
                Text(
                    modifier = Modifier.testTag("${STATUS_BUTTON}_TEXT"),
                    text = getStatusDescription(selectedStatus),
                    style = MaterialTheme.typography.bodyMedium
                )
                Icon(
                    modifier = Modifier.drawableTestTag(
                        tag = "${STATUS_BUTTON}_ICON",
                        id = R.drawable.arrow_drop_down
                    ),
                    painter = painterResource(id = R.drawable.arrow_drop_down),
                    contentDescription = stringResource(id = R.string.status_description)
                )
            }
            DropdownMenu(
                modifier = Modifier.testTag(STATUS_ITEM_MENU),
                expanded = statusExpanded,
                onDismissRequest = {
                    statusExpanded = false
                }
            ) {
                StatusType.entries.forEach {
                    DropdownMenuItem(
                        modifier = Modifier
                            .testTag("${STATUS_ITEM}_${it.name}"),
                        text = {
                            Text(
                                modifier = Modifier.testTag("${STATUS_ITEM}_${it.name}_TEXT"),
                                text = getStatusDescription(it),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        },
                        onClick = {
                            onEvent(ListingListScreenEvent.OnStatusSelected(it))
                            statusExpanded = false
                        }
                    )
                }
            }
        }

        OutlinedButton(
            modifier = Modifier.testTag(LISTING_TYPE),
            onClick = {
                onListingTypeSelected()
            }
        ) {
            Text(
                modifier = Modifier
                    .padding(end = DIM_8)
                    .testTag("${LISTING_TYPE}_TEXT"),
                text = if (selectedListingTypes.isEmpty()) stringResource(id = R.string.no_listing_type) else pluralStringResource(
                    id = R.plurals.listing_types,
                    count = selectedListingTypes.count(),
                    selectedListingTypes.count()
                ),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

object FilterComponentTestTags {
    private const val PREFIX = "FILTER_COMPONENT_"
    const val FILTER_COMPONENT_LAYOUT = "${PREFIX}LAYOUT"
    const val STATUS_BUTTON = "${PREFIX}STATUS_BUTTON"
    const val STATUS_ITEM_MENU = "${PREFIX}STATUS_ITEM_MENU"
    const val STATUS_ITEM = "${PREFIX}STATUS_ITEM"
    const val LISTING_TYPE = "${PREFIX}LISTING_TYPE"
}

@Composable
@Preview(showBackground = true)
fun FilterComponentPreview() {
    MviExampleTheme {
        FilterComponent(
            selectedStatus = StatusType.BUY,
            selectedListingTypes = listOf(DwellingType.HOUSE, DwellingType.TOWNHOUSE, DwellingType.APARTMENT_UNIT_FLAT),
            onListingTypeSelected = {}
        )
    }
}