package ru.bugdrivenui.bduix.domain.repository

import ru.bugdrivenui.bduix.domain.state.Result

interface ILoginRepository {

    suspend fun isValidApiKey(apiKey: String): Result<Boolean>

    suspend fun apiKeyExists(): Boolean
}