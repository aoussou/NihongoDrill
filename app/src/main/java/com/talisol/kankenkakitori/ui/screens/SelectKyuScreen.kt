package com.talisol.kankenkakitori.ui.screens

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import com.talisol.kankenkakitori.quizUtils.QuizAction

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SelectKyuScreen(
    groupsList: List<String>,
    modifier: Modifier = Modifier,
    onAction: (QuizAction) -> Unit
) {


    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize(0.5F)
            .verticalScroll(scrollState)
            .then(modifier),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        for (i in groupsList) {
            Button(onClick = {
                onAction(QuizAction.SelectQuestionLevel(i))
            }) {
                Text(text = i)
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
                    QuizAction.ChooseNumberOfQuestions(
                        textState.value.text.toInt()
                    )
                );
                focusManager.clearFocus()

                Log.i("DEBUG", "number sent")
            })
    )



}