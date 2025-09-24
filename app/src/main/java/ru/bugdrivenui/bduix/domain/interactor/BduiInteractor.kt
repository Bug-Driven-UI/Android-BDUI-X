package ru.bugdrivenui.bduix.domain.interactor

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.bugdrivenui.bduix.data.model.BduiScreenResponse
import ru.bugdrivenui.bduix.domain.repository.IBduiScreenRepository
import ru.bugdrivenui.bduix.domain.state.State
import ru.bugdrivenui.bduix.domain.state.toState
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BduiInteractor @Inject constructor(
    private val bduiScreenRepository: IBduiScreenRepository,
) {

    fun getScreen(/* params */): Flow<State<BduiScreenResponse>> = flow {
        emit(State.Loading)
//        emit(bduiScreenRepository.getScreen().toState())
    }
}