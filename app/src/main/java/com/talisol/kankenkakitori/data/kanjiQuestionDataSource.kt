package com.talisol.kankenkakitori.data

import databases.kanji.KakitoriQuestion

interface KanjiQuestionDataSource {

    fun getAllKankenEntriesByKyu(kyu:String): List<KakitoriQuestion>

    fun updateTotalCorrectNumber(id:Long, nCorrect: Long)

    fun updateTotalWrongNumber(id:Long, nWrong: Long)

    fun updateCorrectStreak(id:Long, nCorrectStreak: Long)

    fun updateLastCorrectDate(id:Long, lastCorrectDate:String)

    fun getKankenKyuList(): List<String>

    fun makeUnavailable(id: Long)

    fun markForReview(id:Long)

    fun getTotalCorrect(id:Long): Long

    fun getTotalWrong(id:Long): Long

    fun getCorrectStrek(id: Long): Long
}