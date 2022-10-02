package com.talisol.kankenkakitori.actions

import com.talisol.kankenkakitori.ui.states.PopupState

sealed class PopupAction{
    data class ShowAlertDialog(val popUpState: PopupState): PopupAction()
    object CloseAlertDialog: PopupAction()
    object ShowOtherGuesses: PopupAction()
    object CloseOtherGuesses: PopupAction()
}

