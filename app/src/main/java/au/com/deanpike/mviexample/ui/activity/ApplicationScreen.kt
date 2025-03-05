package au.com.deanpike.mviexample.ui.activity

import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.BackNavigationBehavior
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import au.com.deanpike.detail.ui.project.ProjectDetailScreen
import au.com.deanpike.detail.ui.property.PropertyDetailScreen
import au.com.deanpike.listings.ui.list.ListingListScreen
import au.com.deanpike.mviexample.ui.util.customPaneScaffoldDirective

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun ApplicationScreen() {
    val navigator = rememberListDetailPaneScaffoldNavigator<SelectedItem>(
        scaffoldDirective = customPaneScaffoldDirective(windowAdaptiveInfo = currentWindowAdaptiveInfo())
    )

    var refreshStatusBar by remember {
        mutableStateOf(false)
    }
    ListDetailPaneScaffold(
        directive = navigator.scaffoldDirective,
        value = navigator.scaffoldValue,
        listPane = {
            ListingListScreen(
                isSinglePane = navigator.scaffoldDirective.maxHorizontalPartitions == 1,
                refreshStatusBar = refreshStatusBar,
                onPropertyClicked = { propertyId ->
                    navigator.navigateTo(
                        pane = ListDetailPaneScaffoldRole.Detail,
                        content = SelectedItem(
                            propertyId = propertyId,
                            projectId = null,
                            listingType = SelectedListingType.PROPERTY
                        )
                    )
                },
                onProjectClicked = { projectId ->
                    navigator.navigateTo(
                        pane = ListDetailPaneScaffoldRole.Detail,
                        content = SelectedItem(
                            projectId = projectId,
                            propertyId = null,
                            listingType = SelectedListingType.PROJECT
                        )
                    )
                },
                onProjectChildClicked = { projectId, projectChildId ->
                    navigator.navigateTo(
                        pane = ListDetailPaneScaffoldRole.Detail,
                        content = SelectedItem(
                            projectId = projectId,
                            propertyId = projectChildId,
                            listingType = SelectedListingType.PROJECT_CHILD
                        )
                    )
                }
            )
        },
        detailPane = {
            navigator.currentDestination?.content?.let { item ->
                refreshStatusBar = false
                when (item.listingType) {
                    SelectedListingType.PROPERTY -> {
                        item.propertyId?.let { propertyId ->
                            PropertyDetailScreen(
                                propertyId = propertyId,
                                onCloseClicked = {
                                    navigator.navigateBack()
                                    refreshStatusBar = true
                                }
                            )
                        }
                    }
                    SelectedListingType.PROJECT -> {
                        item.projectId?.let { projectId ->
                            ProjectDetailScreen(
                                projectId = projectId,
                                onCloseClicked = {
                                    navigator.navigateBack()
                                    refreshStatusBar = true
                                },
                                onProjectChildClicked = { propertyId ->
                                    navigator.navigateTo(
                                        pane = ListDetailPaneScaffoldRole.Detail,
                                        content = SelectedItem(
                                            projectId = projectId,
                                            propertyId = propertyId,
                                            listingType = SelectedListingType.PROJECT_CHILD
                                        )
                                    )
                                }
                            )
                        }
                    }
                    SelectedListingType.PROJECT_CHILD -> {
                        item.propertyId?.let { propertyId ->
                            PropertyDetailScreen(
                                propertyId = propertyId,
                                onCloseClicked = {
                                    navigator.navigateBack(BackNavigationBehavior.PopLatest)
                                    refreshStatusBar = true
                                }
                            )
                        }
                    }
                }
            }
        }
    )
}

private enum class SelectedListingType {
    PROPERTY,
    PROJECT,
    PROJECT_CHILD
}

private class SelectedItem(
    val projectId: Long? = null,
    val propertyId: Long? = null,
    val listingType: SelectedListingType
)