package au.com.deanpike.listings.ui.list.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import au.com.deanpike.commonshared.model.ListingDetails
import au.com.deanpike.datashared.type.ListingType
import au.com.deanpike.listings.client.model.listing.response.ProjectChild
import au.com.deanpike.listings.ui.list.component.ProjectChildListItemComponentTestTags.PROJECT_CHILD_LIST_ITEM_LAYOUT
import au.com.deanpike.listings.ui.list.component.ProjectChildListItemComponentTestTags.PROJECT_CHILD_LIST_ITEM_LIFECYCLE
import au.com.deanpike.listings.ui.list.component.ProjectChildListItemComponentTestTags.PROJECT_CHILD_LIST_ITEM_PRICE
import au.com.deanpike.uishared.component.BedBathCarComponent
import au.com.deanpike.uishared.theme.Dimension.DIM_16
import au.com.deanpike.uishared.theme.Dimension.DIM_4
import au.com.deanpike.uishared.theme.Dimension.DIM_8
import au.com.deanpike.uishared.theme.MviExampleTheme

@Composable
fun ProjectChildListItemComponent(
    projectChild: ProjectChild,
    onProjectChildClicked: (Long) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .wrapContentHeight()
            .padding(top = DIM_4, bottom = DIM_4)
            .testTag("${PROJECT_CHILD_LIST_ITEM_LAYOUT}_${projectChild.id}"),
        colors = CardDefaults.cardColors().copy(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        border = BorderStroke(width = 0.5.dp, color = MaterialTheme.colorScheme.outlineVariant),
        onClick = {
            onProjectChildClicked(projectChild.id)
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = DIM_16, end = DIM_16, top = DIM_4, bottom = DIM_4),
            verticalAlignment = Alignment.Top
        ) {
            projectChild.listingDetails.price?.let {
                Text(
                    modifier = Modifier
                        .weight(0.8F)
                        .testTag("${PROJECT_CHILD_LIST_ITEM_PRICE}_${projectChild.id}"),
                    text = it,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            projectChild.lifecycleStatus?.let {
                Text(
                    modifier = Modifier
                        .weight(0.2F)
                        .testTag("${PROJECT_CHILD_LIST_ITEM_LIFECYCLE}_${projectChild.id}"),
                    text = it,
                    style = MaterialTheme.typography.titleSmall,
                    textAlign = TextAlign.End
                )
            }
        }

        BedBathCarComponent(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = DIM_16, end = DIM_16, bottom = DIM_8),
            bedrooms = projectChild.listingDetails.numberOfBedrooms,
            bathrooms = projectChild.listingDetails.numberOfBathrooms,
            carSpaces = projectChild.listingDetails.numberOfCarSpaces,
        )
    }
}

object ProjectChildListItemComponentTestTags {
    private const val PREFIX = "PROJECT_CHILD_LIST_ITEM_"
    const val PROJECT_CHILD_LIST_ITEM_LAYOUT = "${PREFIX}LAYOUT"
    const val PROJECT_CHILD_LIST_ITEM_PRICE = "${PREFIX}PRICE"
    const val PROJECT_CHILD_LIST_ITEM_LIFECYCLE = "${PREFIX}LIFECYCLE"
}

@Preview(showBackground = true)
@Composable
fun ProjectChildListItemComponentShortPreview() {
    MviExampleTheme {
        ProjectChildListItemComponent(
            projectChild = ProjectChild(
                listingType = ListingType.PROPERTY,
                id = 2222,
                lifecycleStatus = "New Home",
                listingDetails = ListingDetails(
                    price = "Contact Agent",
                    numberOfBedrooms = 4,
                    numberOfBathrooms = 3,
                    numberOfCarSpaces = 2
                )
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProjectChildListItemComponentLongPreview() {
    MviExampleTheme {
        ProjectChildListItemComponent(
            projectChild = ProjectChild(
                listingType = ListingType.PROPERTY,
                id = 2222,
                lifecycleStatus = "New Home",
                listingDetails = ListingDetails(
                    price = "Whole Floor North-Facing Residence With Harbour Views",
                    numberOfBedrooms = 4,
                    numberOfBathrooms = 3,
                    numberOfCarSpaces = 2
                )
            )
        )
    }
}