package com.talisol.kankenkakitori.ui

import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.talisol.kankenkakitori.actions.KanjiRecAction
import com.talisol.kankenkakitori.actions.PopUpAction

@Composable
fun MySpinner(
    isExpanded: Boolean,
    onPopUpAction: (PopUpAction) -> Unit,
    items: List<String>,
    onKanjiRecAction: (KanjiRecAction) -> Unit

) {

    DropdownMenu(
        expanded = isExpanded,
        onDismissRequest = { onPopUpAction(PopUpAction.CloseOtherGuesses)},
    ) {
        items.forEachIndexed { index, element ->
            DropdownMenuItem(onClick = {
                onKanjiRecAction(KanjiRecAction.SetPredictedKanji(element))
                onPopUpAction(PopUpAction.CloseOtherGuesses)
            }) {
                Text(text = element)
            }
        }
    }

}