package ru.bugdrivenui.bduix.core.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import ru.bugdrivenui.bduix.presentation.bdui_screen.compose.BduiScreen
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiActionUi
import ru.bugdrivenui.bduix.presentation.bdui_screen.viewmodel.BduiScreenViewModel
import ru.bugdrivenui.bduix.presentation.start_screen.compose.StartScreen
import ru.bugdrivenui.bduix.presentation.start_screen.viewmodel.StartScreenViewModel
import kotlin.reflect.typeOf

const val SHOULD_UPDATE_SCREEN_KEY = "SHOULD_UPDATE_SCREEN_KEY"

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

            val viewModel: BduiScreenViewModel = hiltViewModel<BduiScreenViewModel, BduiScreenViewModel.Factory>(
                creationCallback = { factory ->
                    factory.create(
                        screenName = args.screenName,
                        screenParams = args.screenParams,
                    )
                }
            )

            val updatePreviousScreenResultFlow = remember(backStackEntry) {
                backStackEntry.savedStateHandle.getStateFlow<Boolean?>(SHOULD_UPDATE_SCREEN_KEY, null)
            }
            val updatePreviousScreen by updatePreviousScreenResultFlow.collectAsStateWithLifecycle()
            LaunchedEffect(updatePreviousScreen) {
                if (updatePreviousScreen == true) {
                    viewModel.onAction(BduiActionUi.UpdateScreenResultReceived)
                    backStackEntry.savedStateHandle.set<Boolean?>(SHOULD_UPDATE_SCREEN_KEY, null)
                }
            }

            BduiScreen(viewModel)
        }

        bottomSheet(
            route = NavigationRoute.BottomSheet.BduiBottomSheet,
        ) { args ->
            val viewModel: BduiScreenViewModel = hiltViewModel<BduiScreenViewModel, BduiScreenViewModel.Factory>(
                creationCallback = { factory ->
                    factory.create(
                        screenName = args.screenName,
                        screenParams = args.screenParams,
                    )
                }
            )

            var isHidden by remember { mutableStateOf(false) }
            ModalBottomSheet(
                sheetState = rememberModalBottomSheetState(
                    confirmValueChange = {
                        if (it == SheetValue.Hidden && !isHidden) {
                            isHidden = true
                            navController.popBackStack()
                        }
                        true
                    }
                ),
                onDismissRequest = {},
                containerColor = Color.White,
                dragHandle = {
                    Surface(
                        modifier = Modifier.padding(vertical = 8.dp),
                        color = Color(0xFFEBEAE8),
                        shape = RoundedCornerShape(28.dp),
                    ) {
                        Box(modifier = Modifier.size(width = 40.dp, height = 4.dp))
                    }
                },
            ) {
                BduiScreen(viewModel)
            }
        }
    }
}