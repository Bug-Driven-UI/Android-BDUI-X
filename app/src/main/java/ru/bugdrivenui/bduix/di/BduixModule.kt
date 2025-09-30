package ru.bugdrivenui.bduix.di

import android.content.Context
import coil.ImageLoader
import coil.disk.DiskCache
import coil.memory.MemoryCache
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.create
import ru.bugdrivenui.bduix.data.DataConstants.APPLICATION_JSON
import ru.bugdrivenui.bduix.data.DataConstants.BASE_URL
import ru.bugdrivenui.bduix.data.DataConstants.TIMEOUT_SECONDS
import ru.bugdrivenui.bduix.data.api.BduiApi
import ru.bugdrivenui.bduix.data.repository.BduiScreenRepository
import ru.bugdrivenui.bduix.domain.repository.IBduiScreenRepository
import ru.bugdrivenui.bduix.сore.resources.IResourcesWrapper
import ru.bugdrivenui.bduix.сore.resources.ResourcesWrapper
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class BduixModule {

    @Binds
    abstract fun bindBduiScreenRepository(impl: BduiScreenRepository): IBduiScreenRepository

    @Binds
    abstract fun bindResourcesWrapper(impl: ResourcesWrapper): IResourcesWrapper

    companion object {

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

        @Singleton
        @Provides
        fun provideImageLoader(
            @ApplicationContext context: Context,
            okHttpClient: OkHttpClient,
        ): ImageLoader =
            ImageLoader.Builder(context)
                .okHttpClient(okHttpClient)
                .memoryCache {
                    MemoryCache.Builder(context)
                        .maxSizePercent(0.25)
                        .build()
                }
                .diskCache {
                    DiskCache.Builder()
                        .directory(context.cacheDir.resolve("coil_image_cache"))
                        .maxSizePercent(0.02)
                        .build()
                }
                .crossfade(true)
                .respectCacheHeaders(true)
                .build()
    }
}