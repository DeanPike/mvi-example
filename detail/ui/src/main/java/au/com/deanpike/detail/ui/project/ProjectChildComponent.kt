package au.com.deanpike.detail.ui.project

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import au.com.deanpike.commonshared.model.ListingDetails
import au.com.deanpike.detail.client.model.detail.ProjectChild
import au.com.deanpike.detail.ui.project.ProjectChildComponentTestTags.PROJECT_CHILD_IMAGE
import au.com.deanpike.detail.ui.project.ProjectChildComponentTestTags.PROJECT_CHILD_LAYOUT
import au.com.deanpike.detail.ui.project.ProjectChildComponentTestTags.PROJECT_CHILD_LIFECYCLE
import au.com.deanpike.detail.ui.project.ProjectChildComponentTestTags.PROJECT_CHILD_PRICE
import au.com.deanpike.uishared.R
import au.com.deanpike.uishared.base.listingIdTestTag
import au.com.deanpike.uishared.component.PropertyDetailComponent
import au.com.deanpike.uishared.theme.Dimension.DIM_16
import au.com.deanpike.uishared.theme.Dimension.DIM_8
import au.com.deanpike.uishared.theme.MviExampleTheme
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun ProjectChildComponent(
    child: ProjectChild,
    onProjectChildClicked: (Long) -> Unit = {}
) {
    Card(
        modifier = Modifier.width(100.dp),
        colors = CardDefaults.cardColors().copy(
            containerColor = Color.Gray.copy(alpha = 0.05F)
        ),
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outline)
    ) {
        Row(modifier = Modifier
            .padding(DIM_16)
            .clickable {
                onProjectChildClicked(child.id)
            }
            .listingIdTestTag(
                tag = PROJECT_CHILD_LAYOUT,
                id = child.id
            )
        ) {
            child.propertyImage?.let {
                AsyncImage(
                    modifier = Modifier
                        .size(120.dp)
                        .testTag(PROJECT_CHILD_IMAGE),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(it)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(id = R.drawable.gallery_placeholder),
                    fallback = painterResource(id = R.drawable.gallery_placeholder),
                    error = painterResource(id = R.drawable.gallery_placeholder),
                    contentScale = ContentScale.Crop,
                    contentDescription = stringResource(id = R.string.property_image),
                    alignment = Alignment.Center
                )
            }
            Column {
                PropertyDetailComponent(
                    modifier = Modifier.padding(start = DIM_8, end = DIM_8),
                    details = ListingDetails(
                        price = child.price,
                        numberOfBedrooms = child.bedroomCount,
                        numberOfBathrooms = child.bathroomCount,
                        numberOfCarSpaces = child.carSpaceCount
                    ),
                    dwellingType = null
                )

                child.lifecycleStatus?.let {
                    Text(
                        modifier = Modifier
                            .padding(start = DIM_8, top = DIM_8)
                            .testTag(PROJECT_CHILD_LIFECYCLE),
                        text = it
                    )
                }

                child.price?.let {
                    Text(
                        modifier = Modifier
                            .padding(
                                top = DIM_8,
                                start = DIM_8,
                                end = DIM_8
                            )
                            .fillMaxWidth(0.8F)
                            .testTag(PROJECT_CHILD_PRICE),
                        text = it,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

object ProjectChildComponentTestTags {
    private const val PREFIX = "PROJECT_CHILD_"
    const val PROJECT_CHILD_LAYOUT = "${PREFIX}LAYOUT"
    const val PROJECT_CHILD_IMAGE = "${PREFIX}IMAGE"
    const val PROJECT_CHILD_PRICE = "${PREFIX}PRICE"
    const val PROJECT_CHILD_LIFECYCLE = "${PREFIX}LIFECYCLE"
}

@Preview(showBackground = true)
@Composable
fun ProjectChildComponentPreview() {
    MviExampleTheme {
        ProjectChildComponent(
            child = ProjectChild(
                id = 2019256252,
                bedroomCount = 2,
                bathroomCount = 2,
                carSpaceCount = 1,
                price = "Starting from $2,000,000 with extra data",
                propertyTypeDescription = "newApartments",
                propertyUrl = "https://www.domain.com.au/13-crown-street-wollongong-nsw-2500-2019256252",
                propertyImage = "https://bucket-api.domain.com.au/v1/bucket/image/2019256252_1_1_240521_034448-w3000-h1875",
                lifecycleStatus = "New Home"
            )
        )
    }
}