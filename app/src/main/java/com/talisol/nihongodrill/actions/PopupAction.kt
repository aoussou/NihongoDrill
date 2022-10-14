package com.talisol.nihongodrill.actions

import com.talisol.nihongodrill.ui.states.PopupState

sealed class PopupAction{
    data class ShowAlertDialog(val popUpState: PopupState): PopupAction()
    object CloseAlertDialog: PopupAction()
    object ShowOtherGuesses: PopupAction()
    object CloseOtherGuesses: PopupAction()
}

