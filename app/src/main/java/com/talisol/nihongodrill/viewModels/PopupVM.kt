package com.talisol.nihongodrill.viewModels

import androidx.lifecycle.ViewModel
import com.talisol.nihongodrill.actions.PopupAction
import com.talisol.nihongodrill.ui.states.PopupState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PopupVM: ViewModel() {



    private val _popupState = MutableStateFlow(PopupState())
    val popupState = _popupState.asStateFlow()

    fun onAction(action: PopupAction) {
        when (action) {
            is PopupAction.ShowAlertDialog -> showAlertDialog(action.popUpState)
            is PopupAction.CloseAlertDialog -> closeAlertDialog()
            is PopupAction.ShowOtherGuesses -> showOtherGuesses()
            is PopupAction.CloseOtherGuesses -> closeOtherGuesses()
        }
    }

    private fun showAlertDialog(newPopupState: PopupState) {
        _popupState.update {
            it.copy(
                isAlertDialogShown = true,
                dialogText = newPopupState.dialogText,
                onConfirmAction = newPopupState.onConfirmAction
            )
        }
    }

    private fun closeAlertDialog() {
        _popupState.value = PopupState()
    }

    private fun showOtherGuesses() {
        _popupState.update {
            it.copy(isShowOtherGuesses = true)
        }
    }

    private fun closeOtherGuesses() {
        _popupState.update {
            it.copy(isShowOtherGuesses = false)
        }
    }


}