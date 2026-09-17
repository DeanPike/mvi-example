package au.com.deanpike.listings.ui.search

import androidx.lifecycle.viewModelScope
import au.com.deanpike.commonshared.util.ResponseWrapper
import au.com.deanpike.datashared.dispatcher.DispatcherProvider
import au.com.deanpike.listings.client.usecase.SuggestedLocationsUseCase
import au.com.deanpike.uishared.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

private const val LOCATION_SEARCH_DEBOUNCE_MS = 500L

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    dispatcher: DispatcherProvider,
    private val suggestedLocationsUseCase: SuggestedLocationsUseCase
) : BaseViewModel<SearchScreenEvent, SearchScreenState, SearchScreenEffect>() {

    private val locationQuery = MutableSharedFlow<String>(extraBufferCapacity = 1)

    init {
        locationQuery
            .debounce(LOCATION_SEARCH_DEBOUNCE_MS.milliseconds)
            .distinctUntilChanged()
            .filter { it.isNotBlank() }
            .onEach { fetchSuggestedLocations(it) }
            .flowOn(dispatcher.getIoDispatcher())
            .launchIn(viewModelScope)
    }

    override fun createInitialState() = SearchScreenState()

    override fun handleEvent(event: SearchScreenEvent) {
        when (event) {
            is SearchScreenEvent.OnLocationChanged -> {
                onLocationChanged(event.location)
            }

            is SearchScreenEvent.OnLocationSelected -> {
                setState {
                    copy(
                        location = event.location,
                        suggestedLocations = emptyList()
                    )
                }
            }

            is SearchScreenEvent.OnStatusChanged -> {
                setState {
                    copy(selectedStatus = event.status)
                }
            }

            is SearchScreenEvent.OnDwellingTypesChanged -> {
                setState {
                    copy(selectedDwellingTypes = event.dwellingTypes)
                }
            }

            is SearchScreenEvent.OnSearchClicked -> {
                onSearchClicked()
            }
        }
    }

    private fun onLocationChanged(location: String) {
        setState {
            copy(location = location)
        }

        if (location.isBlank()) {
            setState {
                copy(suggestedLocations = emptyList())
            }
            return
        }

        locationQuery.tryEmit(location)
    }

    private suspend fun fetchSuggestedLocations(location: String) {
        when (val response = suggestedLocationsUseCase.getSuggestedLocations(location)) {
            is ResponseWrapper.Success -> {
                setState {
                    copy(suggestedLocations = response.data)
                }
            }

            is ResponseWrapper.Error -> {
                setState {
                    copy(suggestedLocations = emptyList())
                }
            }
        }
    }

    private fun onSearchClicked() {
        setEffect {
            SearchScreenEffect.OnSearchRequested(
                location = uiState.location,
                status = uiState.selectedStatus,
                dwellingTypes = uiState.selectedDwellingTypes
            )
        }
    }
}
