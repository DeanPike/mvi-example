package au.com.deanpike.ui.screen.list

import androidx.lifecycle.viewModelScope
import au.com.deanpike.client.model.listing.search.ListingSearch
import au.com.deanpike.client.type.StatusType
import au.com.deanpike.client.usecase.ListingUseCase
import au.com.deanpike.client.util.ResponseWrapper
import au.com.deanpike.datashared.dispatcher.DispatcherProvider
import au.com.deanpike.uishared.base.BaseViewModel
import au.com.deanpike.uishared.base.ScreenStateType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class ListingListViewModel @Inject constructor(
    private val dispatcher: DispatcherProvider,
    private val listingUseCase: ListingUseCase
) : BaseViewModel<ListingListScreenEvent, ListingListScreenState, ListingListScreenEffect>() {
    override fun createInitialState() = ListingListScreenState()

    override fun handleEvent(event: ListingListScreenEvent) {
        when (event) {
            is ListingListScreenEvent.Initialise -> {
                initialise()
            }
            is ListingListScreenEvent.OnStatusSelected -> {
                onStatusSelected(event.status)
            }
            is ListingListScreenEvent.OnListingTypeClicked -> {
                onListingTypeClicked()
            }
            is ListingListScreenEvent.OnBottomSheetDismissed -> {
                onBottomSheetDismissed()
            }
            is ListingListScreenEvent.OnListingTypesApplied -> {
                onListingTypesApplied(event)
            }
            is ListingListScreenEvent.OnRetryClicked -> {
                onRetryClicked()
            }
        }
    }

    private fun initialise() {
        setState {
            copy(
                screenState = ScreenStateType.LOADING
            )
        }
        getListings()
    }

    private fun onStatusSelected(status: StatusType) {
        setState {
            copy(
                screenState = ScreenStateType.LOADING,
                selectedStatus = status
            )
        }
        getListings()
    }

    private fun onListingTypeClicked() {
        setState {
            copy(
                showListingTypeScreen = true
            )
        }
    }

    private fun onBottomSheetDismissed() {
        setState {
            copy(
                showListingTypeScreen = false
            )
        }
    }

    private fun onListingTypesApplied(event: ListingListScreenEvent.OnListingTypesApplied) {
        setState {
            copy(
                showListingTypeScreen = false,
                selectedListingTypes = event.selectedListingTypes,
                screenState = ScreenStateType.LOADING
            )
        }
        getListings()
    }

    private fun onRetryClicked() {
        setState {
            copy(
                screenState = ScreenStateType.REFRESHING
            )
        }

        getListings()
    }

    private fun getListings() {
        viewModelScope.launch(dispatcher.getIoDispatcher()) {
            when (val response = listingUseCase.getListings(
                ListingSearch(
                    searchMode = uiState.selectedStatus,
                    dwellingTypes = uiState.selectedListingTypes
                )
            )) {
                is ResponseWrapper.Success -> {
                    setState {
                        copy(
                            screenState = ScreenStateType.SUCCESS,
                            listings = response.data
                        )
                    }
                }
                is ResponseWrapper.Error -> {
                    setState {
                        copy(
                            screenState = ScreenStateType.ERROR,
                            listings = emptyList()
                        )
                    }
                }
            }
        }
    }
}