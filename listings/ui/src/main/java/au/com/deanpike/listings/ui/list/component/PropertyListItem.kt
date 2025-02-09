package au.com.deanpike.listings.ui.list.component

import au.com.deanpike.uishared.R as RShared
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import au.com.deanpike.commonshared.model.ListingDetails
import au.com.deanpike.datashared.type.ListingType
import au.com.deanpike.listings.client.model.listing.response.Property
import au.com.deanpike.listings.ui.list.component.PropertyListItemTesTags.PROPERTY_LIST_ITEM_ADDRESS
import au.com.deanpike.listings.ui.list.component.PropertyListItemTesTags.PROPERTY_LIST_ITEM_HEADLINE
import au.com.deanpike.listings.ui.list.component.PropertyListItemTesTags.PROPERTY_LIST_ITEM_LAYOUT
import au.com.deanpike.listings.ui.list.component.PropertyListItemTesTags.PROPERTY_LIST_ITEM_PRICE
import au.com.deanpike.listings.ui.list.component.PropertyListItemTesTags.PROPERTY_LIST_ITEM_PROPERTY_IMAGE
import au.com.deanpike.uishared.component.AgencyBannerComponent
import au.com.deanpike.uishared.component.LifecycleStatusComponent
import au.com.deanpike.uishared.component.PropertyDetailComponent
import au.com.deanpike.uishared.theme.Dimension.DIM_16
import au.com.deanpike.uishared.theme.Dimension.DIM_4
import au.com.deanpike.uishared.theme.Dimension.DIM_8
import au.com.deanpike.uishared.theme.MviExampleTheme
import coil.compose.AsyncImage

@Composable
fun PropertyListItem(
    property: Property,
    onItemClicked: (Long) -> Unit = {}
) {
    ConstraintLayout(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .fillMaxWidth()
            .clickable {
                onItemClicked(property.id)
            }
            .padding(bottom = DIM_8)
            .testTag(PROPERTY_LIST_ITEM_LAYOUT)
    ) {

        val (lifecycleRef, propertyImageRef, agencyRef, priceRef, headlineRef, addressRef, propertyDetailRef) = createRefs()

        AsyncImage(
            modifier = Modifier
                .constrainAs(propertyImageRef) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth()
                .defaultMinSize(minHeight = 240.dp)
                .testTag(PROPERTY_LIST_ITEM_PROPERTY_IMAGE),
            placeholder = painterResource(id = RShared.drawable.gallery_placeholder),
            error = painterResource(id = RShared.drawable.gallery_placeholder),
            model = property.listingImage,
            contentDescription = stringResource(id = RShared.string.property_image_description)
        )

        LifecycleStatusComponent(
            modifier = Modifier.constrainAs(lifecycleRef) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
            },
            lifecycleStatus = property.lifecycleStatus
        )

        AgencyBannerComponent(
            modifier = Modifier
                .constrainAs(agencyRef) {
                    start.linkTo(parent.start)
                    top.linkTo(propertyImageRef.bottom)
                },
            agencyColour = property.agencyColour,
            logo = property.agencyImage
        )

        property.detail.price?.let {
            Text(
                modifier = Modifier
                    .constrainAs(priceRef) {
                        start.linkTo(parent.start)
                        top.linkTo(agencyRef.bottom)
                    }
                    .padding(start = DIM_16, end = DIM_16, top = DIM_8)
                    .testTag(PROPERTY_LIST_ITEM_PRICE),
                text = it,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }

        Text(
            modifier = Modifier
                .constrainAs(addressRef) {
                    start.linkTo(parent.start)
                    top.linkTo(priceRef.bottom)
                }
                .padding(start = DIM_16, end = DIM_16, top = DIM_4)
                .testTag(PROPERTY_LIST_ITEM_ADDRESS),
            text = property.address,
            style = MaterialTheme.typography.labelLarge
        )

        PropertyDetailComponent(
            modifier = Modifier
                .constrainAs(propertyDetailRef) {
                    start.linkTo(parent.start)
                    top.linkTo(addressRef.bottom)
                }
                .padding(start = DIM_16, end = DIM_16),
            details = property.detail,
            dwellingType = property.dwellingType
        )

        property.headLine?.let {
            Text(
                modifier = Modifier
                    .constrainAs(headlineRef) {
                        start.linkTo(parent.start)
                        top.linkTo(propertyDetailRef.bottom)
                    }
                    .padding(start = DIM_16, end = DIM_16, top = DIM_8)
                    .testTag(PROPERTY_LIST_ITEM_HEADLINE),
                text = it,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
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
fun PropertyListItemPreview() {
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