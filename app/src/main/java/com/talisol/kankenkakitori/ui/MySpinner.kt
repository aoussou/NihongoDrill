package com.talisol.kankenkakitori.ui

import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.talisol.kankenkakitori.actions.KanjiRecAction
import com.talisol.kankenkakitori.actions.PopUpAction
import com.talisol.kankenkakitori.actions.QuizAction
import com.talisol.kankenkakitori.actions.TrackingAction
import com.talisol.kankenkakitori.ui.states.PopUpState

@Composable
fun MySpinner(
    isExpanded: Boolean,
    onPopUpAction: (PopUpAction) -> Unit,
    items: List<String>,
    onKanjiRecAction: (KanjiRecAction) -> Unit,
) {

    DropdownMenu(
        expanded = isExpanded,
        onDismissRequest = { onPopUpAction(PopUpAction.CloseOtherGuesses)},
    ) {
        items.forEachIndexed { index, element ->
            DropdownMenuItem(onClick = {

                val checkChangeKanji = PopUpState(
                    dialogText = "Are you sure you actually wrote $element?",
                    onConfirmAction = {
                        onKanjiRecAction(KanjiRecAction.SetPredictedKanji(element))
                        onPopUpAction(PopUpAction.CloseOtherGuesses)
                        onKanjiRecAction(KanjiRecAction.SaveImage(element))
                        onPopUpAction(PopUpAction.CloseAlertDialog)
                    }
                )

                onPopUpAction(PopUpAction.ShowAlertDialog(checkChangeKanji))



            }) {
                Text(text = element)
            }
        }
    }

}