package ru.bugdrivenui.bduix.domain.repository

import ru.bugdrivenui.bduix.data.model.RenderedScreenResponseModel
import ru.bugdrivenui.bduix.data.model.ScreenRenderRequestModel
import ru.bugdrivenui.bduix.domain.state.Result

interface IBduiScreenRepository {

    suspend fun getScreen(
        userId: String,
        request: ScreenRenderRequestModel
    ): Result<RenderedScreenResponseModel>
}