package au.com.deanpike.ui.screen.listingType

import au.com.deanpike.listings.client.type.DwellingType
import au.com.deanpike.uishared.base.ScreenStateType
import au.com.deanpike.uishared.base.UiEffect
import au.com.deanpike.uishared.base.UiEvent
import au.com.deanpike.uishared.base.UiState

data class ListingTypeState(
    val screenState: ScreenStateType = ScreenStateType.INITIAL,
    val selectedListingTypes: List<DwellingType> = emptyList()
) : UiState

sealed class ListingTypeEvent : UiEvent {
    data class Initialise(val listingTypes: List<DwellingType>) : ListingTypeEvent()
    data class OnItemSelected(
        val listingType: DwellingType,
        val selected: Boolean
    ) : ListingTypeEvent()

    data object OnApplyClicked : ListingTypeEvent()
}

sealed class ListingTypeEffect : UiEffect {
    data class OnApplyClicked(val listingTypes: List<DwellingType>) : ListingTypeEffect()
}