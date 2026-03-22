package au.com.deanpike.detail.ui.provider

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import au.com.deanpike.detail.ui.project.ProjectDetailScreen
import au.com.deanpike.detail.ui.property.PropertyDetailScreen
import au.com.deanpike.detail.ui.shared.FullSizeImageComponent
import au.com.deanpike.navigation.keys.FullSizeImageComponentKey
import au.com.deanpike.navigation.keys.ProjectDetailScreenKey
import au.com.deanpike.navigation.keys.PropertyDetailScreenKey

fun EntryProviderScope<NavKey>.detailEntryBuilder(backStack: MutableList<NavKey>) {
    entry<PropertyDetailScreenKey> { key ->
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
    entry<ProjectDetailScreenKey> { key ->
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
}