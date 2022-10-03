package com.talisol.kankenkakitori.data

import com.talisol.kankenkakitori.KanjiDatabase
import databases.kanji.KakitoriQuestion


class KanjiQuestionDataSourceImpl(
    db: KanjiDatabase
) : KanjiQuestionDataSource {

    private val queries = db.kanjiQuestionQueries

    override fun getAllKankenEntriesByKyu(kyu: String): List<KakitoriQuestion> {
        return queries.selectByKankenKyu(kyu).executeAsList()
    }

    override fun getKankenKyuList(): List<String> {
        return queries.getKankenKyulist().executeAsList()
    }

    override fun makeUnavailable(id: Long) {
        queries.makeUnavailable(id)
    }

    override fun markForReview(id: Long) {
        queries.markForReview(id)
    }


}
