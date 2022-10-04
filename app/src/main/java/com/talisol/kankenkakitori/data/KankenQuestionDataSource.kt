package com.talisol.kankenkakitori.data

import databases.kanji.SelectKakitoriQuestions


interface KankenQuestionDataSource {

    fun selectKakitoriQuestions(kyu:String,type: String): List<SelectKakitoriQuestions>

    fun getKankenKyuList(): List<String>



}