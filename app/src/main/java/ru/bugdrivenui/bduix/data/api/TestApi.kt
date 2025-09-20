package ru.bugdrivenui.bduix.data.api

import retrofit2.Response
import retrofit2.http.GET
import ru.bugdrivenui.bduix.data.model.TestResponse

private const val TEST_ENDPOINT = "test"

interface TestApi {

    @GET(TEST_ENDPOINT)
    suspend fun getTestData(): Response<TestResponse>
}