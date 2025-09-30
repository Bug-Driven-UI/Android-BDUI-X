package ru.bugdrivenui.bduix.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.navigation.ModalBottomSheetLayout
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.navigation.rememberBottomSheetNavigator
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.bugdrivenui.bduix.presentation.ui.theme.BduiTheme
import ru.bugdrivenui.bduix.сore.navigation.BduiNavGraph
import ru.bugdrivenui.bduix.сore.navigation.NavigationManager
import ru.bugdrivenui.bduix.сore.snackbar.SnackbarManager
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigationManager: NavigationManager

    @Inject
    lateinit var snackbarManager: SnackbarManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val bottomSheetNavigator = rememberBottomSheetNavigator()
            val navController = rememberNavController(bottomSheetNavigator)

            LaunchedEffect(key1 = Unit) {
                navigationManager.commands.collect { command ->
                    command.execute(navController)
                }
            }

            CompositionLocalProvider(LocalSnackbarManager provides snackbarManager) {
                ModalBottomSheetLayout(bottomSheetNavigator) {
                    BduiTheme {
                        BduiNavGraph(navController)
                    }
                }
            }
        }
    }
}

val LocalSnackbarManager = compositionLocalOf { SnackbarManager() }
