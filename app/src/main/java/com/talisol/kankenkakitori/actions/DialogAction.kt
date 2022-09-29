package com.talisol.kankenkakitori.actions

import com.talisol.kankenkakitori.ui.states.DialogState

sealed class DialogAction{
    data class ShowAlertDialog(val dialogState: DialogState): DialogAction()
    object CloseAlertDialog: DialogAction()
}

