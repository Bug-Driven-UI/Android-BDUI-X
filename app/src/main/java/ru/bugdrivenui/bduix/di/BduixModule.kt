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

    @Binds
    abstract fun bindTestRepository(impl: TestRepository): ITestRepository
}