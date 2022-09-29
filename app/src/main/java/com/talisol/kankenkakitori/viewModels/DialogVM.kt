package com.talisol.kankenkakitori.viewModels

import androidx.lifecycle.ViewModel
import com.talisol.kankenkakitori.ui.states.DialogState
import com.talisol.kankenkakitori.quizUtils.QuizAction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DialogVM: ViewModel() {

    private val _dialogState = MutableStateFlow(DialogState())
    val dialogState = _dialogState.asStateFlow()

    fun onAction(action: QuizAction) {

        when (action) {

            is QuizAction.ShowAlertDialog -> showAlertDialog(action.dialogState)
            is QuizAction.CloseAlertDialog -> closeAlertDialog()
            else -> {}
        }

    }

    private fun showAlertDialog(newDialogState: DialogState) {
        _dialogState.update {
            it.copy(
                isAlertDialogShown = true,
                dialogText = newDialogState.dialogText,
                onConfirmAction = newDialogState.onConfirmAction
            )
        }

    }

    private fun closeAlertDialog() {
        _dialogState.value = DialogState()
    }

}