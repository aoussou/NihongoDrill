package com.talisol.kankenkakitori.ui.screens

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.talisol.kankenkakitori.actions.DialogAction
import com.talisol.kankenkakitori.ui.states.DialogState
import com.talisol.kankenkakitori.actions.QuizAction

@Composable
fun QuizAlertDialog(
    dialogState: DialogState,
    onAction: (DialogAction) -> Unit,
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
                    Text(dialogState.confirmButtonText)
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        onAction(DialogAction.CloseAlertDialog)
                    }) {
                    Text(dialogState.dismissButtonText)
                }
            }
        )
    }

}