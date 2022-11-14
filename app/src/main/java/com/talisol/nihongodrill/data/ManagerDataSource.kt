package com.talisol.nihongodrill.data

import databases.*

interface ManagerDataSource {

    fun getKankenKyuList(): List<String>

    fun getKankenQuestionTypeList(kyu: String): List<String>


    fun getAllKankenQuestions(): List<KankenQuestion>


    fun getJLPTlevelList(): List<String>
    fun getJLPTQuestionTypeList(level: String): List<String>

//    fun selectJLPTQuestions(level: String, type: String): List<JlptQuestion>

    fun getAllJLPTQuestions(): List<JlptQuestion>

    fun getWordInfo(word: String): List<Explanation>

    fun getWordInfoGivenReading(word: String, reading: String): Explanation?

    fun getExamplesList(wordId: Long): List<Example>
}