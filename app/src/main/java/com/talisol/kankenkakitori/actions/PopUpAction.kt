package com.talisol.kankenkakitori.actions

import com.talisol.kankenkakitori.ui.states.PopUpState

sealed class PopUpAction{
    data class ShowAlertDialog(val popUpState: PopUpState): PopUpAction()
    object CloseAlertDialog: PopUpAction()
    object ShowOtherGuesses: PopUpAction()
    object CloseOtherGuesses: PopUpAction()
}

