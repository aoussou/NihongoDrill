package com.talisol.kankenkakitori.ui.screens

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.input.TextFieldValue
import com.talisol.kankenkakitori.quizUtils.DialogState
import com.talisol.kankenkakitori.quizUtils.QuizAction

@Composable
fun QuizAlertDialog(
    dialogState: DialogState,
    onAction: (QuizAction) -> Unit,
) {
    if (dialogState.isAlertDialogShown) {
        AlertDialog(
            onDismissRequest = {},
            title = {
                Text(text = dialogState.title)
            },
            text = {
                Text(dialogState.dialogText!!)
            },
            confirmButton = {
                Button(
                    onClick = dialogState.onConfirmAction!!
                ) {
                    Text(dialogState.comfirmButtonText)
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        onAction(QuizAction.CloseAlertDialog)
                    }) {
                    Text(dialogState.dismissButtonText)
                }
            }
        )
    }

}