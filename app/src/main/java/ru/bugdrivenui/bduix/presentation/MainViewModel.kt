package ru.bugdrivenui.bduix.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.bugdrivenui.bduix.domain.interactor.TestInteractor
import ru.bugdrivenui.bduix.domain.state.State
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val testInteractor: TestInteractor,
) : ViewModel() {

    init {
        viewModelScope.launch {
            testInteractor.getTestData().collect { state ->
                when (state) {
                    is State.Error -> Log.d("MainViewModel", "Error: ${state.throwable}")
                    State.Loading -> Log.d("MainViewModel", "Loading")
                    is State.Success<*> -> Log.d("MainViewModel", "Success: ${state.data}" )
                }
            }
        }
    }
}