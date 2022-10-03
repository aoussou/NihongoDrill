package com.talisol.kankenkakitori.data

import com.talisol.kankenkakitori.KanjiDatabase
import databases.kanji.SelectKakitoriQuestionByKankenKyu


class KanjiQuestionDataSourceImpl(
    db: KanjiDatabase
) : KanjiQuestionDataSource {

    private val queries = db.kakitoriQuestionQueries

    override fun getAllKankenEntriesByKyu(kyu: String): List<SelectKakitoriQuestionByKankenKyu> {
        return queries.selectKakitoriQuestionByKankenKyu(kyu).executeAsList()
    }

    override fun getKankenKyuList(): List<String> {
        return queries.getKankenKyulist().executeAsList()
    }




}
