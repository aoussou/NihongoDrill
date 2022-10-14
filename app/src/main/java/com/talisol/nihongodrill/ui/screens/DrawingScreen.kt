package com.talisol.nihongodrill.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import com.talisol.nihongodrill.actions.DrawingAction
import com.talisol.nihongodrill.drawingUtils.DrawingState
import com.talisol.nihongodrill.drawingUtils.MotionEvent
import com.talisol.nihongodrill.drawingUtils.PathProperties
import com.talisol.nihongodrill.drawingUtils.dragMotionEvent

@Composable
fun DrawingScreen(
    currentStroke: Path,
    state: DrawingState,
    onAction: (DrawingAction) -> Unit,
    pathProperties: PathProperties = PathProperties(),
    strokeType: Stroke = Stroke(
        width = pathProperties.strokeWidth,
        cap = pathProperties.strokeCap,
        join = pathProperties.strokeJoin
    )
) {

    val view = LocalView.current
    val drawModifier = Modifier
        .aspectRatio(1f)
        .fillMaxWidth()
        .padding(8.dp)
        .background(Color.White)
        .clipToBounds()
        .onGloballyPositioned { layoutCoordinates: LayoutCoordinates ->
            onAction(DrawingAction.SetComposableBounds(layoutCoordinates.boundsInRoot()))
            onAction(DrawingAction.SetDrawingScreenView(view))
        }
        .dragMotionEvent(
            onDragStart = { pointerInputChange ->
                onAction(DrawingAction.UpdateMotion(MotionEvent.Down))
                onAction(DrawingAction.UpdateCurrentPosition(pointerInputChange.position))
                if (pointerInputChange.pressed != pointerInputChange.previousPressed) pointerInputChange.consume()
            },
            onDrag = { pointerInputChange ->
                onAction(DrawingAction.UpdateMotion(MotionEvent.Move))
                onAction(DrawingAction.UpdateCurrentPosition(pointerInputChange.position))
                if (pointerInputChange.positionChange() != Offset.Zero) pointerInputChange.consume()
            },
            onDragEnd = { pointerInputChange ->
                onAction(DrawingAction.UpdateMotion(MotionEvent.Up))
                if (pointerInputChange.pressed != pointerInputChange.previousPressed) pointerInputChange.consume()
            }
        )




        Canvas(modifier = drawModifier) {
            when (state.motionEvent) {
                MotionEvent.Down -> {
                    onAction(DrawingAction.MovePath)
                    onAction(DrawingAction.UpdatePreviousPosition(state.currentPosition))
                }

                MotionEvent.Move -> {
                    onAction(DrawingAction.MakeBezierCurve)
                    onAction(DrawingAction.UpdatePreviousPosition(state.currentPosition))
                }

                MotionEvent.Up -> {
                    onAction(DrawingAction.MakeLine)
                    onAction(DrawingAction.AddToStrokesList(currentStroke))
                    onAction(DrawingAction.ResetCurrentPath)
                    onAction(DrawingAction.ClearUndoneStrokes)
                    onAction(DrawingAction.UpdateCurrentPosition(Offset.Unspecified))
                    // not sure why previous position required?
//                    onAction(DrawingAction.UpdatePreviousPosition(Offset.Unspecified))
//                    previousPosition = currentPosition
                    onAction(DrawingAction.UpdateMotion(MotionEvent.Idle))
                }
                else -> Unit
            }

            ///////////////////////////////////////////////////////////////////////////////
            with(drawContext.canvas.nativeCanvas) {

                val checkPoint = saveLayer(null, null)

                // This keeps the path on the canvas after you drew it
                state.allStrokes.forEach {
                    val path = it
                    drawPath(
                        color = pathProperties.color,
                        path = path,
                        style = strokeType
                    )
                }

                // This shows you the path as you are drawing
                if (state.motionEvent != MotionEvent.Idle) {
                    drawPath(
                        color = pathProperties.color,
                        path = currentStroke,
                        style = strokeType
                    )
                }
                restoreToCount(checkPoint)
            }
        }

    }












