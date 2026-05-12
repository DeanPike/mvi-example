package au.com.deanpike.listings.ui.list.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import au.com.deanpike.commonshared.model.ListingDetails
import au.com.deanpike.datashared.type.ListingType
import au.com.deanpike.listings.client.model.listing.response.Property
import au.com.deanpike.listings.ui.list.ListingListScreenEvent
import au.com.deanpike.listings.ui.list.component.PropertyCardTestTags.PROPERTY_CARD_ADDRESS
import au.com.deanpike.listings.ui.list.component.PropertyCardTestTags.PROPERTY_CARD_DESCRIPTION
import au.com.deanpike.listings.ui.list.component.PropertyCardTestTags.PROPERTY_CARD_HEADLINE
import au.com.deanpike.listings.ui.list.component.PropertyCardTestTags.PROPERTY_CARD_IMAGE
import au.com.deanpike.listings.ui.list.component.PropertyCardTestTags.PROPERTY_CARD_LAYOUT
import au.com.deanpike.listings.ui.list.component.PropertyCardTestTags.PROPERTY_CARD_PRICE
import au.com.deanpike.listings.ui.util.StringUtils
import au.com.deanpike.uishared.R
import au.com.deanpike.uishared.theme.MviExampleTheme
import coil3.compose.AsyncImage

@Composable
fun PropertyCard(
    property: Property,
    onEvent: (ListingListScreenEvent) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onEvent(
                    ListingListScreenEvent.OnPropertySelected(
                        id = property.id,
                        address = property.address
                    )
                )
            }
            .testTag(PROPERTY_CARD_LAYOUT),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors().copy(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
    ) {
        Box(contentAlignment = Alignment.Center) {
            Column(
                modifier = Modifier.padding(bottom = 12.dp)
            ) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16F / 9F)
                        .testTag(PROPERTY_CARD_IMAGE),
                    placeholder = painterResource(id = R.drawable.gallery_placeholder),
                    error = painterResource(id = R.drawable.gallery_placeholder),
                    model = property.listingImage,
                    contentDescription = stringResource(id = R.string.property_image_description),
                    contentScale = ContentScale.FillBounds
                )

                androidx.compose.animation.AnimatedVisibility(
                    visible = property.detail.price != null
                ) {
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .testTag(PROPERTY_CARD_PRICE),
                        text = property.detail.price ?: "",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF111111)
                    )
                }

                Text(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .testTag(PROPERTY_CARD_DESCRIPTION),
                    text = StringUtils.createDetailsText(
                        dwellingType = property.dwellingType,
                        numberOfBedrooms = property.detail.numberOfBedrooms,
                        numberOfBathrooms = property.detail.numberOfBathrooms,
                        numberOfCarSpaces = property.detail.numberOfCarSpaces
                    ),
                    color = Color(0xFF333333),
                    style = MaterialTheme.typography.labelLarge
                )

                Text(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .testTag(PROPERTY_CARD_ADDRESS),
                    text = property.address,
                    color = Color(0xFF333333),
                    style = MaterialTheme.typography.labelLarge
                )

                Text(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .testTag(PROPERTY_CARD_HEADLINE),
                    text = property.headLine ?: "",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF111111)
                )
            }

            androidx.compose.animation.AnimatedVisibility(
                visible = property.lifecycleStatus != null,
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Text(
                    modifier = Modifier
                        .wrapContentSize()
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                        .border(width = 1.dp, color = MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(12.dp))
                        .padding(4.dp),
                    text = property.lifecycleStatus ?: "",
                    color = Color(0xFF2B2B2B),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Start
                )
            }
        }
    }
}

object PropertyCardTestTags {
    private const val PREFIX = "PROPERTY_CARD_"
    const val PROPERTY_CARD_LAYOUT = "${PREFIX}LAYOUT"
    const val PROPERTY_CARD_IMAGE = "${PREFIX}IMAGE"
    const val PROPERTY_CARD_PRICE = "${PREFIX}PRICE"
    const val PROPERTY_CARD_DESCRIPTION = "${PREFIX}DESCRIPTION"
    const val PROPERTY_CARD_ADDRESS = "${PREFIX}ADDRESS"
    const val PROPERTY_CARD_HEADLINE = "${PREFIX}HEADLINE"
}

@Preview(device = Devices.PIXEL_8_PRO, showBackground = true)
@Composable
fun PropertyCardPreview() {
    MviExampleTheme() {
        PropertyCard(
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
            )
        )
    }
}