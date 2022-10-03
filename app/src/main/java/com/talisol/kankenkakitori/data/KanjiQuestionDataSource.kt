package com.talisol.kankenkakitori.data

import databases.kanji.KakitoriQuestion

interface KanjiQuestionDataSource {

    fun getAllKankenEntriesByKyu(kyu:String): List<KakitoriQuestion>

    fun getKankenKyuList(): List<String>



}