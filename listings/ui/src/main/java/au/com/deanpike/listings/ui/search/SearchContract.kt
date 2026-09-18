package au.com.deanpike.listings.ui.search

import au.com.deanpike.listings.client.model.suggestedlocation.Location
import au.com.deanpike.listings.client.type.DwellingType
import au.com.deanpike.listings.client.type.StatusType
import au.com.deanpike.uishared.base.UiEffect
import au.com.deanpike.uishared.base.UiEvent
import au.com.deanpike.uishared.base.UiState

data class SearchScreenState(
    val selectedLocation: Location? = null,
    val suggestedLocations: List<Location> = emptyList(),
    val selectedStatus: StatusType = StatusType.BUY,
    val selectedDwellingTypes: List<DwellingType> = listOf(DwellingType.ALL)
) : UiState

sealed class SearchScreenEvent : UiEvent {
    data class OnLocationChanged(val location: String) : SearchScreenEvent()
    data class OnLocationSelected(val location: Location) : SearchScreenEvent()
    data class OnStatusChanged(val status: StatusType) : SearchScreenEvent()
    data class OnDwellingTypesChanged(val dwellingTypes: List<DwellingType>) : SearchScreenEvent()
    data object OnSearchClicked : SearchScreenEvent()
}

sealed class SearchScreenEffect : UiEffect {
    data class OnSearchRequested(
        val location: Location?,
        val status: StatusType,
        val dwellingTypes: List<DwellingType>
    ) : SearchScreenEffect()
}
