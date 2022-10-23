package com.talisol.nihongodrill.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.talisol.nihongodrill.ui.screens.QuizSettingScreen
import com.talisol.nihongodrill.ui.screens.questionScreens.HomeScreen

@Composable
fun SetupNavGraph(
    navController: NavHostController
) {

    NavHost(navController = navController, startDestination = ScreenRoute.HomeScreen.route){

        composable(route = ScreenRoute.HomeScreen.route) {
            HomeScreen(navController = navController)
        }

//        composable(route = QuizSettingScreen(groupsList = , quizType = , onAction = ))

    }

}