package ru.bugdrivenui.bduix.data.repository

import kotlinx.coroutines.Dispatchers
import ru.bugdrivenui.bduix.data.api.BduiApi
import ru.bugdrivenui.bduix.data.model.RenderedScreenResponseModel
import ru.bugdrivenui.bduix.data.model.action.ScreenDoActionRequestModel
import ru.bugdrivenui.bduix.data.model.action.ScreenDoActionResponseModel
import ru.bugdrivenui.bduix.data.model.render.ScreenRenderRequestModel
import ru.bugdrivenui.bduix.data.utils.apiCall
import ru.bugdrivenui.bduix.data.utils.toResult
import ru.bugdrivenui.bduix.domain.repository.IBduiScreenRepository
import ru.bugdrivenui.bduix.domain.state.Result
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BduiScreenRepository @Inject constructor(
    private val bduiApi: BduiApi,
) : IBduiScreenRepository {

    override suspend fun getScreen(
        userId: String,
        request: ScreenRenderRequestModel
    ): Result<RenderedScreenResponseModel> {
        return apiCall(Dispatchers.IO) {
            bduiApi.getRenderedScreen(
                userId = userId,
                request = request,
            ).toResult()
        }
    }

    override suspend fun doAction(
        request: ScreenDoActionRequestModel,
    ): Result<ScreenDoActionResponseModel> {
        return apiCall(Dispatchers.IO) {
            bduiApi.doAction(request).toResult()
        }
    }
}