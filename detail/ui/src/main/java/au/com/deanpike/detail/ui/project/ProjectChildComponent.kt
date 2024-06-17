package au.com.deanpike.detail.ui.project

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
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
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import au.com.deanpike.commonshared.model.ListingDetails
import au.com.deanpike.detail.client.model.detail.ProjectChild
import au.com.deanpike.detail.ui.project.ProjectChildComponentTestTags.PROJECT_CHILD_IMAGE
import au.com.deanpike.detail.ui.project.ProjectChildComponentTestTags.PROJECT_CHILD_LAYOUT
import au.com.deanpike.detail.ui.project.ProjectChildComponentTestTags.PROJECT_CHILD_LIFECYCLE
import au.com.deanpike.detail.ui.project.ProjectChildComponentTestTags.PROJECT_CHILD_PRICE
import au.com.deanpike.uishared.R
import au.com.deanpike.uishared.component.PropertyDetailComponent
import au.com.deanpike.uishared.theme.Dimension.DIM_8
import au.com.deanpike.uishared.theme.MviExampleTheme
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun ProjectChildComponent(
    position: Int,
    child: ProjectChild,
    onProjectChildClicked: (Long) -> Unit = {}
) {
    Card(
        modifier = Modifier.clickable {
            onProjectChildClicked(child.id)
        },
        colors = CardDefaults.cardColors().copy(
            containerColor = Color.Gray.copy(alpha = 0.05F)
        ),
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outline)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .width(400.dp)
                .padding(DIM_8)
                .testTag("${PROJECT_CHILD_LAYOUT}_$position")
        ) {
            val (imageRef, priceRef, detailRef, lifecycleRef) = createRefs()

            child.propertyImage?.let {
                AsyncImage(
                    modifier = Modifier
                        .constrainAs(imageRef) {
                            start.linkTo(parent.start)
                            top.linkTo(parent.top)
                        }
                        .size(120.dp)
                        .testTag("${PROJECT_CHILD_IMAGE}_$position"),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(it)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(id = R.drawable.gallery_placeholder),
                    contentScale = ContentScale.Crop,
                    contentDescription = stringResource(id = R.string.property_image),
                    alignment = Alignment.Center
                )
            }

            child.price?.let {
                Text(
                    modifier = Modifier
                        .constrainAs(priceRef) {
                            bottom.linkTo(imageRef.bottom)
                            start.linkTo(imageRef.end)
                            end.linkTo(parent.end)
                            width = Dimension.fillToConstraints
                        }
                        .padding(
                            top = DIM_8,
                            start = DIM_8,
                            end = DIM_8
                        )
                        .fillMaxWidth(0.8F)
                        .testTag("${PROJECT_CHILD_PRICE}_$position"),
                    text = it,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            PropertyDetailComponent(
                modifier = Modifier
                    .constrainAs(detailRef) {
                        top.linkTo(parent.top)
                        start.linkTo(imageRef.end)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }
                    .padding(start = DIM_8, end = DIM_8),
                parentPosition = 0,
                position = position,
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
                        .constrainAs(lifecycleRef) {
                            start.linkTo(imageRef.end)
                            top.linkTo(detailRef.bottom)
                        }
                        .padding(start = DIM_8, top = DIM_8)
                        .testTag("${PROJECT_CHILD_LIFECYCLE}_$position"),
                    text = it)
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
            position = 0,
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