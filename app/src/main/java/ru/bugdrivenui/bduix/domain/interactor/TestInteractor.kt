package ru.bugdrivenui.bduix.domain.interactor

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.bugdrivenui.bduix.domain.model.TestModel
import ru.bugdrivenui.bduix.domain.repository.ITestRepository
import ru.bugdrivenui.bduix.domain.state.State
import ru.bugdrivenui.bduix.domain.state.toState
import javax.inject.Inject

class TestInteractor @Inject constructor(
    private val testRepository: ITestRepository,
) {
    fun getTestData(): Flow<State<TestModel>> = flow {
        emit(State.Loading)
        emit(testRepository.getTestData().toState())
    }
}