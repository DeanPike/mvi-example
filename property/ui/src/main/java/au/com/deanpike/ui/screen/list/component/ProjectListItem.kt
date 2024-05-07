package au.com.deanpike.ui.screen.list.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import au.com.deanpike.client.model.listing.response.ListingDetails
import au.com.deanpike.client.model.listing.response.ListingType
import au.com.deanpike.client.model.listing.response.Project
import au.com.deanpike.client.model.listing.response.ProjectChild
import au.com.deanpike.ui.R
import au.com.deanpike.ui.screen.list.component.ProjectListItemTesTags.PROJECT_LIST_ITEM_ADDRESS
import au.com.deanpike.ui.screen.list.component.ProjectListItemTesTags.PROJECT_LIST_ITEM_AGENCY_IMAGE
import au.com.deanpike.ui.screen.list.component.ProjectListItemTesTags.PROJECT_LIST_ITEM_BANNER_IMAGE
import au.com.deanpike.ui.screen.list.component.ProjectListItemTesTags.PROJECT_LIST_ITEM_CHILDREN
import au.com.deanpike.ui.screen.list.component.ProjectListItemTesTags.PROJECT_LIST_ITEM_CHILD_BUTTON
import au.com.deanpike.ui.screen.list.component.ProjectListItemTesTags.PROJECT_LIST_ITEM_CHILD_COUNT
import au.com.deanpike.ui.screen.list.component.ProjectListItemTesTags.PROJECT_LIST_ITEM_IMAGE
import au.com.deanpike.ui.screen.list.component.ProjectListItemTesTags.PROJECT_LIST_ITEM_LAYOUT
import au.com.deanpike.ui.screen.list.component.ProjectListItemTesTags.PROJECT_LIST_ITEM_PROJECT_NAME
import au.com.deanpike.uishared.theme.Dimension
import au.com.deanpike.uishared.theme.Dimension.DIM_16
import au.com.deanpike.uishared.theme.Dimension.DIM_4
import au.com.deanpike.uishared.theme.MviExampleTheme
import coil.compose.AsyncImage

