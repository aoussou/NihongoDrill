package com.talisol.kankenkakitori.data

import com.talisol.kankenkakitori.KanjiDatabase
import databases.kanji.KakitoriQuestion
import databases.kanji.SelectByKankenKyu


class KanjiQuestionDataSourceImpl(
    db: KanjiDatabase
) : KanjiQuestionDataSource {

    private val queries = db.kanjiQuestionQueries

    override fun getAllKankenEntriesByKyu(kyu: String): List<SelectByKankenKyu> {
        return queries.selectByKankenKyu(kyu).executeAsList()
    }

    override fun getKankenKyuList(): List<String> {
        return queries.getKankenKyulist().executeAsList()
    }




}
