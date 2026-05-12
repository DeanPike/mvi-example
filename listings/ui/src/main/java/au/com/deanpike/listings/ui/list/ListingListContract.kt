package au.com.deanpike.listings.ui.list

import au.com.deanpike.listings.client.model.listing.response.Listing
import au.com.deanpike.listings.client.type.DwellingType
import au.com.deanpike.listings.client.type.StatusType
import au.com.deanpike.uishared.base.ScreenStateType
import au.com.deanpike.uishared.base.UiEvent
import au.com.deanpike.uishared.base.UiState

data class ListingListScreenState(
    val screenState: ScreenStateType = ScreenStateType.INITIAL,
    val listings: List<Listing> = emptyList(),
    val selectedStatus: StatusType = StatusType.BUY,
    val selectedDwellingTypes: List<DwellingType> = listOf(DwellingType.ALL),
) : UiState

sealed class ListingListScreenEvent : UiEvent {
    data object Initialise : ListingListScreenEvent()

    data class OnFilterApplied(
        val status: StatusType,
        val dwellingTypes: List<DwellingType>
    ) : ListingListScreenEvent()

    data object OnRetryClicked : ListingListScreenEvent()
    data class OnPropertySelected(
        val id: Long,
        val address: String
    ) : ListingListScreenEvent()

    data class OnProjectSelected(
        val id: Long,
        val address: String
    ) : ListingListScreenEvent()
}