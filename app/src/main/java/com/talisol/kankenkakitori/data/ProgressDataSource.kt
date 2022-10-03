package com.talisol.kankenkakitori.data

import databases.kanji.KakitoriQuestion

interface ProgressDataSource {

    fun updateTotalCorrectNumber(id:Long, nCorrect: Long)

    fun updateTotalWrongNumber(id:Long, nWrong: Long)

    fun updateCorrectStreak(id:Long, nCorrectStreak: Long)

    fun updateLastCorrectDate(id:Long, lastCorrectDate:String)

    fun getTotalCorrect(id:Long): Long

    fun getTotalWrong(id:Long): Long

    fun getCorrectStrek(id: Long): Long

    fun makeUnavailable(id: Long)

    fun markForReview(id:Long)
}