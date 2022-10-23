package com.talisol.nihongodrill.navigation

sealed class ScreenRoute(val route: String){

    object HomeScreen: ScreenRoute("home")
    object QuizSelection: ScreenRoute("quiz_selection")

}
