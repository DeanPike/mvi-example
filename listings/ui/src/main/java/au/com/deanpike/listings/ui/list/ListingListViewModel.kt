package au.com.deanpike.listings.ui.list

import androidx.lifecycle.viewModelScope
import au.com.deanpike.commonshared.util.ResponseWrapper
import au.com.deanpike.datashared.dispatcher.DispatcherProvider
import au.com.deanpike.listings.client.model.listing.response.Project
import au.com.deanpike.listings.client.model.listing.response.Property
import au.com.deanpike.listings.client.model.listing.search.ListingSearch
import au.com.deanpike.listings.client.type.StatusType
import au.com.deanpike.listings.client.usecase.ListingUseCase
import au.com.deanpike.uishared.base.BaseViewModel
import au.com.deanpike.uishared.base.ScreenStateType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class ListingListViewModel @Inject constructor(
    private val dispatcher: DispatcherProvider,
    private val listingUseCase: ListingUseCase
) : BaseViewModel<ListingListScreenEvent, ListingListScreenState, ListingListScreenEffect>() {
    override fun createInitialState() = ListingListScreenState()

    override fun handleEvent(event: ListingListScreenEvent) {
        when (event) {
            is ListingListScreenEvent.Initialise -> {
                initialise(event)
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
            is ListingListScreenEvent.OnPropertySelected -> {
                onPropertySelected(event)
            }
            is ListingListScreenEvent.OnProjectSelected -> {
                onProjectSelected(event)
            }
            is ListingListScreenEvent.OnProjectChildSelected -> {
                onProjectChildSelected(event)
            }
        }
    }

    private fun initialise(event: ListingListScreenEvent.Initialise) {
        setState {
            copy(
                screenState = ScreenStateType.LOADING,
                isSinglePane = event.isSinglePane
            )
        }
        getListings()
    }

    private fun onStatusSelected(status: StatusType) {
        if (status != uiState.selectedStatus) {
            setState {
                copy(
                    screenState = ScreenStateType.LOADING,
                    selectedStatus = status,
                    selectedPropertyId = null,
                    selectedProjectId = null
                )
            }
            getListings()
        }
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
        if (event.selectedListingTypes.count() != uiState.selectedListingTypes.count() ||
            !uiState.selectedListingTypes.containsAll(event.selectedListingTypes)
        ) {
            setState {
                copy(
                    showListingTypeScreen = false,
                    selectedListingTypes = event.selectedListingTypes,
                    screenState = ScreenStateType.LOADING,
                    selectedPropertyId = null,
                    selectedProjectId = null
                )
            }
            getListings()
        } else {
            setState {
                copy(
                    showListingTypeScreen = false
                )
            }
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

    private fun onPropertySelected(event: ListingListScreenEvent.OnPropertySelected) {
        setState {
            copy(
                selectedPropertyId = event.id,
                selectedProjectId = null,
                selectedProjectChild = null
            )
        }
        setEffect {
            ListingListScreenEffect.OnPropertySelected(
                id = event.id,
                address = event.address
            )
        }
    }

    private fun onProjectSelected(event: ListingListScreenEvent.OnProjectSelected) {
        setState {
            copy(
                selectedProjectId = event.id,
                selectedPropertyId = null,
                selectedProjectChild = null
            )
        }
        setEffect {
            ListingListScreenEffect.OnProjectSelected(event.id, event.address)
        }
    }

    private fun onProjectChildSelected(event: ListingListScreenEvent.OnProjectChildSelected) {
        setState {
            copy(
                selectedProjectId = event.projectId,
                selectedProjectChild = event.projectChildId,
                selectedPropertyId = null
            )
        }
        setEffect {
            ListingListScreenEffect.OnProjectChildSelected(
                projectId = event.projectId,
                projectChildId = event.projectChildId,
                address = event.address
            )
        }
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
                    if (!uiState.isSinglePane) {
                        if (response.data.isNotEmpty()) {
                            val listing = response.data[0]
                            if (listing is Property) {
                                onPropertySelected(
                                    event = ListingListScreenEvent.OnPropertySelected(
                                        id = listing.id,
                                        address = listing.address
                                    )
                                )
                            } else if (listing is Project) {
                                onProjectSelected(
                                    event = ListingListScreenEvent.OnProjectSelected(
                                        id = listing.id,
                                        address = listing.address
                                    )
                                )
                            }
                        }
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