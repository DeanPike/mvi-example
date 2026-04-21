package au.com.deanpike.listings.ui.navigationprovider

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import au.com.deanpike.listings.ui.list.ListingListScreen
import au.com.deanpike.navigation.extension.addDetail
import au.com.deanpike.navigation.keys.ListingScreenKey
import au.com.deanpike.navigation.keys.ProjectDetailScreenKey
import au.com.deanpike.navigation.keys.PropertyDetailScreenKey
import au.com.deanpike.navigation.scene.ListDetailSceneStrategy

fun EntryProviderScope<NavKey>.listingEntryBuilder(backStack: NavBackStack<NavKey>) {
    entry<ListingScreenKey>(
        metadata = ListDetailSceneStrategy.listPane()
    ) {
        ListingListScreen(
            onPropertyClicked = { propertyId, address ->
                backStack.add(PropertyDetailScreenKey(propertyId, address))
            },
            onProjectClicked = { projectId, address ->
                backStack.add(ProjectDetailScreenKey(projectId, address))
            }
        )
    }
}