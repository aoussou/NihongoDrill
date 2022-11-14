package com.talisol.nihongodrill.quizUtils

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlin.text.substring


fun processTarget(string: String, target: String?): AnnotatedString {

    Log.i("DEBUG", "$string target: $target")

    if (target != null && string != target) {


        val startIndex = string.indexOf(target)
        if (startIndex != -1) {

            val beforeTargetString = string.substring(0, startIndex)
            val targetString = string.substring(startIndex, startIndex + target.length)
            val afterTargetString = string.substring(startIndex + target.length)

            val annotatedString = buildAnnotatedString {

                append(beforeTargetString)
                withStyle(style = SpanStyle(color = Color.Red)) {
                    append(targetString)
                }
                append(afterTargetString)
            }
            return annotatedString
        } else {

            val annotatedString = buildAnnotatedString {
                append(string)
            }
            return annotatedString

        }
    } else {
        val annotatedString = buildAnnotatedString {
            append(string)
        }
        return annotatedString
    }


}

fun extractStringFromJson(jsonString: String, removeQuotationMarks: Boolean = true): List<String> {
    Log.i("DEBUG", jsonString)
    val jsonArray = Json.parseToJsonElement(jsonString).jsonArray
    val stringList = mutableListOf<String>()
    for (s in jsonArray.toList()) {
        var suggestionString = s.toString()
        if (removeQuotationMarks) suggestionString = suggestionString.replace(""""""", "")
        stringList.add(suggestionString)
    }

    return stringList
}

fun extractMapFromJson(jsonString: String, keyWord: String): JsonElement? {

    val jsonMap = Json.parseToJsonElement(jsonString).jsonObject.toMutableMap()

    return jsonMap[keyWord]
}


fun getVerbInflection(verb: String, isIchidan: Boolean) {

    val stem = verb.substring(0, verb.length)
    if (isIchidan) {
        val ending = VerbInflections(
            stem + "て",
            stem + "た",
            stem + "な",
            stem + "ぬ",
            stem + "ず",
            stem + "ま",
            stem + "よう",
            stem + "ろ",
            stem + "れば",
            stem + "られ",
            verb,
            stem,
            stem + "られ",
        )
    } else {
        val verbEnding = verb.last().toString()
        val endings = VerbEndings.endingsMap[verbEnding]!!

        val ending = VerbInflections(
            stem + endings.teForm,
            stem + endings.pastForm,
            stem + endings.aForm + "な",
            stem + endings.aForm + "ぬ",
            stem + endings.aForm + "ず",
            stem + endings.iForm + "ま",
            stem + endings.oForm + "う",
            stem + endings.eForm,
            stem + endings.eForm + "ば",
            stem + endings.aForm + "れ",
            verb,
            stem + endings.iForm,
            stem + endings.eForm,

        )

    }

}