package com.talisol.kankenkakitori.data

import com.talisol.kankenkakitori.KanjiDatabase
import databases.kanji.KakitoriQuestion


class KanjiQuestionDataSourceImpl(
    db: KanjiDatabase
): KanjiQuestionDataSource{

    private val queries = db.kanjiQuestionQueries

    override fun getAllKankenEntriesByKyu(kyu:String): List<KakitoriQuestion> {
        return queries.selectByKankenKyu(kyu).executeAsList()
    }

    override fun updateTotalCorrectNumber(id: Long, nCorrect: Long) {
        queries.updateTotalCorrect(nCorrect,id)
    }

    override fun updateTotalWrongNumber(id: Long, nWrong: Long) {
        queries.updateTotalWrong(nWrong,id)
    }

    override fun updateCorrectStreak(id: Long, nCorrectStreak: Long) {
        queries.updateCorrectStreak(nCorrectStreak,id)
    }

    override fun updateLastCorrectDate(id: Long, lastCorrectDate: String) {
        queries.updateLastCorrectDate(lastCorrectDate,id)
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
