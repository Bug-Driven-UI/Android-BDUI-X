package ru.bugdrivenui.bduix.data.repository

import android.util.Log
import kotlinx.coroutines.Dispatchers
import ru.bugdrivenui.bduix.data.api.BduiApi
import ru.bugdrivenui.bduix.data.store.ApiKeyStore
import ru.bugdrivenui.bduix.data.utils.apiCall
import ru.bugdrivenui.bduix.data.utils.toResult
import ru.bugdrivenui.bduix.domain.repository.ILoginRepository
import ru.bugdrivenui.bduix.domain.state.Result
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val api: BduiApi,
    private val apiKeyStore: ApiKeyStore,
) : ILoginRepository {

    override suspend fun isValidApiKey(apiKey: String): Result<Boolean> {
        return apiCall(Dispatchers.IO) {
            api.check(apiKey).toResult()
        }.also { result ->
            if (result is Result.Success && result.data) {
                runCatching { apiKeyStore.saveApiKey(apiKey) }
                    .onFailure { e -> Log.e("LoginRepository", e.message.orEmpty()) }
            }
        }
    }

    override suspend fun apiKeyExists(): Boolean {
        return apiKeyStore.getApiKey() != null
    }
}