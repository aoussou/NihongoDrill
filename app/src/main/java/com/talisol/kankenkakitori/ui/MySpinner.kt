package com.talisol.kankenkakitori.ui

import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.talisol.kankenkakitori.actions.KanjiRecAction
import com.talisol.kankenkakitori.actions.PopupAction
import com.talisol.kankenkakitori.ui.states.PopUpState

@Composable
fun MySpinner(
    isExpanded: Boolean,
    onPopUpAction: (PopupAction) -> Unit,
    items: List<String>,
    onKanjiRecAction: (KanjiRecAction) -> Unit,
) {

    DropdownMenu(
        expanded = isExpanded,
        onDismissRequest = { onPopUpAction(PopupAction.CloseOtherGuesses)},
    ) {
        items.forEachIndexed { index, element ->
            DropdownMenuItem(onClick = {

                val checkChangeKanji = PopUpState(
                    dialogText = "Are you sure you actually wrote $element?",
                    onConfirmAction = {
                        onKanjiRecAction(KanjiRecAction.SetPredictedKanji(element))
                        onPopUpAction(PopupAction.CloseOtherGuesses)
                        onKanjiRecAction(KanjiRecAction.SaveImage(element,"screenshots"))
                        onPopUpAction(PopupAction.CloseAlertDialog)
                    }
                )

                onPopUpAction(PopupAction.ShowAlertDialog(checkChangeKanji))



            }) {
                Text(text = element)
            }
        }
    }

}