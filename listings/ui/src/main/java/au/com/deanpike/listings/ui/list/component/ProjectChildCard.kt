package au.com.deanpike.listings.ui.list.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import au.com.deanpike.commonshared.model.ListingDetails
import au.com.deanpike.datashared.type.ListingType
import au.com.deanpike.listings.client.model.listing.response.ProjectChild
import au.com.deanpike.listings.ui.list.component.ProjectChildCardTestTags.PROJECT_CHILD_CARD_DETAILS
import au.com.deanpike.listings.ui.list.component.ProjectChildCardTestTags.PROJECT_CHILD_CARD_LAYOUT
import au.com.deanpike.listings.ui.list.component.ProjectChildCardTestTags.PROJECT_CHILD_CARD_LIFECYCLE_STATUS
import au.com.deanpike.listings.ui.list.component.ProjectChildCardTestTags.PROJECT_CHILD_CARD_PRICE
import au.com.deanpike.listings.ui.util.StringUtils
import au.com.deanpike.uishared.theme.Dimension.DIM_8

@Composable
fun ProjectChildCard(
    modifier: Modifier = Modifier,
    projectChild: ProjectChild,
    onProjectChildClicked: (Long) -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag(PROJECT_CHILD_CARD_LAYOUT),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors().copy(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        onClick = {
            onProjectChildClicked(projectChild.id)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = DIM_8)
        ) {
            androidx.compose.animation.AnimatedVisibility(
                visible = projectChild.listingDetails.price != null,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .testTag(PROJECT_CHILD_CARD_PRICE),
                    text = projectChild.listingDetails.price ?: "",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF111111)
                )
            }

            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .testTag(PROJECT_CHILD_CARD_DETAILS),
                    text = StringUtils.createDetailsText(
                        dwellingType = null,
                        numberOfBedrooms = projectChild.listingDetails.numberOfBedrooms,
                        numberOfBathrooms = projectChild.listingDetails.numberOfBathrooms,
                        numberOfCarSpaces = projectChild.listingDetails.numberOfCarSpaces
                    ),
                    color = Color(0xFF333333),
                    style = MaterialTheme.typography.labelLarge
                )
                Spacer(modifier = Modifier.weight(1F))
                androidx.compose.animation.AnimatedVisibility(
                    visible = projectChild.lifecycleStatus != null
                ) {
                    Text(
                        modifier = Modifier
                            .wrapContentSize()
                            .clip(RoundedCornerShape(12.dp))
                            .background(MaterialTheme.colorScheme.secondaryContainer)
                            .border(width = 1.dp, color = MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(12.dp))
                            .padding(4.dp)
                            .testTag(PROJECT_CHILD_CARD_LIFECYCLE_STATUS),
                        text = projectChild.lifecycleStatus ?: "",
                        color = Color(0xFF2B2B2B),
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Start
                    )
                }
            }
        }
    }
}

object ProjectChildCardTestTags {
    private const val PREFIX = "PROJECT_CHILD_CARD_"
    const val PROJECT_CHILD_CARD_LAYOUT = "${PREFIX}LAYOUT"
    const val PROJECT_CHILD_CARD_PRICE = "${PREFIX}PRICE"
    const val PROJECT_CHILD_CARD_DETAILS = "${PREFIX}DETAILS"
    const val PROJECT_CHILD_CARD_LIFECYCLE_STATUS = "${PREFIX}LIFECYCLE_STATUS"
}

@Preview
@Composable
fun ProjectChildCardPreview() {
    ProjectChildCard(
        projectChild = ProjectChild(
            id = 1111,
            listingType = ListingType.PROPERTY,
            lifecycleStatus = "New",
            listingDetails = ListingDetails(
                price = "Offers Above $659,275",
                numberOfBedrooms = 3,
                numberOfBathrooms = 2,
                numberOfCarSpaces = 1
            )
        )
    )
}