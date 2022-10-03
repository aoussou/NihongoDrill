package com.talisol.kankenkakitori.data

import com.talisol.kankenkakitori.KanjiDatabase
import databases.kanji.KakitoriQuestion


class ProgressDataSourceImpl(
    db: KanjiDatabase
) : ProgressDataSource {

    private val queries = db.progressQueries

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


    override fun getTotalCorrect(id: Long): Long {
        return queries.getTotalCorrect(id).executeAsOne()
    }

    override fun getTotalWrong(id: Long): Long {
        return queries.getTotalWrong(id).executeAsOne()
    }

    override fun getCorrectStrek(id: Long): Long {
        return queries.getCorrectStreak(id).executeAsOne()
    }


}
