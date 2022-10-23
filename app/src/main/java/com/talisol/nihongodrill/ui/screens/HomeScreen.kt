package com.talisol.nihongodrill.ui.screens.questionScreens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.talisol.nihongodrill.actions.QuizSettingAction
import com.talisol.nihongodrill.navigation.ScreenRoute

@Composable
fun HomeScreen(
    onQuizSettingAction: (QuizSettingAction) -> Unit,
    navController: NavController
) {

    Column(modifier = Modifier.fillMaxSize()) {

        Text("SELECT TEST TYPE")

        Button(onClick = {
            onQuizSettingAction(QuizSettingAction.SelectCategory("jlpt"))
            onQuizSettingAction(QuizSettingAction.LoadSelectedGroupQuestions)
            navController.navigate(ScreenRoute.QuestionsSelection.route)
        }) {
            Text(text = "JLPT")
        }

        Button(onClick = {
            onQuizSettingAction(QuizSettingAction.SelectCategory("kanken"))
            navController.navigate(ScreenRoute.QuestionsSelection.route)
        }) {
            Text(text = "漢検")
        }

    }



}