package com.talisol.nihongodrill.data

import databases.KankenQuestion

interface ManagerDataSource {

    fun getKankenKyuList(): List<Long>

    fun getKankenQuestionList(kyu: Long, type: String): List<KankenQuestion>

    fun selectKankenQuestions(kyu: Long, type: String): List<KankenQuestion>
}