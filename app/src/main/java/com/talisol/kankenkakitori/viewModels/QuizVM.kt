package com.talisol.kankenkakitori.viewModels

import androidx.lifecycle.ViewModel
import com.talisol.kankenkakitori.data.KanjiQuestionDataSource
import com.talisol.kankenkakitori.quizUtils.QuizAction
import com.talisol.kankenkakitori.ui.states.QuizState
import dagger.hilt.android.lifecycle.HiltViewModel
import databases.kanji.KakitoriQuestion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class QuizVM @Inject constructor() : ViewModel() {

    private val _quizState = MutableStateFlow(QuizState())
    val quizState = _quizState.asStateFlow()


    private val _qaList = MutableStateFlow(listOf<KakitoriQuestion>())

    fun onAction(action: QuizAction) {

        when (action) {
            is QuizAction.LoadQuestionList -> loadQuestion(action.qaList)
            is QuizAction.NextQuestion -> nextQuestion()
            is QuizAction.PreviousQuestion -> previousQuestion()
            is QuizAction.ConfirmAnswer -> confirmAnswer()
            is QuizAction.StartQuiz -> startQuiz()
            is QuizAction.InputAnswer -> inputAnswer(action.answerString)
            is QuizAction.EndQuiz -> endQuiz()
            else -> {}
        }
    }

    private fun inputAnswer(answerString: String) {
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
                questionGlobalId = qas.id.toInt()
            )
        }
    }



    private fun confirmAnswer() {
        if (_quizState.value.inputAnswer != null && !quizState.value.isAnswerConfirmed) {
            _quizState.update { it.copy(isAnswerConfirmed = true) }
            if (quizState.value.inputAnswer == quizState.value.correctAnswer) {
                _quizState.update { it.copy(isAnswerCorrect = true) }
            } else {
                _quizState.update { it.copy(isAnswerCorrect = false) }
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
                        isLastQuestion = false
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
                        localQuestionNumber = quizState.value.localQuestionNumber!! + 1
                    )
                }

                selectQAs(quizState.value.localQuestionNumber!!)

                _quizState.update {
                    it.copy(
                        selectedAnswer = null,
                        isAnswerConfirmed = false,
                        isFirstQuestion = false
                    )
                }

                if (quizState.value.localQuestionNumber == _qaList.value.size - 1) _quizState.update {
                    it.copy(isLastQuestion = true)
                }

            }
        }
    }


    private fun loadQuestion(selectedQuestionsList: List<KakitoriQuestion>) {
        _qaList.value = selectedQuestionsList
    }


    private fun startQuiz() {

        selectQAs(0)
        _quizState.update {
            it.copy(
                isQuizOver = false,
                isQuizStarted = true,
                isFirstQuestion = true,
                isLastQuestion = false
            )
        }

    }




}