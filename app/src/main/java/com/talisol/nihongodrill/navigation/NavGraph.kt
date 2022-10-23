package com.talisol.nihongodrill.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.talisol.nihongodrill.actions.QuizSettingAction
import com.talisol.nihongodrill.ui.screens.LevelSectionScreen
import com.talisol.nihongodrill.ui.screens.QuizSettingScreen
import com.talisol.nihongodrill.ui.screens.questionScreens.HomeScreen

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    onQuizSettingAction: (QuizSettingAction) -> Unit,
) {

    NavHost(navController = navController, startDestination = ScreenRoute.HomeScreen.route) {

        composable(route = ScreenRoute.HomeScreen.route) {
            HomeScreen(navController = navController, onQuizSettingAction = onQuizSettingAction)
        }

        composable(route = ScreenRoute.LevelSelection.route) {
            LevelSectionScreen()
        }

    }

}