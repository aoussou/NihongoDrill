package com.talisol.kankenkakitori.actions

import android.graphics.Bitmap

sealed class KanjiRecAction{
    data class RecognizeKanji(val bitmap: Bitmap) : KanjiRecAction()
    object ResetPredictedKanji : KanjiRecAction()
    data class SaveImage(val bitmap: Bitmap, val kanji: String) : KanjiRecAction()
    object SetOtherGuessesList: KanjiRecAction()
    data class SetPredictedKanji(val kanji: String): KanjiRecAction()
}
