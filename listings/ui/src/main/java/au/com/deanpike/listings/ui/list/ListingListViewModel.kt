package au.com.deanpike.listings.ui.list

import androidx.lifecycle.viewModelScope
import au.com.deanpike.commonshared.util.ResponseWrapper
import au.com.deanpike.datashared.dispatcher.DispatcherProvider
import au.com.deanpike.listings.client.model.listing.search.ListingSearch
import au.com.deanpike.listings.client.type.StatusType
import au.com.deanpike.listings.client.usecase.ListingUseCase
import au.com.deanpike.uishared.base.BaseViewModel
import au.com.deanpike.uishared.base.ScreenStateType
import au.com.deanpike.uishared.base.UiEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class ListingListViewModel @Inject constructor(
    private val dispatcher: DispatcherProvider,
    private val listingUseCase: ListingUseCase
) : BaseViewModel<ListingListScreenEvent, ListingListScreenState, UiEffect>() {
    override fun createInitialState() = ListingListScreenState()

    override fun handleEvent(event: ListingListScreenEvent) {
        when (event) {
            is ListingListScreenEvent.Initialise -> {
                initialise()
            }

            is ListingListScreenEvent.OnFilterApplied -> {
                onFilterChanged(event)
            }
            is ListingListScreenEvent.OnRetryClicked -> {
                onRetryClicked()
            }

            is ListingListScreenEvent.OnPropertySelected -> {
            }

            is ListingListScreenEvent.OnProjectSelected -> {
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
        if (status != uiState.selectedStatus) {
            setState {
                copy(
                    screenState = ScreenStateType.LOADING,
                    selectedStatus = status
                )
            }
            getListings()
        }
    }

    private fun onFilterChanged(event: ListingListScreenEvent.OnFilterApplied) {
        if (uiState.selectedStatus != event.status || uiState.selectedDwellingTypes != event.dwellingTypes) {
            setState {
                copy(
                    selectedDwellingTypes = event.dwellingTypes,
                    selectedStatus = event.status,
                    screenState = ScreenStateType.LOADING
                )
            }
            getListings()
        }
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
                    dwellingTypes = uiState.selectedDwellingTypes
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