package au.com.deanpike.ui.screen.list

import au.com.deanpike.client.model.listing.response.Listing
import au.com.deanpike.client.type.ListingType
import au.com.deanpike.client.type.StatusType
import au.com.deanpike.uishared.base.ScreenStateType
import au.com.deanpike.uishared.base.UiEffect
import au.com.deanpike.uishared.base.UiEvent
import au.com.deanpike.uishared.base.UiState

data class ListingListScreenState(
    val screenState: ScreenStateType = ScreenStateType.INITIAL,
    val listings: List<Listing> = emptyList(),
    val selectedStatus: StatusType = StatusType.BUY,
    val selectedListingTypes: List<ListingType> = emptyList(),
    val showListingTypeScreen: Boolean = false
) : UiState

sealed class ListingListScreenEvent : UiEvent {
    data object Initialise : ListingListScreenEvent()
    data class OnStatusSelected(
        val status: StatusType
    ) : ListingListScreenEvent()

    data object OnListingTypeClicked : ListingListScreenEvent()
    data object OnBottomSheetDismissed : ListingListScreenEvent()
    data class OnListingTypesApplied(val selectedListingTypes: List<ListingType>) : ListingListScreenEvent()
}

sealed class ListingListScreenEffect : UiEffect {

}