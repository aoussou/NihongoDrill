package com.talisol.nihongodrill.quizUtils

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray

// THIS NEEDS A LOT OF FIXING

// IT CRASHES IF IT CAN'T FIND THE TARGET

// IT'S BEING CALLED ALL THE TIME WHEN DRAWING CHARACTER
fun makeTargetRed(string:String,target:String): AnnotatedString{

    Log.i("DEBUG",string)
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

fun extractStringFromJson(jsonString: String): List<String> {
    Log.i("DEBUG",jsonString)
    val jsonArray = Json.parseToJsonElement(jsonString).jsonArray
    val stringList = mutableListOf<String>()
    for (s in jsonArray.toList()) {
        val suggestionString = s.toString().replace(""""""", "")
        stringList.add(suggestionString)
    }

    return stringList
}