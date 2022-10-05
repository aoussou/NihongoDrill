package com.talisol.kankenkakitori.data

import com.talisol.kankenkakitori.KanjiDatabase
import databases.kanji.SelectKakitoriQuestions


class KankenQuestionDataSourceImpl(
    db: KanjiDatabase
) : KankenQuestionDataSource {

    private val queries = db.kankenQuestionQueries

    override fun selectKakitoriQuestions(kyu: String,type: String): List<SelectKakitoriQuestions> {
        return queries.selectKakitoriQuestions(kyu,type).executeAsList()
    }


    override fun getKankenKyuList(): List<String> {
        return queries.getKankenKyulist().executeAsList()
    }

    override fun getQuestionTypeList(): List<String> {
        return queries.getQuestionTypeList().executeAsList()
    }


}
