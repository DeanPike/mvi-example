package au.com.deanpike.ui.screen.listingType

import au.com.deanpike.listings.client.type.ListingType
import au.com.deanpike.uishared.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class ListingTypeViewModel @Inject constructor() : BaseViewModel<ListingTypeEvent, ListingTypeState, ListingTypeEffect>() {
    override fun createInitialState() = ListingTypeState()

    override fun handleEvent(event: ListingTypeEvent) {
        when (event) {
            is ListingTypeEvent.Initialise -> {
                initialiseScreen(event.listingTypes)
            }
            is ListingTypeEvent.OnItemSelected -> {
                onItemSelected(event)
            }
            is ListingTypeEvent.OnApplyClicked -> {
                onApplyClicked()
            }
        }
    }

    private fun initialiseScreen(listingTypes: List<ListingType>) {
        setState {
            copy(
                selectedListingTypes = listingTypes
            )
        }
    }

    private fun onItemSelected(event: ListingTypeEvent.OnItemSelected) {
        if (event.listingType != ListingType.ALL) {
            val selectedListings = uiState.selectedListingTypes.toMutableList()
            if (event.selected) {
                selectedListings.add(event.listingType)
            } else {
                selectedListings.remove(event.listingType)
            }

            setState {
                copy(
                    selectedListingTypes = selectedListings.toList()
                )
            }
        } else {
            setState {
                copy(
                    selectedListingTypes = emptyList()
                )
            }
        }
    }

    private fun onApplyClicked() {
        setEffect {
            ListingTypeEffect.OnApplyClicked(uiState.selectedListingTypes)
        }
    }
}