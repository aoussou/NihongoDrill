package com.talisol.nihongodrill.navigation

sealed class ScreenRoute(val route: String){

    object HomeScreen: ScreenRoute("home")
    object QuestionsSelection: ScreenRoute("questions_selection")
    object QuizScreen: ScreenRoute("quiz_screen")

}
