package au.com.deanpike.detail.ui.property

import androidx.lifecycle.viewModelScope
import au.com.deanpike.commonshared.util.ResponseWrapper
import au.com.deanpike.datashared.dispatcher.DispatcherProvider
import au.com.deanpike.detail.client.usecase.PropertyDetailUseCase
import au.com.deanpike.uishared.base.BaseViewModel
import au.com.deanpike.uishared.base.ScreenStateType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class PropertyDetailViewModel @Inject constructor(
    private val dispatcher: DispatcherProvider,
    private val useCase: PropertyDetailUseCase
) : BaseViewModel<PropertyDetailScreenEvent, PropertyDetailScreenState, PropertyDetailScreenEffect>() {
    override fun createInitialState() = PropertyDetailScreenState()

    override fun handleEvent(event: PropertyDetailScreenEvent) {
        when (event) {
            is PropertyDetailScreenEvent.Initialise -> {
                initialiseScreen(event.propertyId)
            }
        }
    }

    private fun initialiseScreen(propertyId: Int) {
        viewModelScope.launch(dispatcher.getIoDispatcher()) {
            when (val response = useCase.getPropertyDetails(propertyId)) {
                is ResponseWrapper.Success -> {
                    setState {
                        copy(
                            propertyId = propertyId,
                            propertyDetail = response.data,
                            screenState = ScreenStateType.SUCCESS
                        )
                    }

                }
                is ResponseWrapper.Error -> {
                    setState {
                        copy(
                            propertyId = propertyId,
                            propertyDetail = null,
                            screenState = ScreenStateType.ERROR
                        )
                    }
                }
            }
        }
    }
}