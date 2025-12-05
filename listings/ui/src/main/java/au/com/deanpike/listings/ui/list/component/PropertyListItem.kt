package au.com.deanpike.listings.ui.list.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import au.com.deanpike.commonshared.model.ListingDetails
import au.com.deanpike.datashared.type.ListingType
import au.com.deanpike.listings.client.model.listing.response.Property
import au.com.deanpike.listings.ui.list.component.PropertyListItemTesTags.PROPERTY_LIST_ITEM_ADDRESS
import au.com.deanpike.listings.ui.list.component.PropertyListItemTesTags.PROPERTY_LIST_ITEM_HEADLINE
import au.com.deanpike.listings.ui.list.component.PropertyListItemTesTags.PROPERTY_LIST_ITEM_LAYOUT
import au.com.deanpike.listings.ui.list.component.PropertyListItemTesTags.PROPERTY_LIST_ITEM_PRICE
import au.com.deanpike.listings.ui.list.component.PropertyListItemTesTags.PROPERTY_LIST_ITEM_PROPERTY_IMAGE
import au.com.deanpike.uishared.R as RShared
import au.com.deanpike.uishared.component.AgencyBannerComponent
import au.com.deanpike.uishared.component.BedBathCarComponent
import au.com.deanpike.uishared.component.LifecycleStatusComponent
import au.com.deanpike.uishared.theme.Dimension.DIM_16
import au.com.deanpike.uishared.theme.Dimension.DIM_4
import au.com.deanpike.uishared.theme.Dimension.DIM_8
import au.com.deanpike.uishared.theme.MviExampleTheme
import au.com.deanpike.uishared.theme.outlineLight
import coil.compose.AsyncImage

@Composable
fun PropertyListItem(
    property: Property,
    onItemClicked: (Long) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .padding(start = DIM_16, end = DIM_16, top = DIM_8, bottom = DIM_8)
            .fillMaxWidth()
            .clickable {
                onItemClicked(property.id)
            }
            .testTag(PROPERTY_LIST_ITEM_LAYOUT),
        shape = RoundedCornerShape(DIM_8),
        border = BorderStroke(width = 0.5.dp, color = MaterialTheme.colorScheme.outline),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(modifier = Modifier.background(color = MaterialTheme.colorScheme.background)) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
                    .padding(bottom = DIM_16)
            ) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .defaultMinSize(minHeight = 240.dp)
                        .testTag(PROPERTY_LIST_ITEM_PROPERTY_IMAGE),
                    placeholder = painterResource(id = RShared.drawable.gallery_placeholder),
                    error = painterResource(id = RShared.drawable.gallery_placeholder),
                    model = property.listingImage,
                    contentDescription = stringResource(id = RShared.string.property_image_description)
                )

                AgencyBannerComponent(
                    agencyColour = property.agencyColour,
                    logo = property.agencyImage
                )

                property.detail.price?.let {
                    Text(
                        modifier = Modifier
                            .padding(start = DIM_16, end = DIM_16, top = DIM_8)
                            .testTag(PROPERTY_LIST_ITEM_PRICE),
                        text = it,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                }

                Text(
                    modifier = Modifier
                        .padding(start = DIM_16, end = DIM_16, top = DIM_4)
                        .testTag(PROPERTY_LIST_ITEM_ADDRESS),
                    text = property.address,
                    style = MaterialTheme.typography.labelLarge
                )

                BedBathCarComponent(
                    modifier = Modifier
                        .padding(start = DIM_16, end = DIM_16, top = DIM_8),
                    bedrooms = property.detail.numberOfBedrooms,
                    bathrooms = property.detail.numberOfBathrooms,
                    carSpaces = property.detail.numberOfCarSpaces
                )

                property.headLine?.let {
                    Text(
                        modifier = Modifier
                            .padding(start = DIM_16, end = DIM_16, top = DIM_8)
                            .testTag(PROPERTY_LIST_ITEM_HEADLINE),
                        text = it,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
            LifecycleStatusComponent(lifecycleStatus = property.lifecycleStatus)
        }

    }
}

object PropertyListItemTesTags {
    private const val PREFIX = "PROPERTY_LIST_ITEM_"
    const val PROPERTY_LIST_ITEM_LAYOUT = "${PREFIX}LAYOUT"
    const val PROPERTY_LIST_ITEM_PROPERTY_IMAGE = "${PREFIX}PROPERTY_IMAGE"
    const val PROPERTY_LIST_ITEM_PRICE = "${PREFIX}PRICE"
    const val PROPERTY_LIST_ITEM_HEADLINE = "${PREFIX}HEADLINE"
    const val PROPERTY_LIST_ITEM_ADDRESS = "${PREFIX}ADDRESS"
}

@Composable
@Preview
fun PropertyListItemPreview1() {
    MviExampleTheme {
        PropertyListItem(
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