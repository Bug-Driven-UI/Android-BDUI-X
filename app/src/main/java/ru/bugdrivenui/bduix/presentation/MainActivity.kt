package ru.bugdrivenui.bduix.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import ru.bugdrivenui.bduix.presentation.bdui_screen.compose.BduiScreen
import ru.bugdrivenui.bduix.presentation.bdui_screen.viewmodel.BduiScreenViewModel

import ru.bugdrivenui.bduix.presentation.ui.theme.BduixTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: BduiScreenViewModel = hiltViewModel()
            BduixTheme {
                BduiScreen(viewModel)
            }
        }
    }
}
