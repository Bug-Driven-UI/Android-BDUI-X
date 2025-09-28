package ru.bugdrivenui.bduix.data.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import ru.bugdrivenui.bduix.data.model.RenderedScreenResponseModel
import ru.bugdrivenui.bduix.data.model.ScreenRenderRequestModel

interface BduiApi {

    @POST("v1/screen/render")
    suspend fun getRenderedScreen(
        @Header("userId") userId: String,
        @Body request: ScreenRenderRequestModel,
    ): Response<RenderedScreenResponseModel>
}