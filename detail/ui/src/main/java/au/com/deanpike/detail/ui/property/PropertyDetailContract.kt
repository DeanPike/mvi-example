package au.com.deanpike.detail.ui.property

import au.com.deanpike.detail.client.model.detail.PropertyDetail
import au.com.deanpike.uishared.base.ScreenStateType
import au.com.deanpike.uishared.base.UiEffect
import au.com.deanpike.uishared.base.UiEvent
import au.com.deanpike.uishared.base.UiState

data class PropertyDetailScreenState(
    val screenState: ScreenStateType = ScreenStateType.INITIAL,
    val propertyId: Int? = null,
    val propertyDetail: PropertyDetail? = null
) : UiState

sealed class PropertyDetailScreenEvent : UiEvent {
    data class Initialise(
        val propertyId: Int
    ) : PropertyDetailScreenEvent()
}

sealed class PropertyDetailScreenEffect : UiEffect {

}