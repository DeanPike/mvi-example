package au.com.deanpike.mvi_example.ui.base

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<Event : UiEvent, State : UiState, Effect : UiEffect> : ViewModel() {
    private val initialState: State by lazy { createInitialState() }
    abstract fun createInitialState(): State
    abstract fun handleEvent(event: Event)

    var uiState by mutableStateOf(initialState)
        private set

    private val _event: MutableSharedFlow<Event> = MutableSharedFlow()
    val event = _event.asSharedFlow()

    private val _effect: Channel<Effect> = Channel()
    val effect = _effect.receiveAsFlow()

    init {
        subscribeEvents()
    }

    override fun onCleared() {
        super.onCleared()
        _effect.close()
    }

    protected fun setState(reduce: State.() -> State) {
        uiState = uiState.reduce()
    }

    @ExperimentalCoroutinesApi
    protected fun setEffect(builder: () -> Effect) {
        if (!_effect.isClosedForSend) {
            val effectValue = builder()
            viewModelScope.launch { _effect.send(effectValue) }
        }
    }

    fun setEvent(event: Event) {
        val newEvent = event
        viewModelScope.launch { _event.emit(newEvent) }
    }

    private fun subscribeEvents() {
        viewModelScope.launch {
            event.collect {
                handleEvent(it)
            }
        }
    }
}

interface UiEffect

interface UiEvent

interface UiState