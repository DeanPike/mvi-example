package au.com.deanpike.mviexample.ui.activity

import android.os.Parcelable
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.BackNavigationBehavior
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.IntOffset
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import au.com.deanpike.detail.ui.project.ProjectDetailScreen
import au.com.deanpike.detail.ui.property.PropertyDetailScreen
import au.com.deanpike.listings.ui.list.ListingListScreen
import au.com.deanpike.listings.ui.list.ListingListScreenEvent
import au.com.deanpike.listings.ui.list.ListingListViewModel
import au.com.deanpike.mviexample.ui.util.customPaneScaffoldDirective
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun ApplicationScreen() {
    val scope = rememberCoroutineScope()
    val navigator = rememberListDetailPaneScaffoldNavigator<SelectedItem>(
        scaffoldDirective = customPaneScaffoldDirective(windowAdaptiveInfo = currentWindowAdaptiveInfo())
    )

    var isSinglePane by remember {
        mutableStateOf(true)
    }

    LaunchedEffect(navigator) {
        isSinglePane = navigator.scaffoldDirective.maxHorizontalPartitions == 1
    }

    // Create the ViewModel at this level so it survives navigation
    val listingListViewModel: ListingListViewModel = hiltViewModel<ListingListViewModel>()
    LaunchedEffect(Unit) {
        listingListViewModel.setEvent(
            ListingListScreenEvent.Initialise(
                isSinglePane = isSinglePane
            )
        )
    }

    ListDetailPaneScaffold(
        directive = navigator.scaffoldDirective,
        value = navigator.scaffoldValue,
        listPane = {
            AnimatedPane(
                enterTransition = slideInFromRight(),
                exitTransition = slideOutToRight()
            ) {
                ListingListScreen(
                    viewModel = listingListViewModel,
                    onPropertyClicked = { propertyId, address ->
                        scope.launch {
                            navigator.navigateTo(
                                pane = ListDetailPaneScaffoldRole.Detail,
                                contentKey = SelectedItem(
                                    propertyId = propertyId,
                                    projectId = null,
                                    listingType = SelectedListingType.PROPERTY,
                                    address = address
                                )
                            )
                        }
                    },
                    onProjectClicked = { projectId, address ->
                        scope.launch {
                            navigator.navigateTo(
                                pane = ListDetailPaneScaffoldRole.Detail,
                                contentKey = SelectedItem(
                                    projectId = projectId,
                                    propertyId = null,
                                    listingType = SelectedListingType.PROJECT,
                                    address = address
                                )
                            )
                        }
                    },
                    onProjectChildClicked = { projectId, projectChildId, address ->
                        scope.launch {
                            navigator.navigateTo(
                                pane = ListDetailPaneScaffoldRole.Detail,
                                contentKey = SelectedItem(
                                    projectId = projectId,
                                    propertyId = projectChildId,
                                    listingType = SelectedListingType.PROJECT_CHILD,
                                    address = address
                                )
                            )
                        }
                    }
                )
            }
        },
        detailPane = {
            navigator.currentDestination?.contentKey?.let { item ->
                when (item.listingType) {
                    SelectedListingType.PROPERTY -> {
                        item.propertyId?.let { propertyId ->
                            AnimatedPane(
                                enterTransition = slideInFromLeft(),
                                exitTransition = slideOutToLeft()
                            ) {
                                PropertyDetailScreen(
                                    isSinglePane = isSinglePane,
                                    propertyId = propertyId,
                                    loadingAddress = item.address,
                                    onCloseClicked = {
                                        if (navigator.canNavigateBack()) {
                                            scope.launch {
                                                navigator.navigateBack()
                                            }
                                        }
                                    }
                                )
                            }
                        }
                    }
                    SelectedListingType.PROJECT -> {
                        item.projectId?.let { projectId ->
                            AnimatedPane(
                                enterTransition = slideInFromLeft(),
                                exitTransition = slideOutToLeft()
                            ) {
                                ProjectDetailScreen(
                                    isSinglePane = isSinglePane,
                                    projectId = projectId,
                                    loadingAddress = item.address,
                                    onCloseClicked = {
                                        if (navigator.canNavigateBack()) {
                                            scope.launch {
                                                navigator.navigateBack()
                                            }
                                        }
                                    },
                                    onProjectChildClicked = { propertyId ->
                                        scope.launch {
                                            navigator.navigateTo(
                                                pane = ListDetailPaneScaffoldRole.Detail,
                                                contentKey = SelectedItem(
                                                    projectId = projectId,
                                                    propertyId = propertyId,
                                                    listingType = SelectedListingType.PROJECT_CHILD,
                                                    address = item.address
                                                )
                                            )
                                        }
                                    }
                                )
                            }
                        }
                    }
                    SelectedListingType.PROJECT_CHILD -> {
                        item.propertyId?.let { propertyId ->
                            AnimatedPane(
                                enterTransition = slideInFromLeft(),
                                exitTransition = slideOutToLeft()
                            ) {
                                PropertyDetailScreen(
                                    isSinglePane = isSinglePane,
                                    propertyId = propertyId,
                                    loadingAddress = item.address,
                                    onCloseClicked = {
                                        scope.launch {
                                            navigator.navigateBack(BackNavigationBehavior.PopLatest)
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}

fun slideInFromLeft(): EnterTransition {
    return slideIn(initialOffset = { size ->
        IntOffset(x = -size.width, y = 0)
    })
}

fun slideInFromRight(): EnterTransition {
    return slideIn(initialOffset = { size ->
        IntOffset(x = size.width, y = 0)
    })
}

fun slideOutToLeft(): ExitTransition {
    return slideOut(targetOffset = { size ->
        IntOffset(x = 0, y = 0)
    })
}

fun slideOutToRight(): ExitTransition {
    return slideOut(targetOffset = { size ->
        IntOffset(x = 0, y = 0)
    })
}


private enum class SelectedListingType {
    PROPERTY,
    PROJECT,
    PROJECT_CHILD
}

@Parcelize
private class SelectedItem(
    val projectId: Long? = null,
    val propertyId: Long? = null,
    val listingType: SelectedListingType,
    val address: String
) : Parcelable