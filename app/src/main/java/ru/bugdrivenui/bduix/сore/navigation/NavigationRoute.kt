package ru.bugdrivenui.bduix.сore.navigation

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

sealed interface NavigationRoute {

    @Serializable
    data object StartScreen : NavigationRoute

    @Serializable
    data class BduiScreen(val args: Args) : NavigationRoute {

        @Serializable
        data class Args(
            val screenId: String,
            val screenParams: Map<String, JsonElement>,
        )
    }

    sealed class BottomSheet<T : Any>(
        val key: String,
        val serializer: KSerializer<T>,
    ) {

        data object BduiBottomSheet : BottomSheet<BduiBottomSheet.Args>(
            key = "BduiBottomSheet",
            serializer = Args.serializer(),
        ) {

            @Serializable
            data class Args(
                val screenId: String,
                val screenParams: Map<String, JsonElement>,
            )
        }
    }
}