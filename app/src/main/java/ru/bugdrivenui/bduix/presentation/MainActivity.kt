package ru.bugdrivenui.bduix.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.navigation.ModalBottomSheetLayout
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.navigation.rememberBottomSheetNavigator
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.bugdrivenui.bduix.navigation.BduiNavGraph
import ru.bugdrivenui.bduix.navigation.NavigationManager
import ru.bugdrivenui.bduix.presentation.ui.theme.BduixTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigationManager: NavigationManager

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

            ModalBottomSheetLayout(bottomSheetNavigator) {
                BduixTheme {
                    BduiNavGraph(navController)
                }
            }
        }
    }
}
