package au.com.deanpike.uishared.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import au.com.deanpike.uishared.R
import au.com.deanpike.uishared.component.DetailListItemTestTags.DETAIL_ITEM_BATHROOMS
import au.com.deanpike.uishared.component.DetailListItemTestTags.DETAIL_ITEM_BEDROOMS
import au.com.deanpike.uishared.component.DetailListItemTestTags.DETAIL_ITEM_CAR_SPACES
import au.com.deanpike.uishared.component.DetailListItemTestTags.DETAIL_ITEM_GROUP
import au.com.deanpike.uishared.theme.Dimension
import au.com.deanpike.uishared.theme.MviExampleTheme

@Composable
fun BedBathCarComponent(
    modifier: Modifier = Modifier,
    bedrooms: Int? = null,
    bathrooms: Int? = null,
    carSpaces: Int? = null,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.testTag(DETAIL_ITEM_GROUP),
            verticalAlignment = Alignment.CenterVertically
        ) {
            bedrooms?.let {
                DetailItemComponent(
                    icon = R.drawable.baseline_bed_24,
                    text = "$it",
                    description = R.string.number_of_bedrooms,
                    testTag = DETAIL_ITEM_BEDROOMS
                )
            }
            Spacer(modifier = Modifier.width(Dimension.DIM_24))
            bathrooms?.let {
                DetailItemComponent(
                    icon = R.drawable.baseline_bathtub_24,
                    text = "$it",
                    description = R.string.number_of_bathrooms,
                    testTag = DETAIL_ITEM_BATHROOMS
                )
            }
            Spacer(modifier = Modifier.width(Dimension.DIM_24))
            carSpaces?.let {
                DetailItemComponent(
                    icon = R.drawable.baseline_directions_car_24,
                    text = "$it",
                    description = R.string.number_of_parking_spaces,
                    testTag = DETAIL_ITEM_CAR_SPACES
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
}

@Preview(showBackground = true)
@Composable
fun DetailListItemComponentPreview() {
    MviExampleTheme {
        BedBathCarComponent(
            bedrooms = 4,
            bathrooms = 3,
            carSpaces = 2
        )
    }
}