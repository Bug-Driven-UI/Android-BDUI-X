package ru.bugdrivenui.bduix.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import ru.bugdrivenui.bduix.data.DataConstants.APPLICATION_JSON
import ru.bugdrivenui.bduix.data.DataConstants.BASE_URL
import ru.bugdrivenui.bduix.data.DataConstants.TIMEOUT_SECONDS
import ru.bugdrivenui.bduix.data.api.BduiApi
import ru.bugdrivenui.bduix.data.api.TestApi
import ru.bugdrivenui.bduix.data.repository.BduiScreenRepository
import ru.bugdrivenui.bduix.data.repository.TestRepository
import ru.bugdrivenui.bduix.domain.repository.IBduiScreenRepository
import ru.bugdrivenui.bduix.domain.repository.ITestRepository
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class BduixModule {

    @Binds
    abstract fun bindBduiScreenRepository(impl: BduiScreenRepository): IBduiScreenRepository

    companion object {

        @Singleton
        @Provides
        fun provideTestApi(retrofit: Retrofit): TestApi {
            return retrofit.create<TestApi>()
        }

        @Singleton
        @Provides
        fun provideBduiApi(retrofit: Retrofit): BduiApi {
            return retrofit.create<BduiApi>()
        }

        @Singleton
        @Provides
        fun provideRetrofit(
            okHttpClient: OkHttpClient,
            json: Json,
        ): Retrofit = retrofit(
            baseUrl = BASE_URL,
            okHttpClient = okHttpClient,
            json = json,
        )

        @Singleton
        @Provides
        fun provideOkHttpClient(): OkHttpClient = okHttpClient()

        @Singleton
        @Provides
        fun provideJson(): Json = json()

        private fun retrofit(
            baseUrl: String,
            okHttpClient: OkHttpClient,
            json: Json,
        ): Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory(APPLICATION_JSON.toMediaType()))
            .build()

        private fun okHttpClient(): OkHttpClient {
            return OkHttpClient.Builder().apply {
                connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            }.build()
        }

        private fun json() = Json {
            ignoreUnknownKeys = true
            prettyPrint = true
        }
    }

    @Binds
    abstract fun bindTestRepository(impl: TestRepository): ITestRepository
}