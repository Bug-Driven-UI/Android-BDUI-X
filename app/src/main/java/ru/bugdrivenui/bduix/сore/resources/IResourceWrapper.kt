package ru.bugdrivenui.bduix.сore.resources

import android.content.Context
import androidx.annotation.StringRes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

interface IResourcesWrapper {

    fun getString(@StringRes id: Int, vararg args: Any): String
}

@Singleton
class ResourcesWrapper @Inject constructor(
    @ApplicationContext private val context: Context,
) : IResourcesWrapper {

    override fun getString(id: Int, vararg args: Any): String {
        return context.getString(id, *args)
    }
}