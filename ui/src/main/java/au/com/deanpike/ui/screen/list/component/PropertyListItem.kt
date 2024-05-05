package au.com.deanpike.ui.screen.list.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import au.com.deanpike.client.model.listing.response.ListingDetails
import au.com.deanpike.client.model.listing.response.ListingType
import au.com.deanpike.client.model.listing.response.Property
import au.com.deanpike.ui.R
import au.com.deanpike.ui.screen.list.component.PropertyListItemTesTags.PROPERTY_LIST_ITEM_ADDRESS
import au.com.deanpike.ui.screen.list.component.PropertyListItemTesTags.PROPERTY_LIST_ITEM_AGENCY_IMAGE
import au.com.deanpike.ui.screen.list.component.PropertyListItemTesTags.PROPERTY_LIST_ITEM_HEADLINE
import au.com.deanpike.ui.screen.list.component.PropertyListItemTesTags.PROPERTY_LIST_ITEM_LAYOUT
import au.com.deanpike.ui.screen.list.component.PropertyListItemTesTags.PROPERTY_LIST_ITEM_PRICE
import au.com.deanpike.ui.screen.list.component.PropertyListItemTesTags.PROPERTY_LIST_ITEM_PROPERTY_IMAGE
import au.com.deanpike.ui.screen.shared.PropertyDetailComponent
import au.com.deanpike.uishared.theme.Dimension.DIM_16
import au.com.deanpike.uishared.theme.Dimension.DIM_4
import au.com.deanpike.uishared.theme.Dimension.DIM_8
import au.com.deanpike.uishared.theme.MviExampleTheme
import coil.compose.AsyncImage

@Composable
fun PropertyListItem(
    position: Int,
    property: Property,
    onItemClicked: (Long) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .fillMaxWidth()
            .clickable {
                onItemClicked(property.id)
            }
            .padding(bottom = DIM_8)
            .testTag("${PROPERTY_LIST_ITEM_LAYOUT}_$position")
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("${PROPERTY_LIST_ITEM_PROPERTY_IMAGE}_$position"),
            placeholder = painterResource(id = R.drawable.gallery_placeholder),
            model = property.listingImage,
            contentDescription = stringResource(id = R.string.property_image_description)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .background(
                    color = if ((property.agencyColour?.length ?: 0) == 7) {
                        Color(android.graphics.Color.parseColor(property.agencyColour))
                    } else {
                        MaterialTheme.colorScheme.background
                    }
                ),
            horizontalArrangement = Arrangement.End
        ) {
            AsyncImage(
                modifier = Modifier
                    .height(40.dp)
                    .testTag("${PROPERTY_LIST_ITEM_AGENCY_IMAGE}_$position"),
                placeholder = painterResource(id = R.drawable.gallery_placeholder),
                model = property.agencyImage,
                contentDescription = stringResource(id = R.string.property_image_description)
            )
        }
        property.detail.price?.let {
            Text(
                modifier = Modifier
                    .padding(start = DIM_16, end = DIM_16, top = DIM_8)
                    .testTag("${PROPERTY_LIST_ITEM_PRICE}_$position"),
                text = it,
                style = MaterialTheme.typography.titleMedium
            )
        }
        property.headLine?.let {
            Text(
                modifier = Modifier
                    .padding(start = DIM_16, end = DIM_16, top = DIM_8)
                    .testTag("${PROPERTY_LIST_ITEM_HEADLINE}_$position"),
                text = it,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Text(
            modifier = Modifier
                .padding(start = DIM_16, end = DIM_16, top = DIM_4)
                .testTag("${PROPERTY_LIST_ITEM_ADDRESS}_$position"),
            text = property.address,
            style = MaterialTheme.typography.labelLarge
        )
        PropertyDetailComponent(
            parentPosition = position,
            position = position,
            details = property.detail,
            dwellingType = property.dwellingType
        )
    }
}

object PropertyListItemTesTags {
    private const val PREFIX = "PROPERTY_LIST_ITEM_"
    const val PROPERTY_LIST_ITEM_LAYOUT = "${PREFIX}LAYOUT"
    const val PROPERTY_LIST_ITEM_PROPERTY_IMAGE = "${PREFIX}PROPERTY_IMAGE"
    const val PROPERTY_LIST_ITEM_AGENCY_IMAGE = "${PREFIX}AGENCY_IMAGE"
    const val PROPERTY_LIST_ITEM_PRICE = "${PREFIX}PRICE"
    const val PROPERTY_LIST_ITEM_HEADLINE = "${PREFIX}HEADLINE"
    const val PROPERTY_LIST_ITEM_ADDRESS = "${PREFIX}ADDRESS"
}

@Composable
@Preview
fun PropertyListItemPreview() {
    MviExampleTheme {
        PropertyListItem(
            position = 0,
            property = Property(
                id = 1,
                listingType = ListingType.PROPERTY,
                address = "1 Smith Street, Sydney, 2000",
                listingImage = "https://bucket-api.domain.com.au/v1/bucket/image/2019157827_1_1_240403_091429-w1500-h1000",
                agencyImage = "https://images.domain.com.au/img/Agencys/29143/logo_29143.jpeg?buster=2024-04-03",
                agencyColour = "#ffffff",
                dwellingType = "House",
                headLine = "Two Bedroom Apartment in The Quay",
                lifecycleStatus = "New",
                detail = ListingDetails(
                    price = "Guide $520,000",
                    numberOfBedrooms = 3,
                    numberOfBathrooms = 1,
                    numberOfCarSpaces = 0
                )
            ),
        )
    }
}