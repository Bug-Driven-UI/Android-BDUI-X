package ru.bugdrivenui.bduix.сore.navigation

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.net.Uri
import androidx.navigation.NavController
import androidx.navigation.NavOptions

sealed interface NavigationCommand {

    fun execute(navController: NavController)

    data class Navigate<T : Any>(
        private val route: T,
        private val options: NavOptions? = null,
    ) : NavigationCommand {

        override fun execute(navController: NavController) {
            navController.navigate(route, options)
        }
    }

    data class NavigateToBottomSheet<T : Any>(
        private val route: NavigationRoute.BottomSheet<T>,
        private val args: T,
        private val options: NavOptions? = null,
    ) : NavigationCommand {

        override fun execute(navController: NavController) {
            val payload = jsonNav.encodeToString(route.serializer, args)
            val encoded = Uri.encode(payload)

            navController.navigate(
                route = "sheet/${route.key}?arg=$encoded",
                navOptions = options,
            )
        }
    }

    data class Replace<T : Any>(
        private val route: T,
    ) : NavigationCommand {

        override fun execute(navController: NavController) {
            val currentDestinationId = navController.currentBackStackEntry?.destination?.id
            currentDestinationId?.let { current ->
                navController.navigate(route) {
                    popUpTo(current) {
                        inclusive = true
                    }
                }
            }
        }
    }

    data class ReplaceWithBottomSheet<T : Any>(
        val route: NavigationRoute.BottomSheet<T>,
        val args: T,
    ) : NavigationCommand {

        override fun execute(navController: NavController) {
            val payload = jsonNav.encodeToString(route.serializer, args)
            val encoded = Uri.encode(payload)
            val currentDestinationId = navController.currentBackStackEntry?.destination?.id
            currentDestinationId?.let { current ->
                navController.navigate("sheet/${route.key}?arg=$encoded") {
                    popUpTo(current) {
                        inclusive = true
                    }
                }
            }
        }
    }

    data object Back : NavigationCommand {

        override fun execute(navController: NavController) {
            val popped = navController.popBackStack()
            if (!popped) {
                navController.context.findActivity()?.finish()
            }
        }
    }
}

private tailrec fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}
