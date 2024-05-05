package au.com.deanpike.ui.screen.list

import au.com.deanpike.client.model.listing.response.Listing
import au.com.deanpike.uishared.base.ScreenStateType
import au.com.deanpike.uishared.base.UiEffect
import au.com.deanpike.uishared.base.UiEvent
import au.com.deanpike.uishared.base.UiState

data class ListingListScreenState(
    val screenState: ScreenStateType = ScreenStateType.INITIAL,
    val listings: List<Listing> = emptyList()
) : UiState

sealed class ListingListScreenEvent : UiEvent {
    data object Initialise : ListingListScreenEvent()

}

sealed class ListingListScreenEffect : UiEffect {

}