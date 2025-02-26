package au.com.deanpike.detail.ui.property

import au.com.deanpike.detail.client.model.detail.PropertyDetail
import au.com.deanpike.uishared.base.ScreenStateType
import au.com.deanpike.uishared.base.UiEffect
import au.com.deanpike.uishared.base.UiEvent
import au.com.deanpike.uishared.base.UiState

data class PropertyDetailScreenState(
    val screenState: ScreenStateType = ScreenStateType.INITIAL,
    val propertyId: Long? = null,
    val propertyDetail: PropertyDetail? = null
) : UiState

sealed class PropertyDetailScreenEvent : UiEvent {
    data class Initialise(
        val propertyId: Long
    ) : PropertyDetailScreenEvent()

    data object OnRetryClicked : PropertyDetailScreenEvent()
}

sealed class PropertyDetailScreenEffect : UiEffect {

}