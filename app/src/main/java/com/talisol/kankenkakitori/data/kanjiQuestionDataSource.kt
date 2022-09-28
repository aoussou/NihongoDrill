package com.talisol.kankenkakitori.data

import databases.kanji.KakitoriQuestion

interface KanjiQuestionDataSource {

    fun getAllKankenEntriesByKyu(kyu:Long): List<KakitoriQuestion>

    fun updateTotalCorrectNumber(id:Long, nCorrect: Long)

    fun updateTotalWrongNumber(id:Long, nWrong: Long)

    fun updateCorrectStreak(id:Long, nCorrectStreak: Long)

    fun updateLastCorrectDate(id:Long, lastCorrectDate:String)

    fun getKankenKyuList(): List<Long>

    fun makeUnavailable(id: Long)

    fun markForReview(id:Long)
}