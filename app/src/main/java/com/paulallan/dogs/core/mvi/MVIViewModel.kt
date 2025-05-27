package com.paulallan.dogs.core.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * Base ViewModel for MVI (Model-View-Intent) architecture.
 *
 * @param V The type representing the ViewState.
 * @param I The type representing the Intent.
 * @property initialState The initial state of the View.
 */
abstract class MVIViewModel<V : ViewState, I : Intent>(
    val initialState: V,
) : ViewModel() {

    // Backing state flow holding the current view state
    private val _state: MutableStateFlow<V> = MutableStateFlow(initialState)

    /**
     * Public state flow to observe the current view state.
     * Triggers [initialDataLoad] when first collected.
     */
    val state: StateFlow<V> by lazy {
        _state.onStart {
            // Load initial data when state is first observed
            viewModelScope.launch {
                initialDataLoad()
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Companion.WhileSubscribed(5000L),
            initialValue = initialState
        )
    }

    /**
     * Called when the state is first collected.
     * Override to perform initial data loading.
     */
    open suspend fun initialDataLoad() {}

    /**
     * Updates the current state using the provided reducer function.
     *
     * @param reducer Function that takes the current state and returns a new state.
     */
    protected fun updateState(reducer: (V) -> V) {
        _state.update { current -> reducer(current) }
    }
}
