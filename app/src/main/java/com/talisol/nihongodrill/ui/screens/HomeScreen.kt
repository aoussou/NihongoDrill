package com.talisol.nihongodrill.ui.screens.questionScreens

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.talisol.nihongodrill.actions.QuizSettingAction

@Composable
fun HomeScreen(
    onQuizSettingAction: (QuizSettingAction) -> Unit,
    navController: NavController
) {

    Text("SELECT TEST TYPE")

    Button(onClick = {
        onQuizSettingAction(QuizSettingAction.SelectCategory("jlpt"))
    }) {
        Text(text = "JLPT")
    }

    Button(onClick = {
        onQuizSettingAction(QuizSettingAction.SelectCategory("kanken"))
    }) {
        Text(text = "漢検")
    }

}