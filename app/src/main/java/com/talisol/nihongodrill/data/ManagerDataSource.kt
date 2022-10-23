package com.talisol.nihongodrill.data

import databases.JlptQuestion
import databases.KankenQuestion

interface ManagerDataSource {

    fun getKankenKyuList(): List<String>

    fun getKankenQuestionTypeList(kyu: String): List<String>

    fun selectKankenQuestions(kyu: String, type: String): List<KankenQuestion>


    fun getJLPTlevelList(): List<String>
    fun getJLPTQuestionTypeList(level: String): List<String>

//    fun selectJLPTQuestions(level: String, type: String): List<JlptQuestion>

    fun getAllJLPTQuestions(): List<JlptQuestion>
}