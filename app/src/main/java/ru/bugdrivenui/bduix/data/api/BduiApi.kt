package ru.bugdrivenui.bduix.data.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import ru.bugdrivenui.bduix.data.model.RenderedScreenResponseModel
import ru.bugdrivenui.bduix.data.model.action.ScreenDoActionRequestModel
import ru.bugdrivenui.bduix.data.model.action.ScreenDoActionResponseModel
import ru.bugdrivenui.bduix.data.model.render.ScreenRenderRequestModel

interface BduiApi {

    @POST("api/v1/screen/render")
    suspend fun getRenderedScreen(
        @Header("Authorization") apiKey: String,
        @Header("userId") userId: String,
        @Body request: ScreenRenderRequestModel,
    ): Response<RenderedScreenResponseModel>

    @POST("api/v1/screen/action")
    suspend fun doAction(
        @Header("Authorization") apiKey: String,
        @Body request: ScreenDoActionRequestModel,
    ): Response<ScreenDoActionResponseModel>

    @GET("api/v1/check")
    suspend fun check(
        @Header("Authorization") apiKey: String,
    ): Response<Boolean>
}