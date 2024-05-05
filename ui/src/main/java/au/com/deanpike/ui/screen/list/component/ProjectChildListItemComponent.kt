package au.com.deanpike.ui.screen.list.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import au.com.deanpike.client.model.listing.response.ListingDetails
import au.com.deanpike.ui.screen.list.component.ProjectChildListItemComponentTestTags.PROJECT_CHILD_LIST_ITEM_LAYOUT
import au.com.deanpike.ui.screen.list.component.ProjectChildListItemComponentTestTags.PROJECT_CHILD_LIST_ITEM_PRICE
import au.com.deanpike.ui.screen.shared.PropertyDetailComponent
import au.com.deanpike.uishared.theme.Dimension.DIM_16
import au.com.deanpike.uishared.theme.Dimension.DIM_4
import au.com.deanpike.uishared.theme.Dimension.DIM_8
import au.com.deanpike.uishared.theme.MviExampleTheme

@Composable
fun ProjectChildListItemComponent(
    parentPosition: Int,
    position: Int,
    id: Long,
    listingDetails: ListingDetails,
    onProjectChildClicked: (Long) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .wrapContentHeight()
            .padding(top = DIM_4, bottom = DIM_4)
            .testTag("${PROJECT_CHILD_LIST_ITEM_LAYOUT}_${parentPosition}_${position}"),
        colors = CardDefaults.cardColors().copy(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outline),
        onClick = {
            onProjectChildClicked(id)
        }
    ) {
        Column(
            modifier = Modifier.padding(DIM_8),
        ) {
            listingDetails.price?.let {
                Text(
                    modifier = Modifier
                        .padding(start = DIM_16, end = DIM_16, top = DIM_8)
                        .testTag("${PROJECT_CHILD_LIST_ITEM_PRICE}_${parentPosition}_${position}"),
                    text = it,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            PropertyDetailComponent(
                parentPosition = parentPosition,
                position = position,
                details = listingDetails,
                dwellingType = null
            )
        }
    }
}

object ProjectChildListItemComponentTestTags {
    private const val PREFIX = "PROJECT_CHILD_LIST_ITEM_"
    const val PROJECT_CHILD_LIST_ITEM_LAYOUT = "${PREFIX}LAYOUT"
    const val PROJECT_CHILD_LIST_ITEM_PRICE = "${PREFIX}PRICE"
}

@Preview(showBackground = true)
@Composable
fun ProjectChildListItemComponentPreview() {
    MviExampleTheme {
        ProjectChildListItemComponent(
            parentPosition = 0,
            position = 1,
            id = 2222,
            listingDetails = ListingDetails(
                price = "Contact Agent",
                numberOfBedrooms = 4,
                numberOfBathrooms = 3,
                numberOfCarSpaces = 2
            )
        )
    }
}