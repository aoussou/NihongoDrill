package com.talisol.kankenkakitori.data

import databases.kanji.KakitoriQuestion
import databases.kanji.SelectByKankenKyu

interface KanjiQuestionDataSource {

    fun getAllKankenEntriesByKyu(kyu:String): List<SelectByKankenKyu>

    fun getKankenKyuList(): List<String>



}