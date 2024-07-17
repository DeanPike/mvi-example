package au.com.deanpike.detail.ui.project

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import au.com.deanpike.detail.client.model.detail.ProjectChild
import au.com.deanpike.detail.ui.project.ProjectChildrenComponentTestTags.PROJECT_CHILDREN
import au.com.deanpike.uishared.theme.Dimension.DIM_8
import au.com.deanpike.uishared.theme.MviExampleTheme

@Composable
fun ProjectChildrenComponent(
    childListings: List<ProjectChild>,
    onProjectChildClicked: (Long) -> Unit = {}
) {
    var screenWidth by remember{
        mutableIntStateOf(0)

    }
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .onGloballyPositioned { coordinates ->
                screenWidth = coordinates.size.width
            }
            .testTag(PROJECT_CHILDREN),
        contentPadding = PaddingValues(horizontal = DIM_8),
        horizontalArrangement = Arrangement.spacedBy(DIM_8)
    ) {
        childListings.forEachIndexed { index, projectChild ->
            item(key = projectChild.id) {
                ProjectChildComponent(
                    position = index,
                    child = projectChild,
                    screenWidth = screenWidth,
                    onProjectChildClicked = onProjectChildClicked
                )
            }
        }
    }
}

object ProjectChildrenComponentTestTags {
    private const val PREFIX = "PROJECT_CHILDREN_"
    const val PROJECT_CHILDREN = "${PREFIX}PROJECT_CHILDREN"
}

@Preview(showBackground = true)
@Composable
fun ProjectChildrenComponentPreview() {
    val childListings = listOf(
        ProjectChild(
            id = 2019256252,
            bedroomCount = 2,
            bathroomCount = 2,
            carSpaceCount = 1,
            price = "Starting from $2,000,000 with extra data",
            propertyTypeDescription = "newApartments",
            propertyUrl = "https://www.domain.com.au/13-crown-street-wollongong-nsw-2500-2019256252",
            propertyImage = "https://bucket-api.domain.com.au/v1/bucket/image/2019256252_1_1_240521_034448-w3000-h1875",
            lifecycleStatus = "New Home"
        ),
        ProjectChild(
            id = 2019256302,
            bedroomCount = 3,
            bathroomCount = 2,
            carSpaceCount = 1,
            price = "Starting from $3,250,000",
            propertyTypeDescription = "newApartments",
            propertyUrl = "https://www.domain.com.au/13-crown-street-wollongong-nsw-2500-2019256302",
            propertyImage = "https://bucket-api.domain.com.au/v1/bucket/image/2019256252_5_1_240521_034450-w2500-h1468",
            lifecycleStatus = "New Home"
        )
    )
    MviExampleTheme {
        ProjectChildrenComponent(
            childListings = childListings
        )
    }
}