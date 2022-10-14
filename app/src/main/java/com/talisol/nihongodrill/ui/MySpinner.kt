package com.talisol.nihongodrill.ui

import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.talisol.nihongodrill.actions.KanjiRecAction
import com.talisol.nihongodrill.actions.PopupAction
import com.talisol.nihongodrill.ui.states.PopupState

@Composable
fun MySpinner(
    isExpanded: Boolean,
    onPopupAction: (PopupAction) -> Unit,
    items: List<String>,
    onKanjiRecAction: (KanjiRecAction) -> Unit,
) {

    DropdownMenu(
        expanded = isExpanded,
        onDismissRequest = { onPopupAction(PopupAction.CloseOtherGuesses)},
    ) {
        items.forEachIndexed { index, element ->
            DropdownMenuItem(onClick = {

                val checkChangeKanji = PopupState(
                    dialogText = "Are you sure you actually wrote $element?",
                    onConfirmAction = {
                        onKanjiRecAction(KanjiRecAction.SetPredictedKanji(element))
                        onPopupAction(PopupAction.CloseOtherGuesses)
                        onKanjiRecAction(KanjiRecAction.SaveImage(element,"screenshots"))
                        onPopupAction(PopupAction.CloseAlertDialog)
                    }
                )

                onPopupAction(PopupAction.ShowAlertDialog(checkChangeKanji))



            }) {
                Text(text = element)
            }
        }
    }

}