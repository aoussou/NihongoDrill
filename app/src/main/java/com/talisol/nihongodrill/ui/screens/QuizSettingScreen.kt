package com.talisol.nihongodrill.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import com.talisol.nihongodrill.actions.QuizSettingAction

@Composable
fun SelectKyuScreen(
    groupsList: List<String>,
    quizType: List<String>,
    modifier: Modifier = Modifier,
    onAction: (QuizSettingAction) -> Unit
) {

    Row(horizontalArrangement = Arrangement.SpaceBetween){

        val scrollState1 = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxSize(0.5F)
                .verticalScroll(scrollState1)
                .then(modifier),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "SELECT TYPE")
            for (i in quizType) {
                Button(onClick = {
                    onAction(QuizSettingAction.ChooseQuestionType(i))
                }) {
                    Text(text = i)
                }
            }
        }

        val scrollState2 = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxSize(0.5F)
                .verticalScroll(scrollState2)
                .then(modifier),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "SELECT LEVEL")
            for (i in groupsList) {
                Button(
                    onClick = {
                    onAction(QuizSettingAction.SelectQuestionLevel(i))
                }) {
                    Text(text = i)
                }
            }
        }

    }




    val textState = remember { mutableStateOf(TextFieldValue()) }
    val focusManager = LocalFocusManager.current
    OutlinedTextField(
        label = { Text("Number of Random Question") },
        value = textState.value,
        onValueChange = {
            textState.value = it;

        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Number
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                onAction(
                    QuizSettingAction.ChooseNumberOfQuestions(
                        textState.value.text.toInt()
                    )
                )
                focusManager.clearFocus()

                Log.i("DEBUG", "number sent")
            })
    )


}