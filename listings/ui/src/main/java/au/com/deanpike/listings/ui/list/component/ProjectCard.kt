package au.com.deanpike.listings.ui.list.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import au.com.deanpike.commonshared.model.ListingDetails
import au.com.deanpike.datashared.type.ListingType
import au.com.deanpike.listings.client.model.listing.response.Project
import au.com.deanpike.listings.client.model.listing.response.ProjectChild
import au.com.deanpike.listings.ui.list.ListingListScreenEvent
import au.com.deanpike.listings.ui.list.component.ProjectCardTesTags.PROJECT_CARD_ADDRESS
import au.com.deanpike.listings.ui.list.component.ProjectCardTesTags.PROJECT_CARD_CHILD_ROW
import au.com.deanpike.listings.ui.list.component.ProjectCardTesTags.PROJECT_CARD_IMAGE
import au.com.deanpike.listings.ui.list.component.ProjectCardTesTags.PROJECT_CARD_LAYOUT
import au.com.deanpike.listings.ui.list.component.ProjectCardTesTags.PROJECT_CARD_PROJECT_NAME
import au.com.deanpike.uishared.R
import au.com.deanpike.uishared.theme.AppTheme
import au.com.deanpike.uishared.theme.Dimension.DIM_16
import au.com.deanpike.uishared.theme.Dimension.DIM_4
import au.com.deanpike.uishared.theme.Dimension.DIM_8
import au.com.deanpike.uishared.util.ThemePreviews
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.request.error
import coil3.request.placeholder

@Composable
fun ProjectCard(
    project: Project,
    onEvent: (ListingListScreenEvent) -> Unit = {},
) {
    val listState = rememberLazyListState()

    LaunchedEffect(project.properties) {
        listState.scrollToItem(0)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onEvent(
                    ListingListScreenEvent.OnProjectSelected(
                        id = project.id,
                        address = project.address
                    )
                )
            }
            .testTag(PROJECT_CARD_LAYOUT),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors().copy(
            containerColor = MaterialTheme.colorScheme.surface
        ),
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.padding(bottom = 12.dp)
            ) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16F / 9F)
                        .testTag(PROJECT_CARD_IMAGE),
                    placeholder = painterResource(id = R.drawable.gallery_placeholder),
                    error = painterResource(id = R.drawable.gallery_placeholder),
                    model = ImageRequest.Builder(LocalContext.current)
                        .diskCachePolicy(coil3.request.CachePolicy.ENABLED)
                        .data(project.listingImage)
                        .crossfade(true)
                        .error(R.drawable.gallery_placeholder)
                        .placeholder(R.drawable.gallery_placeholder)
                        .build(),
                    contentDescription = stringResource(id = R.string.property_image_description),
                    contentScale = ContentScale.FillBounds
                )

                Text(
                    modifier = Modifier
                        .padding(start = DIM_16, end = DIM_16, top = DIM_8)
                        .testTag(PROJECT_CARD_PROJECT_NAME),
                    text = project.projectName ?: "",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                )

                Text(
                    modifier = Modifier
                        .padding(
                            start = DIM_16,
                            end = DIM_16,
                            top = DIM_4,
                            bottom = DIM_4
                        )
                        .testTag(PROJECT_CARD_ADDRESS),
                    text = project.address,
                    style = MaterialTheme.typography.labelLarge
                )

                LazyRow(
                    state = listState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag(PROJECT_CARD_CHILD_ROW),
                    contentPadding = PaddingValues(horizontal = DIM_8),
                    horizontalArrangement = Arrangement.spacedBy(DIM_8)
                ) {
                    items(project.properties.size) { index ->
                        ProjectChildCard(
                            modifier = Modifier.fillParentMaxWidth(0.9F),
                            projectChild = project.properties[index],
                            onProjectChildClicked = { childId ->
                                onEvent(
                                    ListingListScreenEvent.OnPropertySelected(
                                        id = childId,
                                        address = project.address
                                    )
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}

object ProjectCardTesTags {
    private const val PREFIX = "PROJECT_CARD_"
    const val PROJECT_CARD_LAYOUT = "${PREFIX}LAYOUT"
    const val PROJECT_CARD_IMAGE = "${PREFIX}IMAGE"
    const val PROJECT_CARD_PROJECT_NAME = "${PREFIX}PROJECT_NAME"
    const val PROJECT_CARD_ADDRESS = "${PREFIX}ADDRESS"
    const val PROJECT_CARD_CHILD_ROW = "${PREFIX}CHILD_ROW"
}

@ThemePreviews
@Composable
fun ProjectCardPreview() {
    AppTheme {
        ProjectCard(
            project = Project(
                id = 1234,
                listingType = ListingType.PROJECT,
                address = "100 Harris Street, Pyrmont, 2000",
                listingImage = "https://bucket-api.domain.com.au/v1/bucket/image/10c22c15-f2cb-4b50-b55c-ad9466bc1427-w2500-h1668",
                bannerImage = "https://bucket-api.domain.com.au/v1/bucket/image/5501_3_13_220624_031714-w1600-h1080",
                logoImage = "https://images.domain.com.au/img/Agencys/devproject/logo_5501_240421_114243",
                projectName = "Blakelys Run",
                projectColour = "#c4bfad",
                properties = listOf(
                    ProjectChild(
                        id = 1111,
                        listingType = ListingType.PROPERTY,
                        lifecycleStatus = "New",
                        listingDetails = ListingDetails(
                            price = "Offers Above $659,275",
                            numberOfBedrooms = 3,
                            numberOfBathrooms = 2,
                            numberOfCarSpaces = 1
                        )
                    ),
                    ProjectChild(
                        id = 2222,
                        listingType = ListingType.PROPERTY,
                        lifecycleStatus = "Sold",
                        listingDetails = ListingDetails(
                            price = "Contact Agent",
                            numberOfBedrooms = null,
                            numberOfBathrooms = null,
                            numberOfCarSpaces = null
                        )
                    )
                )
            )
        )
    }
}