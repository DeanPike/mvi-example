package au.com.deanpike.ui.screen.list.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import au.com.deanpike.commonshared.model.ListingDetails
import au.com.deanpike.ui.screen.list.component.ProjectChildListItemComponentTestTags.PROJECT_CHILD_LIST_ITEM_LAYOUT
import au.com.deanpike.ui.screen.list.component.ProjectChildListItemComponentTestTags.PROJECT_CHILD_LIST_ITEM_LIFECYCLE
import au.com.deanpike.ui.screen.list.component.ProjectChildListItemComponentTestTags.PROJECT_CHILD_LIST_ITEM_PRICE
import au.com.deanpike.uishared.component.PropertyDetailComponent
import au.com.deanpike.uishared.theme.Dimension.DIM_16
import au.com.deanpike.uishared.theme.Dimension.DIM_4
import au.com.deanpike.uishared.theme.Dimension.DIM_8
import au.com.deanpike.uishared.theme.MviExampleTheme

@Composable
fun ProjectChildListItemComponent(
    id: Long,
    lifecycleStatus: String?,
    listingDetails: ListingDetails,
    onProjectChildClicked: (Long) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .wrapContentHeight()
            .padding(top = DIM_4, bottom = DIM_4)
            .testTag(PROJECT_CHILD_LIST_ITEM_LAYOUT),
        colors = CardDefaults.cardColors().copy(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outline),
        onClick = {
            onProjectChildClicked(id)
        }
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(DIM_8)
        ) {
            val (priceRef, lifecycleRef, detailRef) = createRefs()
            listingDetails.price?.let {
                Text(
                    modifier = Modifier
                        .constrainAs(priceRef) {
                            start.linkTo(parent.start)
                            end.linkTo(lifecycleRef.start)
                            top.linkTo(parent.top)
                            width = Dimension.fillToConstraints
                        }
                        .padding(bottom = DIM_16)
                        .testTag(PROJECT_CHILD_LIST_ITEM_PRICE),
                    text = it,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            lifecycleStatus?.let {
                Text(
                    modifier = Modifier
                        .constrainAs(lifecycleRef) {
                            start.linkTo(priceRef.end)
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)

                        }
                        .testTag(PROJECT_CHILD_LIST_ITEM_LIFECYCLE),
                    text = it,
                    style = MaterialTheme.typography.titleSmall
                )
            }
            PropertyDetailComponent(
                modifier = Modifier
                    .constrainAs(detailRef) {
                        start.linkTo(parent.start)
                        top.linkTo(priceRef.bottom)
                    }
                    .fillMaxWidth(),
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
    const val PROJECT_CHILD_LIST_ITEM_LIFECYCLE = "${PREFIX}LIFECYCLE"
}

@Preview(showBackground = true)
@Composable
fun ProjectChildListItemComponentShortPreview() {
    MviExampleTheme {
        ProjectChildListItemComponent(
            id = 2222,
            lifecycleStatus = "New",
            listingDetails = ListingDetails(
                price = "Contact Agent",
                numberOfBedrooms = 4,
                numberOfBathrooms = 3,
                numberOfCarSpaces = 2
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProjectChildListItemComponentLongPreview() {
    MviExampleTheme {
        ProjectChildListItemComponent(
            id = 2222,
            lifecycleStatus = "New",
            listingDetails = ListingDetails(
                price = "Whole Floor North-Facing Residence With Harbour Views",
                numberOfBedrooms = 4,
                numberOfBathrooms = 3,
                numberOfCarSpaces = 2
            )
        )
    }
}