@Composable
fun ProjectListItem(
    position: Int,
    project: Project,
    onProjectClicked: (Long) -> Unit = {},
    onProjectChildClicked: (Long) -> Unit = {}
) {
    var expandList by rememberSaveable {
        mutableStateOf(false)
    }

    var targetRotation by remember {
        mutableFloatStateOf(0F)
    }
    val r = animateFloatAsState(
        label = "Arrow animation",
        targetValue = targetRotation,
        animationSpec = tween(
            500
        )
    )

    LaunchedEffect(key1 = expandList) {
        targetRotation = if (expandList) 180F else 0F
    }

    ConstraintLayout(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .fillMaxWidth()
            .clickable {
                onProjectClicked(project.id)
            }
            .padding(bottom = Dimension.DIM_8)
            .testTag("${PROJECT_LIST_ITEM_LAYOUT}_$position")
    ) {
        val (imageRef, bannerRef, logoRowRef, projectNameRef, addressRef, projectCountRef, childrenMenuRef) = createRefs()

        AsyncImage(
            modifier = Modifier
                .constrainAs(bannerRef) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                }
                .fillMaxWidth()
                .height(40.dp)
                .background(
                    color = if ((project.projectColour?.length ?: 0) == 7) {
                        Color(android.graphics.Color.parseColor(project.projectColour))
                    } else {
                        MaterialTheme.colorScheme.background
                    }
                )
                .testTag("${PROJECT_LIST_ITEM_BANNER_IMAGE}_$position"),
            placeholder = painterResource(id = R.drawable.gallery_placeholder),
            model = project.bannerImage,
            contentDescription = stringResource(id = R.string.project_banner_image_description),
        )

        AsyncImage(
            modifier = Modifier
                .constrainAs(imageRef) {
                    start.linkTo(parent.start)
                    top.linkTo(bannerRef.bottom)
                }
                .fillMaxWidth()
                .testTag("${PROJECT_LIST_ITEM_IMAGE}_$position"),
            placeholder = painterResource(id = R.drawable.gallery_placeholder),
            model = project.listingImage,
            contentDescription = stringResource(id = R.string.property_image_description)
        )

        Row(
            modifier = Modifier
                .constrainAs(logoRowRef) {
                    start.linkTo(parent.start)
                    top.linkTo(imageRef.bottom)
                }
                .fillMaxWidth()
                .height(40.dp)
                .background(
                    color = if ((project.projectColour?.length ?: 0) == 7) {
                        Color(android.graphics.Color.parseColor(project.projectColour))
                    } else {
                        MaterialTheme.colorScheme.background
                    }
                ),
            horizontalArrangement = Arrangement.End
        ) {
            AsyncImage(
                modifier = Modifier
                    .height(40.dp)
                    .testTag("${PROJECT_LIST_ITEM_AGENCY_IMAGE}_$position"),
                placeholder = painterResource(id = R.drawable.gallery_placeholder),
                model = project.logoImage,
                contentDescription = stringResource(id = R.string.property_image_description)
            )
        }

        project.projectName?.let {
            Text(
                modifier = Modifier
                    .constrainAs(projectNameRef) {
                        start.linkTo(parent.start)
                        top.linkTo(logoRowRef.bottom)
                    }
                    .padding(start = DIM_16, end = DIM_16, top = Dimension.DIM_8)
                    .testTag("${PROJECT_LIST_ITEM_PROJECT_NAME}_$position"),
                text = it,
                style = MaterialTheme.typography.titleMedium
            )
        }
        Text(
            modifier = Modifier
                .constrainAs(addressRef) {
                    start.linkTo(parent.start)
                    top.linkTo(projectNameRef.bottom)
                }
                .padding(
                    start = DIM_16,
                    end = DIM_16,
                    top = DIM_4,
                    bottom = DIM_4
                )
                .testTag("${PROJECT_LIST_ITEM_ADDRESS}_$position"),
            text = project.address,
            style = MaterialTheme.typography.labelLarge
        )
        OutlinedButton(
            modifier = Modifier
                .constrainAs(projectCountRef) {
                    start.linkTo(parent.start)
                    top.linkTo(addressRef.bottom)
                }
                .fillMaxWidth()
                .padding(start = DIM_16, end = DIM_16)
                .testTag("${PROJECT_LIST_ITEM_CHILD_BUTTON}_$position"),
            onClick = { expandList = !expandList }
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    modifier = Modifier
                        .graphicsLayer {
                            rotationZ = r.value
                        },
                    painter = painterResource(id = R.drawable.arrow_drop_down),
                    contentDescription = ""
                )
                Text(
                    modifier = Modifier.testTag("${PROJECT_LIST_ITEM_CHILD_COUNT}_$position"),
                    text = pluralStringResource(id = R.plurals.project_properties, project.properties.size, project.properties.size)
                )
                Icon(
                    modifier = Modifier
                        .graphicsLayer {
                            rotationZ = r.value * -1
                        },
                    painter = painterResource(id = R.drawable.arrow_drop_down),
                    contentDescription = ""
                )
            }
        }
        AnimatedContent(
            label = "project listings",
            modifier = Modifier
                .constrainAs(childrenMenuRef) {
                    start.linkTo(parent.start)
                    top.linkTo(projectCountRef.bottom)
                }
                .fillMaxWidth(),
            targetState = expandList,
            transitionSpec = {
                fadeIn() + slideInVertically(animationSpec = tween(500),
                    initialOffsetY = { fullHeight -> -fullHeight }) togetherWith
                    fadeOut(animationSpec = tween(200))
            }
        ) {
            if (it) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = DIM_16, end = DIM_16)
                        .scrollable(state = rememberScrollState(), orientation = Orientation.Vertical)
                        .testTag("${PROJECT_LIST_ITEM_CHILDREN}_$position")
                ) {
                    project.properties.forEachIndexed { index, projectChild ->
                        ProjectChildListItemComponent(
                            parentPosition = position,
                            position = index,
                            id = projectChild.id,
                            lifecycleStatus = projectChild.lifecycleStatus,
                            listingDetails = projectChild.listingDetails,
                            onProjectChildClicked = onProjectChildClicked
                        )
                    }
                }
            }
        }
    }
}

object ProjectListItemTesTags {
    private const val PREFIX = "PROJECT_LIST_ITEM_"
    const val PROJECT_LIST_ITEM_LAYOUT = "${PREFIX}LAYOUT"
    const val PROJECT_LIST_ITEM_BANNER_IMAGE = "${PREFIX}BANNER_IMAGE"
    const val PROJECT_LIST_ITEM_IMAGE = "${PREFIX}IMAGE"
    const val PROJECT_LIST_ITEM_AGENCY_IMAGE = "${PREFIX}AGENCY_IMAGE"
    const val PROJECT_LIST_ITEM_PROJECT_NAME = "${PREFIX}PROJECT_NAME"
    const val PROJECT_LIST_ITEM_ADDRESS = "${PREFIX}ADDRESS"
    const val PROJECT_LIST_ITEM_CHILD_BUTTON = "${PREFIX}CHILD_BUTTON"
    const val PROJECT_LIST_ITEM_CHILD_COUNT = "${PREFIX}CHILD_COUNT"
    const val PROJECT_LIST_ITEM_CHILDREN = "${PREFIX}CHILDREN"

}

@Composable
@Preview
fun ProjectListItemPreview() {
    MviExampleTheme {
        ProjectListItem(
            position = 0,
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