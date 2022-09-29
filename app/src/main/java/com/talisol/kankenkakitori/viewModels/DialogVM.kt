package com.talisol.kankenkakitori.viewModels

import androidx.lifecycle.ViewModel
import com.talisol.kankenkakitori.actions.DialogAction
import com.talisol.kankenkakitori.ui.states.DialogState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DialogVM: ViewModel() {

    private val _dialogState = MutableStateFlow(DialogState())
    val dialogState = _dialogState.asStateFlow()

    fun onAction(action: DialogAction) {
        when (action) {
            is DialogAction.ShowAlertDialog -> showAlertDialog(action.dialogState)
            is DialogAction.CloseAlertDialog -> closeAlertDialog()
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