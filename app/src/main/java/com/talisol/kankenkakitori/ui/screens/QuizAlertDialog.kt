package com.talisol.kankenkakitori.ui.screens

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.talisol.kankenkakitori.actions.PopupAction
import com.talisol.kankenkakitori.ui.states.PopupState

@Composable
fun QuizAlertDialog(
    popupState: PopupState,
    onAction: (PopupAction) -> Unit,
) {
    if (popupState.isAlertDialogShown) {
        AlertDialog(
            onDismissRequest = {},
            title = {
                Text(text = popupState.title)
            },
            text = {
                Text(popupState.dialogText!!)
            },
            confirmButton = {
                Button(
                    onClick = popupState.onConfirmAction!!
                ) {
                    Text(popupState.confirmButtonText)
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        onAction(PopupAction.CloseAlertDialog)
                    }) {
                    Text(popupState.dismissButtonText)
                }
            }
        )
    }

}