package ru.bugdrivenui.bduix.domain.repository

import ru.bugdrivenui.bduix.data.model.RenderedScreenResponseModel
import ru.bugdrivenui.bduix.data.model.action.ScreenDoActionRequestModel
import ru.bugdrivenui.bduix.data.model.action.ScreenDoActionResponseModel
import ru.bugdrivenui.bduix.data.model.render.ScreenRenderRequestModel
import ru.bugdrivenui.bduix.domain.state.Result

interface IBduiScreenRepository {

    suspend fun getScreen(
        userId: String,
        request: ScreenRenderRequestModel
    ): Result<RenderedScreenResponseModel>

    suspend fun doAction(
        request: ScreenDoActionRequestModel,
    ): Result<ScreenDoActionResponseModel>
}