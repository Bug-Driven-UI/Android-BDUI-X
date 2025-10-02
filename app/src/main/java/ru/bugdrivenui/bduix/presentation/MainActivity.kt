package ru.bugdrivenui.bduix.presentation

//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material.navigation.ModalBottomSheetLayout
import androidx.compose.material.navigation.rememberBottomSheetNavigator
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.bugdrivenui.bduix.core.navigation.BduiNavGraph
import ru.bugdrivenui.bduix.core.navigation.NavigationManager
import ru.bugdrivenui.bduix.core.snackbar.SnackbarManager
import ru.bugdrivenui.bduix.presentation.ui.theme.BduiTheme
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
