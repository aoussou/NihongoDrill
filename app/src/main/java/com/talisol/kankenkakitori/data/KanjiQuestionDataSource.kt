package com.talisol.kankenkakitori.data

import databases.kanji.SelectKakitoriQuestionByKankenKyu

interface KanjiQuestionDataSource {

    fun getAllKankenEntriesByKyu(kyu:String): List<SelectKakitoriQuestionByKankenKyu>

    fun getKankenKyuList(): List<String>



}