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


fun getVerbInflection(verb: String, isIchidan: Boolean): Map<String, String>  {

    val stem = verb.substring(0, verb.length-1)
    val ending =  if (isIchidan) {
        mapOf(
            "te" to stem + "て",
            "past" to stem + "た",
            "negative" to stem + "な",
            "negativeOld" to stem + "ぬ",
            "negativeContinous" to stem + "ず",
            "masu" to stem + "ま",
            "volitional" to stem + "よう",
            "imperative" to stem + "ろ",
            "hypothetical" to stem + "れば",
            "passive" to stem + "られ",
            "dictionary" to verb,
            "masuForm" to stem,
            "potential" to stem + "られ",
        )
    } else {
        val verbEnding = verb.last().toString()
        Log.i("UTILSinflec0", verb)
        Log.i("UTILSinflec1", verbEnding)
        val godanEndings = VerbEndings.endingsMap[verbEnding]!!

        mapOf(
            "te" to stem + godanEndings.teForm,
            "past" to stem + godanEndings.pastForm,
            "negative" to stem + godanEndings.aForm + "な",
            "negative-old" to stem + godanEndings.aForm + "ぬ",
            "negative-continous"  to stem + godanEndings.aForm + "ず",
            "masu" to stem + godanEndings.iForm + "ま",
            "volitional" to stem + godanEndings.oForm + "う",
            "imperative"  to stem + godanEndings.eForm,
            "hypothetical" to stem + godanEndings.eForm + "ば",
            "passive" to stem + godanEndings.aForm + "れ",
            "dictionary" to verb,
            "masu-form" to stem + godanEndings.iForm,
            "potential" to stem + godanEndings.eForm,

        )

    }

    return ending

}