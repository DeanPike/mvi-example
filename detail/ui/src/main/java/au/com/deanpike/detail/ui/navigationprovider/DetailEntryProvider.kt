package au.com.deanpike.detail.ui.navigationprovider

import androidx.compose.material3.Text
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import au.com.deanpike.detail.ui.project.ProjectDetailScreen
import au.com.deanpike.detail.ui.property.PropertyDetailScreen
import au.com.deanpike.detail.ui.shared.FullSizeImageComponent
import au.com.deanpike.navigation.extension.addDetail
import au.com.deanpike.navigation.keys.DefaultDetailScreenKey
import au.com.deanpike.navigation.keys.FullSizeImageComponentKey
import au.com.deanpike.navigation.keys.ProjectDetailScreenKey
import au.com.deanpike.navigation.keys.PropertyDetailScreenKey
import au.com.deanpike.navigation.scene.ListDetailSceneStrategy

fun EntryProviderScope<NavKey>.detailEntryBuilder(backStack: NavBackStack<NavKey>) {
    entry<PropertyDetailScreenKey>(
        metadata = ListDetailSceneStrategy.detailPane()
    ) { key ->
        PropertyDetailScreen(
            propertyId = key.propertyId,
            loadingAddress = key.address,
            onCloseClicked = {
                backStack.removeLastOrNull()
            },
            onImageClicked = { imageUrl: String ->
                backStack.add(FullSizeImageComponentKey(imageUrl))
            }
        )
    }
    entry<ProjectDetailScreenKey>(
        metadata = ListDetailSceneStrategy.detailPane()
    ) { key ->
        ProjectDetailScreen(
            projectId = key.projectId,
            loadingAddress = key.address,
            onCloseClicked = {
                backStack.removeLastOrNull()
            },
            onProjectChildClicked = { projectChildId ->
                backStack.add(
                    PropertyDetailScreenKey(
                        propertyId = projectChildId,
                        address = key.address
                    )
                )
            },
            onImageClicked = { imageUrl ->
                backStack.add(FullSizeImageComponentKey(imageUrl))
            }
        )
    }
    entry<FullSizeImageComponentKey> { key ->
        FullSizeImageComponent(
            url = key.imageUrl,
            onBackClicked = {
                backStack.removeLastOrNull()
            }
        )
    }
    entry<DefaultDetailScreenKey>(
        metadata = ListDetailSceneStrategy.detailPane()
    ) {
        Text(text = "Select a property or project to see details")
    }
}