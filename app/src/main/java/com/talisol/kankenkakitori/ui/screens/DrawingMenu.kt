package com.talisol.kankenkakitori.ui.screens

import android.graphics.Bitmap
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.graphics.applyCanvas
import com.talisol.kankenkakitori.R
import com.talisol.kankenkakitori.actions.DrawingAction
import com.talisol.kankenkakitori.actions.KanjiRecAction
import com.talisol.kankenkakitori.drawingUtils.DrawingState
import kotlin.math.roundToInt


@Composable
fun DrawingPropertiesMenu(
    drawingState: DrawingState,
    onDrawingAction: (DrawingAction) -> Unit,
    onKanjiRecAction: (KanjiRecAction) -> Unit,
) {

    val modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(.3F)
        .background(Color.White)
        .border(BorderStroke(3.dp, Color.Blue))

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        IconButton(
            onClick = {
                if (drawingState.allStrokes.isNotEmpty()) {
                    onDrawingAction(DrawingAction.ClearAllPaths)
                    onKanjiRecAction(KanjiRecAction.ResetPredictedKanji)
                }
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_eraser_black_24dp),
                contentDescription = null,
            )
        }


        IconButton(onClick = {
            if (drawingState.allStrokes.isNotEmpty()) {
                onDrawingAction(DrawingAction.UndoLastStroke)
                onKanjiRecAction(KanjiRecAction.ResetPredictedKanji)
            }
        }) {
            Icon(
                painterResource(id = R.drawable.ic_baseline_undo_24),
                contentDescription = null,
                tint = Color.LightGray
            )
        }

        IconButton(onClick = {
            if (drawingState.allUndoneStrokes.isNotEmpty()) {
                onDrawingAction(DrawingAction.RedoLastUndoneStroke)
            }
        }) {
            Icon(
                painterResource(id = R.drawable.ic_baseline_redo_24),
                contentDescription = null,
                tint = Color.LightGray
            )
        }


        IconButton(onClick = {
            val bounds = drawingState.composableBounds!!
            val bmp = Bitmap
                .createBitmap(
                    (bounds.width).roundToInt(),
                    (bounds.height).roundToInt(),
                    Bitmap.Config.ARGB_8888
                )
                .applyCanvas {
                    translate(-bounds.left, -bounds.top)
                    drawingState.drawingScreenView!!.draw(this)
                }
            onKanjiRecAction(KanjiRecAction.RecognizeKanji(bmp))
        }) {
            Icon(
                painter = painterResource(id =  R.drawable.ic_outline_check_24),
                contentDescription = null,
                tint = Color.Black)
        }

    }

}

