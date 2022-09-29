package com.talisol.kankenkakitori.ui.states

data class DialogState(

    val isAlertDialogShown: Boolean = false,
    val title: String = "Really",
    val dialogText: String? = null,
    val confirmButtonText: String = "Indeed.",
    val dismissButtonText: String = "Nope.",
    val onConfirmAction: (() -> Unit)? = null
)
