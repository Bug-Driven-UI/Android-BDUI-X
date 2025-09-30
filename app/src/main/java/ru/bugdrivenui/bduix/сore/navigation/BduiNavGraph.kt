package ru.bugdrivenui.bduix.сore.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import ru.bugdrivenui.bduix.presentation.bdui_screen.compose.BduiScreen
import ru.bugdrivenui.bduix.presentation.bdui_screen.viewmodel.BduiScreenViewModel
import ru.bugdrivenui.bduix.presentation.start_screen.compose.StartScreen
import ru.bugdrivenui.bduix.presentation.start_screen.viewmodel.StartScreenViewModel
import kotlin.reflect.typeOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BduiNavGraph(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = NavigationRoute.StartScreen,
    ) {
        composable<NavigationRoute.StartScreen> {
            val viewModel: StartScreenViewModel = hiltViewModel()
            StartScreen()
        }

        composable<NavigationRoute.BduiScreen>(
            typeMap = mapOf(
                typeOf<NavigationRoute.BduiScreen.Args>() to navTypeOf<NavigationRoute.BduiScreen.Args>(),
            ),
        ) { backStackEntry ->
            val args = backStackEntry.toRoute<NavigationRoute.BduiScreen>().args

            val viewModel: BduiScreenViewModel = hiltViewModel()
            BduiScreen(viewModel)
        }

        bottomSheet(
            route = NavigationRoute.BottomSheet.BduiBottomSheet,
        ) { args ->
            val viewModel: BduiScreenViewModel = hiltViewModel()
            ModalBottomSheet(
                onDismissRequest = { navController.popBackStack() }
            ) {
                BduiScreen(viewModel)
            }
        }
    }
}