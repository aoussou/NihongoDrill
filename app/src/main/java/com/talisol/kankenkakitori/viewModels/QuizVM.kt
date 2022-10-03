package com.talisol.kankenkakitori.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.talisol.kankenkakitori.actions.QuizAction
import com.talisol.kankenkakitori.actions.TrackingAction
import com.talisol.kankenkakitori.ui.states.QuizState
import dagger.hilt.android.lifecycle.HiltViewModel
import databases.kanji.SelectKakitoriQuestions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class QuizVM @Inject constructor() : ViewModel() {

    private val _quizState = MutableStateFlow(QuizState())
    val quizState = _quizState.asStateFlow()


    private val _qaList = MutableStateFlow(listOf<SelectKakitoriQuestions>())

    fun onAction(action: QuizAction) {

        when (action) {
            is QuizAction.LoadQuestionList -> loadQuestion(action.qaList)
            is QuizAction.NextQuestion -> nextQuestion()
            is QuizAction.PreviousQuestion -> previousQuestion()
            is QuizAction.ConfirmAnswer -> confirmAnswer(action.trackingOnAction)
            is QuizAction.StartQuiz -> startQuiz()
            is QuizAction.InputAnswer -> inputAnswer(action.answerString)
            is QuizAction.EndQuiz -> endQuiz()
            is QuizAction.SelectWrongKanji -> selectWrongKanji(action.kanji,action.kanjiInd)
            is QuizAction.SetQuizType -> setQuizType(action.questionType)
        }
    }

    private fun inputAnswer(answerString: String?) {
        _quizState.update { it.copy(inputAnswer = answerString) }
    }

    private fun endQuiz() {
        _quizState.update {
            it.copy(
                isQuizStarted = false,
                isQuizOver = true,
                inputAnswer = null,
                isAnswerConfirmed = false
            )
        }
    }


    private fun selectQAs(localQuestionNumber: Int) {
        val qas = _qaList.value[localQuestionNumber]
        _quizState.update {
            it.copy(
                localQuestionNumber = localQuestionNumber,
                question = qas.question,
                correctAnswer = qas.answer,
                target = qas.target,
                questionGlobalId = qas.global_id.toInt()
            )
        }
    }

    private fun confirmAnswer(trackingOnAction: (TrackingAction)-> Unit) {
        if (!_quizState.value.isAnswerConfirmed) {

            _quizState.update { it.copy(isAnswerConfirmed = true) }
            val id = _quizState.value.questionGlobalId!!

            if (_quizState.value.questionType == "goji") {
                if (_quizState.value.selectedWrongKanji != _quizState.value.target) {
                    _quizState.update {it.copy(isAnswerCorrect = false)
                    }
                    trackingOnAction(TrackingAction.AddOneWrong(id))
                    return
                }
            }

            if (_quizState.value.inputAnswer == _quizState.value.correctAnswer) {
                _quizState.update { it.copy(isAnswerCorrect = true) }
                trackingOnAction(TrackingAction.AddOneCorrect(id))
            } else {
                _quizState.update { it.copy(isAnswerCorrect = false) }
                trackingOnAction(TrackingAction.AddOneWrong(id))
            }

        }
    }



    private fun previousQuestion() {

        if (quizState.value.localQuestionNumber != null) {

            if (quizState.value.localQuestionNumber!! > 0) {
                _quizState.update {
                    it.copy(
                        localQuestionNumber = quizState.value.localQuestionNumber!! - 1
                    )
                }

                selectQAs(quizState.value.localQuestionNumber!!)
                _quizState.update {
                    it.copy(
                        selectedAnswer = null,
                        isAnswerConfirmed = false,
                        isLastQuestion = false,
                        isAnswerCorrect = null,
                        selectedWrongKanji = null
                    )
                }

                if (quizState.value.localQuestionNumber == 0) _quizState.update { it.copy(isFirstQuestion = true) }

            }
        }
    }


    private fun nextQuestion() {
        if (quizState.value.localQuestionNumber != null) {
            if (quizState.value.localQuestionNumber!! < _qaList.value.size - 1) {
                _quizState.update {
                    it.copy(
                        localQuestionNumber = quizState.value.localQuestionNumber!! + 1,
                        inputAnswer = null
                    )
                }

                selectQAs(quizState.value.localQuestionNumber!!)
                _quizState.update {
                    it.copy(
                        selectedAnswer = null,
                        isAnswerConfirmed = false,
                        isFirstQuestion = false,
                        isAnswerCorrect = null
                    )
                }
                if (quizState.value.localQuestionNumber == _qaList.value.size - 1) _quizState.update {
                    it.copy(isLastQuestion = true)
                }
            }
        }
    }


    private fun loadQuestion(selectedQuestionsList: List<SelectKakitoriQuestions>) {
        _qaList.value = selectedQuestionsList
    }


    private fun startQuiz() {

        selectQAs(0)
        _quizState.update {
            it.copy(
                isQuizOver = false,
                isQuizStarted = true,
                isFirstQuestion = true,
                isLastQuestion = _qaList.value.size == 1
            )
        }
    }

    private fun selectWrongKanji(kanji: String?,kanjiInd: Int?) {
        _quizState.update { it.copy(selectedWrongKanji = kanji, selectedWrongKanjInd = kanjiInd) }
    }

    private fun setQuizType(type: String?) {
        _quizState.update { it.copy(questionType = type) }
    }

}