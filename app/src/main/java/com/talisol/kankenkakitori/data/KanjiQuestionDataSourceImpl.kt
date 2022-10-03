package com.talisol.kankenkakitori.data

import com.talisol.kankenkakitori.KanjiDatabase
import databases.kanji.SelectKakitoriQuestions


class KanjiQuestionDataSourceImpl(
    db: KanjiDatabase
) : KanjiQuestionDataSource {

    private val queries = db.kakitoriQuestionQueries

    override fun selectKakitoriQuestions(kyu: String,type: String): List<SelectKakitoriQuestions> {
        return queries.selectKakitoriQuestions(kyu,type).executeAsList()
    }


    override fun getKankenKyuList(): List<String> {
        return queries.getKankenKyulist().executeAsList()
    }




}
