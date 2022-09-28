package com.talisol.kankenkakitori.quizUtils

data class DialogState(

    val isAlertDialogShown: Boolean = false,
    val title: String = "Really",
    val dialogText: String? = null,
    val comfirmButtonText: String = "Indeed.",
    val dismissButtonText: String = "Nope.",
    val onConfirmAction: (() -> Unit)? = null
)
