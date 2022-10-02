package com.talisol.kankenkakitori.viewModels

import androidx.lifecycle.ViewModel
import com.talisol.kankenkakitori.actions.PopupAction
import com.talisol.kankenkakitori.ui.states.PopUpState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PopupVM: ViewModel() {

    private val _popUpState = MutableStateFlow(PopUpState())
    val popUpState = _popUpState.asStateFlow()

    fun onAction(action: PopupAction) {
        when (action) {
            is PopupAction.ShowAlertDialog -> showAlertDialog(action.popUpState)
            is PopupAction.CloseAlertDialog -> closeAlertDialog()
            is PopupAction.ShowOtherGuesses -> showOtherGuesses()
            is PopupAction.CloseOtherGuesses -> closeOtherGuesses()
        }
    }

    private fun showAlertDialog(newPopUpState: PopUpState) {
        _popUpState.update {
            it.copy(
                isAlertDialogShown = true,
                dialogText = newPopUpState.dialogText,
                onConfirmAction = newPopUpState.onConfirmAction
            )
        }
    }

    private fun closeAlertDialog() {
        _popUpState.value = PopUpState()
    }

    private fun showOtherGuesses() {
        _popUpState.update {
            it.copy(isShowOtherGuesses = true)
        }
    }

    private fun closeOtherGuesses() {
        _popUpState.update {
            it.copy(isShowOtherGuesses = false)
        }
    }


}