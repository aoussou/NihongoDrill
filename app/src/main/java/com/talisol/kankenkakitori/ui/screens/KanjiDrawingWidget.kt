package com.talisol.kankenkakitori.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.node.modifierElementOf
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.talisol.kankenkakitori.actions.DrawingAction
import com.talisol.kankenkakitori.actions.KanjiRecAction
import com.talisol.kankenkakitori.actions.PopupAction
import com.talisol.kankenkakitori.drawingUtils.DrawingState
import com.talisol.kankenkakitori.ui.MySpinner
import com.talisol.kankenkakitori.ui.states.PopupState
import com.talisol.kankenkakitori.ui.states.QuizState

@Composable
fun KanjiDrawingWidget(
    quizState: QuizState,
    drawingState: DrawingState,
    popupState: PopupState,
    currentPath: Path,
    predictedKanji: String?,
    otherGuessesList: List<String>?,
    onDrawingAction: (DrawingAction) -> Unit,
    onKanjiRecAction: (KanjiRecAction) -> Unit,
    onPopupAction: (PopupAction) -> Unit,
) {

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Bottom
    ) {


        Box(
            modifier = Modifier
                .aspectRatio(1f)
                .fillMaxWidth()
                .border(BorderStroke(5.dp, Color.Black))
                .background(Color.White)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(.25F)
                    .aspectRatio(1f)
                    .align(Alignment.TopEnd)
                    .padding(12.dp)
                    .border(
                        if (predictedKanji != null) {
                            BorderStroke(1.dp, Color.Black)
                        } else BorderStroke(0.dp, Color.White)
                    )
                    .background(Color.White)
                    .zIndex(if (predictedKanji != null) 1f else 0f)
                    .clickable {
                        onKanjiRecAction(KanjiRecAction.SetOtherGuessesList)
                        onPopupAction(PopupAction.ShowOtherGuesses)
                    },
                contentAlignment = Alignment.Center
            ) {
                if (predictedKanji != null) Text(text = predictedKanji, fontSize = 36.sp)
                if (otherGuessesList != null) {
                    MySpinner(
                        isExpanded = popupState.isShowOtherGuesses,
                        onPopupAction = onPopupAction,
                        items = otherGuessesList,
                        onKanjiRecAction = onKanjiRecAction
                    )
                }
            }



                DrawingScreen(
                    currentPath,
                    drawingState,
                    onDrawingAction,
                )


        }


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.2F)

        ) {
            DrawingPropertiesMenu(
                drawingState = drawingState,
                onDrawingAction = onDrawingAction,
                onKanjiRecAction = onKanjiRecAction
            )
        }

    }

}









