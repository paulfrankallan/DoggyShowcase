package com.paulallan.dogs.app.presentation.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class MVIViewModel<V : ViewState, I : Intent>(
    val initialState: V,
) : ViewModel() {

    private val _state: MutableStateFlow<V> = MutableStateFlow(initialState)
    val state: StateFlow<V> by lazy {
        _state.onStart {
            viewModelScope.launch {
                initialDataLoad()
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = initialState
        )
    }

    open suspend fun initialDataLoad() {}

    protected fun updateState(reducer: (V) -> V) {
        _state.update { current -> reducer(current) }
    }
}
