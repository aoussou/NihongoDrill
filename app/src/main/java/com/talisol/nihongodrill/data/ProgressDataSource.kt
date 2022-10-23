package com.talisol.nihongodrill.data

import databases.Progress


interface ProgressDataSource {

    fun updateTotalCorrectNumber(id:Long, nCorrect: Long)

    fun updateTotalWrongNumber(id:Long, nWrong: Long)

    fun updateCorrectStreak(id:Long, nCorrectStreak: Long)

    fun updateLastCorrectDate(id:Long, lastCorrectDate:String)

    fun getTotalCorrect(id:Long): Long

    fun getTotalWrong(id:Long): Long

    fun getCorrectStreak(id: Long): Long

    fun makeUnavailable(id: Long)

    fun markForReview(id:Long)

    fun getProgressInfoById(id:Long): Progress
}