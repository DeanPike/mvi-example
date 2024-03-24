package au.com.deanpike.ui.screen.list

import au.com.deanpike.client.model.PersonDTO
import au.com.deanpike.uishared.base.ScreenStateType
import au.com.deanpike.uishared.base.UiEffect
import au.com.deanpike.uishared.base.UiEvent
import au.com.deanpike.uishared.base.UiState

data class PersonListScreenState(
    val screenState: ScreenStateType = ScreenStateType.INITIAL,
    val people: List<PersonDTO> = emptyList()
) : UiState

sealed class PersonListScreenEvent : UiEvent {
    data object Initialise : PersonListScreenEvent()

}

sealed class PersonListScreenEffect : UiEffect {

}