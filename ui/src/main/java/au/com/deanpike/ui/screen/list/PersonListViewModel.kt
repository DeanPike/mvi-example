package au.com.deanpike.ui.screen.list

import androidx.lifecycle.viewModelScope
import au.com.deanpike.client.usecase.PersonUseCase
import au.com.deanpike.uishared.base.BaseViewModel
import au.com.deanpike.uishared.dispatcher.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class PersonListViewModel @Inject constructor(
    private val dispatcher: DispatcherProvider,
    private val personUseCase: PersonUseCase
) : BaseViewModel<PersonListScreenEvent, PersonListScreenState, PersonListScreenEffect>() {
    override fun createInitialState() = PersonListScreenState()

    override fun handleEvent(event: PersonListScreenEvent) {
        when (event) {
            PersonListScreenEvent.Initialise -> {
                initialise()
            }
        }
    }

    private fun initialise() {
        viewModelScope.launch(dispatcher.getIoDispatcher()) {
            val people = personUseCase.getPeople()
            setState {
                copy(
                    people = people
                )
            }
        }
    }
}