package au.com.deanpike.listings.ui.navigationprovider

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import au.com.deanpike.listings.client.type.DwellingType
import au.com.deanpike.listings.client.type.StatusType
import au.com.deanpike.listings.ui.list.ListingListScreen
import au.com.deanpike.listings.ui.search.SearchScreen
import au.com.deanpike.navigation.keys.ListingScreenKey
import au.com.deanpike.navigation.keys.ProjectDetailScreenKey
import au.com.deanpike.navigation.keys.PropertyDetailScreenKey
import au.com.deanpike.navigation.keys.SearchScreenKey
import au.com.deanpike.navigation.scene.ListDetailSceneStrategy

fun EntryProviderScope<NavKey>.listingEntryBuilder(backStack: NavBackStack<NavKey>) {
    entry<SearchScreenKey> {
        SearchScreen(
            onSearch = { location, status, dwellingTypes ->
                backStack.add(
                    ListingScreenKey(
                        location = location,
                        status = status.name,
                        dwellingTypes = dwellingTypes.map { it.name }
                    )
                )
            }
        )
    }
    entry<ListingScreenKey>(
        metadata = ListDetailSceneStrategy.listPane()
    ) { key ->
        ListingListScreen(
            location = key.location,
            status = StatusType.valueOf(key.status),
            dwellingTypes = key.dwellingTypes.map { DwellingType.valueOf(it) },
            onPropertyClicked = { propertyId, address ->
                backStack.add(PropertyDetailScreenKey(propertyId, address))
            },
            onProjectClicked = { projectId, address ->
                backStack.add(ProjectDetailScreenKey(projectId, address))
            }
        )
    }
}