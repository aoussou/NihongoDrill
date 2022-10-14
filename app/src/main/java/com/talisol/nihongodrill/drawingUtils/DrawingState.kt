package com.talisol.nihongodrill.drawingUtils


import android.view.View
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Path

data class DrawingState(
    val allStrokes: MutableList<Path> = mutableListOf(),
    val allUndoneStrokes: MutableList<Path> = mutableListOf(),
    val motionEvent: MotionEvent = MotionEvent.Idle,
    val currentPosition: Offset = Offset.Unspecified,
    val previousPosition: Offset = Offset.Unspecified,
    val composableBounds: Rect? = null,
    val drawingScreenView: View? = null
)