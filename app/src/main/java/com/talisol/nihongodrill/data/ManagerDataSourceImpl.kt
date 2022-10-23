package com.talisol.nihongodrill.data

import com.talisol.nihongodrill.NihongoDatabase
import databases.KankenQuestion


class ManagerDataSourceImpl(
    db: NihongoDatabase
) : ManagerDataSource {

    private val queries = db.managerQueries

    override fun getKankenKyuList(): List<Long> {
        return queries.getKankenKyuList().executeAsList()
    }

    override fun getKankenQuestionList(kyu: Long,type: String): List<KankenQuestion> {
        return queries.kankenQuestion(kyu,type).executeAsList()
    }

    override fun selectKankenQuestions(kyu: Long, type: String): List<KankenQuestion> {
        return queries.kankenQuestion(kyu,type).executeAsList()
    }


}
