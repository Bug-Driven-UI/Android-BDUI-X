package ru.bugdrivenui.bduix.data.store

import android.content.Context
import android.util.Base64
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStoreFile
import com.google.crypto.tink.Aead
import com.google.crypto.tink.aead.AeadConfig
import com.google.crypto.tink.aead.AesGcmKeyManager
import com.google.crypto.tink.integration.android.AndroidKeysetManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.getValue

@Singleton
class ApiKeyStore @Inject constructor(
    @ApplicationContext context: Context,
) {

    companion object {
        private const val DATASTORE_FILE = "secure_prefs.preferences_pb"
        private const val KEYSET_PREF = "secure_keyset_prefs"
        private const val KEYSET_NAME = "secure_keyset"
        private const val MASTER_KEY_URI = "android-keystore://secure_master_key"
        private val API_KEY_PREFS_KEY = stringPreferencesKey("api_key")
    }

    private val aead: Aead? by lazy {
        try {
            AeadConfig.register()
            val keysetManager = AndroidKeysetManager.Builder()
                .withSharedPref(context, KEYSET_NAME, KEYSET_PREF)
                .withKeyTemplate(AesGcmKeyManager.aes256GcmTemplate())
                .withMasterKeyUri(MASTER_KEY_URI)
                .build()

            keysetManager.keysetHandle.getPrimitive(Aead::class.java)
        } catch (e: Exception) {
            Log.e("ApiKeyStore", e.message.orEmpty())
            null
        }
    }

    private val dataStore: DataStore<Preferences> =
        PreferenceDataStoreFactory.create(
            produceFile = { context.preferencesDataStoreFile(DATASTORE_FILE) }
        )

    val tokenFlow: Flow<String?> =
        dataStore.data
            .catch { e ->
                if (e is IOException) emit(emptyPreferences()) else throw e
            }
            .map { prefs ->
                val b64 = prefs[API_KEY_PREFS_KEY] ?: return@map null
                val aead = aead ?: return@map null
                try {
                    val ciphertext = Base64.decode(b64, Base64.NO_WRAP)
                    val plaintext = aead.decrypt(ciphertext, null)
                    String(plaintext, Charsets.UTF_8)
                } catch (e: Exception) {
                    Log.e("ApiKeyStore", e.message.orEmpty())
                    null
                }
            }

    suspend fun getApiKey(): String? = tokenFlow.firstOrNull()

    suspend fun saveApiKey(apiKey: String) {
        val aead = aead ?: throw IllegalStateException("Keystore is not available")
        try {
            val ciphertext = aead.encrypt(apiKey.toByteArray(Charsets.UTF_8), null)
            val encoded = Base64.encodeToString(ciphertext, Base64.NO_WRAP)
            dataStore.edit { it[API_KEY_PREFS_KEY] = encoded }
        } catch (e: IOException) {
            throw e
        }
    }
}