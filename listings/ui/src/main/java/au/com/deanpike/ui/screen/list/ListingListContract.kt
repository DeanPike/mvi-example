package au.com.deanpike.ui.screen.list

import au.com.deanpike.listings.client.model.listing.response.Listing
import au.com.deanpike.listings.client.type.DwellingType
import au.com.deanpike.listings.client.type.StatusType
import au.com.deanpike.uishared.base.ScreenStateType
import au.com.deanpike.uishared.base.UiEffect
import au.com.deanpike.uishared.base.UiEvent
import au.com.deanpike.uishared.base.UiState

data class ListingListScreenState(
    val screenState: ScreenStateType = ScreenStateType.INITIAL,
    val listings: List<Listing> = emptyList(),
    val selectedStatus: StatusType = StatusType.BUY,
    val selectedListingTypes: List<DwellingType> = emptyList(),
    val showListingTypeScreen: Boolean = false,
    val selectedPropertyId: Long? = null,
    val selectedProjectId: Long? = null,
    val selectedProjectChild: Long? = null,
    val isSinglePane: Boolean = true
) : UiState

sealed class ListingListScreenEvent : UiEvent {
    data class Initialise(
        val isSinglePane: Boolean
    ) : ListingListScreenEvent()

    data class OnStatusSelected(
        val status: StatusType
    ) : ListingListScreenEvent()

    data object OnListingTypeClicked : ListingListScreenEvent()
    data object OnBottomSheetDismissed : ListingListScreenEvent()
    data class OnListingTypesApplied(val selectedListingTypes: List<DwellingType>) : ListingListScreenEvent()
    data object OnRetryClicked : ListingListScreenEvent()
    data class OnPropertySelected(val id: Long) : ListingListScreenEvent()
    data class OnProjectSelected(val id: Long) : ListingListScreenEvent()
    data class OnProjectChildSelected(
        val projectId: Long,
        val projectChildId: Long
    ) : ListingListScreenEvent()
}

sealed class ListingListScreenEffect : UiEffect {
    data class OnPropertySelected(val id: Long) : ListingListScreenEffect()
    data class OnProjectSelected(val id: Long) : ListingListScreenEffect()
    data class OnProjectChildSelected(
        val projectId: Long,
        val projectChildId: Long,
    ) : ListingListScreenEffect()
}