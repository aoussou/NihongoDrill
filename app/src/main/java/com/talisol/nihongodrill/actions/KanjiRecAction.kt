package com.talisol.nihongodrill.actions

import android.graphics.Bitmap

sealed class KanjiRecAction{
    data class RecognizeKanji(val bitmap: Bitmap) : KanjiRecAction()
    object ResetPredictedKanji : KanjiRecAction()
    object ResetOtherGuesses : KanjiRecAction()
    object ResetBitmap: KanjiRecAction()
    data class SaveImage(val kanji: String,val directory: String) : KanjiRecAction()
    object SetOtherGuessesList: KanjiRecAction()
    data class SetPredictedKanji(val kanji: String): KanjiRecAction()

}
