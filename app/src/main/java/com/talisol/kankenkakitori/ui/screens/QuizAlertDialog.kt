package com.talisol.kankenkakitori.ui.screens

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.talisol.kankenkakitori.actions.PopupAction
import com.talisol.kankenkakitori.ui.states.PopUpState

@Composable
fun QuizAlertDialog(
    popUpState: PopUpState,
    onAction: (PopupAction) -> Unit,
) {
    if (popUpState.isAlertDialogShown) {
        AlertDialog(
            onDismissRequest = {},
            title = {
                Text(text = popUpState.title)
            },
            text = {
                Text(popUpState.dialogText!!)
            },
            confirmButton = {
                Button(
                    onClick = popUpState.onConfirmAction!!
                ) {
                    Text(popUpState.confirmButtonText)
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        onAction(PopupAction.CloseAlertDialog)
                    }) {
                    Text(popUpState.dismissButtonText)
                }
            }
        )
    }

}