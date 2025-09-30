package ru.bugdrivenui.bduix.domain.interactor

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.bugdrivenui.bduix.data.model.RenderedScreenResponseModel
import ru.bugdrivenui.bduix.data.model.action.ScreenDoActionRequestModel
import ru.bugdrivenui.bduix.data.model.action.ScreenDoActionResponseModel
import ru.bugdrivenui.bduix.data.model.render.ScreenRenderRequestModel
import ru.bugdrivenui.bduix.domain.repository.IBduiScreenRepository
import ru.bugdrivenui.bduix.domain.state.State
import ru.bugdrivenui.bduix.domain.state.toState
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BduiInteractor @Inject constructor(
    private val bduiScreenRepository: IBduiScreenRepository,
) {

    fun getScreen(
        request: ScreenRenderRequestModel,
    ): Flow<State<RenderedScreenResponseModel>> = flow {
        emit(State.Loading)

        val userId = UUID.randomUUID().toString()
        val screen = bduiScreenRepository.getScreen(
            userId = userId,
            request = request,
        ).toState()

        emit(screen)
    }

    fun doAction(
        request: ScreenDoActionRequestModel,
    ): Flow<State<ScreenDoActionResponseModel>> = flow {
        emit(State.Loading)

        val response = bduiScreenRepository.doAction(request).toState()

        emit(response)
    }
}