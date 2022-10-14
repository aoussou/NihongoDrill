package com.talisol.nihongodrill.data

import databases.kanji.SelectKakitoriQuestions


interface KankenQuestionDataSource {

    fun selectKakitoriQuestions(kyu:String,type: String): List<SelectKakitoriQuestions>

    fun getKankenKyuList(): List<String>

    fun getQuestionTypeList(): List<String>


}