package com.talisol.kankenkakitori.quizUtils

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle

fun makeTargetRed(string:String,target:String): AnnotatedString{

    val startIndex = string.indexOf(target)
    val beforeTargetString = string.substring(0,startIndex)
    val targetString = string.substring(startIndex,startIndex+target.length)
    val afterTargetString = string.substring(startIndex+target.length)

    val annotatedString = buildAnnotatedString {

        append(beforeTargetString)
        withStyle(style = SpanStyle(color = Color.Red)) {
            append(targetString)
        }
        append(afterTargetString)
    }

    return annotatedString
}