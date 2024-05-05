package au.com.deanpike.ui.screen.list

import androidx.lifecycle.viewModelScope
import au.com.deanpike.client.model.listing.search.ListingSearch
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
            ListingListScreenEvent.Initialise -> {
                initialise()
            }
        }
    }

    private fun initialise() {
        viewModelScope.launch(dispatcher.getIoDispatcher()) {
            setState {
                copy(
                    screenState = ScreenStateType.LOADING
                )
            }
            when (val response = listingUseCase.getListings(
                ListingSearch(
                    searchMode = "Buy",
                    dwellingTypes = listOf("House")
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