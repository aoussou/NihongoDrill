package com.talisol.nihongodrill.quizUtils

data class Question(
     val global_id: Long,
     val level: String?,
     val question_type: String,
     val source: String?,
     val reference: String?,
     val question: String,
     val answer: String,
     val target: String?,
     val mca_list: String?,
     val format: String,
     val total_correct: Long,
     val total_wrong: Long,
     val correct_streak: Long,
     val last_correct_date: String?,
     val available: Long,
     val marked_for_review: Long
)
