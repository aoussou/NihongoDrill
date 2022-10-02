package com.talisol.kankenkakitori.actions

import com.talisol.kankenkakitori.ui.states.PopUpState

sealed class PopupAction{
    data class ShowAlertDialog(val popUpState: PopUpState): PopupAction()
    object CloseAlertDialog: PopupAction()
    object ShowOtherGuesses: PopupAction()
    object CloseOtherGuesses: PopupAction()
}

