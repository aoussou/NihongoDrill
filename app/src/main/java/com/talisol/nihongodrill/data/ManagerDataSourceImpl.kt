package com.talisol.nihongodrill.data

import com.talisol.nihongodrill.NihongoDatabase
import databases.JlptQuestion
import databases.KankenQuestion
import databases.WordExplanation


class ManagerDataSourceImpl(
    db: NihongoDatabase
) : ManagerDataSource {

    private val queries = db.managerQueries

    override fun getKankenKyuList(): List<String> {
        return queries.getKankenKyuList().executeAsList()
    }

    override fun getKankenQuestionTypeList(kyu: String): List<String> {
        return queries.getKankenQuestionTypeList(kyu).executeAsList()
    }


    override fun getAllKankenQuestions(): List<KankenQuestion> {
        return queries.kankenQuestion().executeAsList()
    }


    override fun getJLPTlevelList(): List<String> {
        return queries.getJLPTlevelList().executeAsList()
    }

    override fun getJLPTQuestionTypeList(level: String): List<String> {
        return queries.getJLPTquestionType(level).executeAsList()
    }

//    override fun selectJLPTQuestions(level: String, type: String): List<JlptQuestion> {
//        return queries.jlptQuestion(level,type).executeAsList()
//    }

    override fun getAllJLPTQuestions(): List<JlptQuestion> {
        return queries.jlptQuestion().executeAsList()
    }

    override fun getWordExplanation(word: String): WordExplanation?  {
        return queries.wordExplanation(word).executeAsOneOrNull()
    }

}
