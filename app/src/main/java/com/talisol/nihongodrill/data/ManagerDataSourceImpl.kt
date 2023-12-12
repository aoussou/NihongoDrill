package com.talisol.nihongodrill.data



import com.talisol.nihongodrill.NihongoDatabase
import databases.Example
import databases.Explanation
import databases.JlptQuestion
import databases.KankenQuestion


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

    override fun getWordInfo(word: String): List<Explanation>  {
        return queries.wordInfo(word).executeAsList()
    }

    override fun getWordInfoGivenReading(word: String, reading: String): Explanation? {
        return queries.wordInfoGivenReading(word, reading).executeAsOneOrNull()
    }

    override fun getExamplesList(wordId: Long): List<Example> {
        return queries.wordExample(wordId).executeAsList()
    }

}
