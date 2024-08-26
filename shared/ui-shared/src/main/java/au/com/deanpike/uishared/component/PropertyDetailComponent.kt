package au.com.deanpike.uishared.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import au.com.deanpike.commonshared.model.ListingDetails
import au.com.deanpike.uishared.R
import au.com.deanpike.uishared.component.DetailListItemTestTags.DETAIL_ITEM_BATHROOMS
import au.com.deanpike.uishared.component.DetailListItemTestTags.DETAIL_ITEM_BEDROOMS
import au.com.deanpike.uishared.component.DetailListItemTestTags.DETAIL_ITEM_CAR_SPACES
import au.com.deanpike.uishared.component.DetailListItemTestTags.DETAIL_ITEM_DWELLING_TYPE
import au.com.deanpike.uishared.component.DetailListItemTestTags.DETAIL_ITEM_GROUP
import au.com.deanpike.uishared.theme.Dimension
import au.com.deanpike.uishared.theme.Dimension.DIM_8
import au.com.deanpike.uishared.theme.MviExampleTheme

@Composable
fun PropertyDetailComponent(
    modifier: Modifier = Modifier,
    parentPosition: Int,
    position: Int,
    details: ListingDetails,
    dwellingType: String?
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .testTag("${DETAIL_ITEM_GROUP}_${parentPosition}_${position}"),
            verticalAlignment = Alignment.CenterVertically
        ) {
            details.numberOfBedrooms?.let {
                DetailItemComponent(
                    icon = R.drawable.bed_outline,
                    text = "$it",
                    description = R.string.number_of_bedrooms,
                    testTag = DETAIL_ITEM_BEDROOMS,
                    parentPosition = parentPosition,
                    position = position
                )
            }
            Spacer(modifier = Modifier.width(Dimension.DIM_16))
            details.numberOfBathrooms?.let {
                DetailItemComponent(
                    icon = R.drawable.bath_outline,
                    text = "$it",
                    description = R.string.number_of_bathrooms,
                    testTag = DETAIL_ITEM_BATHROOMS,
                    parentPosition = parentPosition,
                    position = position
                )
            }
            Spacer(modifier = Modifier.width(Dimension.DIM_16))
            details.numberOfCarSpaces?.let {
                DetailItemComponent(
                    icon = R.drawable.car_outline,
                    text = "$it",
                    description = R.string.number_of_parking_spaces,
                    testTag = DETAIL_ITEM_CAR_SPACES,
                    parentPosition = parentPosition,
                    position = position
                )
            }
        }
        Row {
            dwellingType?.let {
                Text(
                    modifier = Modifier
                        .padding(top = DIM_8)
                        .testTag("${DETAIL_ITEM_DWELLING_TYPE}_${parentPosition}_${position}"),
                    text = it,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

        }
    }
}

object DetailListItemTestTags {
    private const val PREFIX = "DETAIL_ITEM_"
    const val DETAIL_ITEM_GROUP = "${PREFIX}GROUP"
    const val DETAIL_ITEM_BEDROOMS = "${PREFIX}BEDROOMS"
    const val DETAIL_ITEM_BATHROOMS = "${PREFIX}BATHROOMS"
    const val DETAIL_ITEM_CAR_SPACES = "${PREFIX}CAR_SPACES"
    const val DETAIL_ITEM_DWELLING_TYPE = "${PREFIX}DWELLING_TYPE"
}

@Preview(showBackground = true)
@Composable
fun DetailListItemComponentPreview() {
    MviExampleTheme {
        PropertyDetailComponent(
            parentPosition = 0,
            position = 1,
            details = ListingDetails(
                price = "$1,000,500",
                numberOfBedrooms = 4,
                numberOfBathrooms = 3,
                numberOfCarSpaces = 2
            ),
            dwellingType = "House"
        )
    }
}