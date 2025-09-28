package ru.bugdrivenui.bduix

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.HiltAndroidApp
import ru.bugdrivenui.bduix.di.ImageLoaderEntryPoint

@HiltAndroidApp
class App : Application(), ImageLoaderFactory {

    override fun newImageLoader(): ImageLoader {
        val entryPoint = EntryPointAccessors.fromApplication(
            this,
            ImageLoaderEntryPoint::class.java
        )
        return entryPoint.imageLoader()
    }
}