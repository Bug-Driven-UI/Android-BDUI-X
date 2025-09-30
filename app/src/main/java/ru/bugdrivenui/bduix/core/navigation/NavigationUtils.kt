package ru.bugdrivenui.bduix.core.navigation

import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.compose.material.navigation.bottomSheet
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import kotlinx.serialization.json.Json
import java.util.Base64

val jsonNav = Json { ignoreUnknownKeys = true }

fun <T : Any> NavGraphBuilder.bottomSheet(
    route: NavigationRoute.BottomSheet<T>,
    content: @Composable (T) -> Unit,
) {
    val fullRoute = "sheet/${route.key}?arg={arg}"
    bottomSheet(
        route = fullRoute,
        arguments = listOf(
            navArgument("arg") {
                type = NavType.StringType
                nullable = false
            }
        )
    ) { entry ->
        val rawArg = entry.arguments?.getString("arg") ?: return@bottomSheet
        val decoded = Uri.decode(rawArg)
        val args = jsonNav.decodeFromString(route.serializer, decoded)

        content(args)
    }
}

inline fun <reified T> navTypeOf(
    json: Json = Json,
) = object : NavType<T>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): T =
        requireNotNull(bundle.getString(key)).let(json::decodeFromString)

    override fun parseValue(value: String): T {
        return json.decodeFromString(
            String(
                bytes = if (Build.VERSION.SDK_INT >= 26) {
                    Base64.getUrlDecoder().decode(value)
                } else {
                    android.util.Base64.decode(value, 0)
                }
            )
        )
    }

    override fun serializeAsValue(value: T): String {
        return if (Build.VERSION.SDK_INT >= 26) {
            Base64.getUrlEncoder().encodeToString(json.encodeToString(value).toByteArray())
        } else {
            android.util.Base64.encodeToString(json.encodeToString(value).toByteArray(), 0)
        }
    }

    override fun put(bundle: Bundle, key: String, value: T) =
        bundle.putString(key, json.encodeToString(value))
}
