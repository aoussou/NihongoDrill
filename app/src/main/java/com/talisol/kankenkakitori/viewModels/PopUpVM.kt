package com.talisol.kankenkakitori.viewModels

import androidx.lifecycle.ViewModel
import com.talisol.kankenkakitori.actions.PopUpAction
import com.talisol.kankenkakitori.ui.states.PopUpState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PopUpVM: ViewModel() {

    private val _popUpState = MutableStateFlow(PopUpState())
    val popUpState = _popUpState.asStateFlow()

    fun onAction(action: PopUpAction) {
        when (action) {
            is PopUpAction.ShowAlertDialog -> showAlertDialog(action.popUpState)
            is PopUpAction.CloseAlertDialog -> closeAlertDialog()
            is PopUpAction.ShowOtherGuesses -> showOtherGuesses()
            is PopUpAction.CloseOtherGuesses -> closeOtherGuesses()
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