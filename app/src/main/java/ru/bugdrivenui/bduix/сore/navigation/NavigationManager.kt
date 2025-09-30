package ru.bugdrivenui.bduix.сore.navigation

import androidx.navigation.NavOptions
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NavigationManager @Inject constructor() {

    private val _commands = MutableSharedFlow<NavigationCommand>(extraBufferCapacity = 1)
    val commands = _commands.asSharedFlow()

    fun tryAddCommand(command: NavigationCommand): Boolean {
        return _commands.tryEmit(command)
    }

    fun <T : Any> navigate(
        route: T,
        options: NavOptions? = null,
    ) = tryAddCommand(NavigationCommand.Navigate(route, options))

    fun <T : Any> navigateToBottomSheet(
        route: NavigationRoute.BottomSheet<T>,
        args: T,
        options: NavOptions? = null,
    ) = tryAddCommand(
        command = NavigationCommand.NavigateToBottomSheet(
            route = route,
            args = args,
            options = options,
        ),
    )

    fun <T : Any> replace(
        route: T,
    ) = tryAddCommand(NavigationCommand.Replace(route))

    fun back() = tryAddCommand(NavigationCommand.Back)
}