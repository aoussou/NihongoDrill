package com.talisol.kankenkakitori.viewModels

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.talisol.kankenkakitori.data.KanjiQuestionDataSource
import com.talisol.kankenkakitori.quizUtils.DialogState
import com.talisol.kankenkakitori.quizUtils.QuizAction
import com.talisol.kankenkakitori.quizUtils.QuizState
import databases.kanji.KakitoriQuestion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class QuizVM @Inject constructor(
    private val kanjiQuestionDataSource: KanjiQuestionDataSource
) : ViewModel() {

    private val skipAllCorrect = true
    private val onlyNeverAnswered = false

    private lateinit var localQAlist: List<KakitoriQuestion>
    private var originalListNumber = 0

    val groupsList: List<Long> = kanjiQuestionDataSource.getKankenKyuList()

    private val _state = MutableStateFlow(QuizState())
    val state = _state.asStateFlow()

    private val _dialogState = MutableStateFlow(DialogState())
    val dialogState = _dialogState.asStateFlow()

    private lateinit var loadedQAs: List<KakitoriQuestion>

    fun onAction(action: QuizAction) {

        when (action) {
            is QuizAction.NextQuestion -> nextQuestion()
            is QuizAction.PreviousQuestion -> previousQuestion()
            is QuizAction.ConfirmAnswer -> confirmAnswer()
            is QuizAction.StartQuiz -> startQuiz()
            is QuizAction.SelectQuestionGroup -> setQuestionGroup(action.questionGroupID)
            is QuizAction.InputAnswer -> inputAnswer(action.answerString)
            is QuizAction.EndQuiz -> endQuiz()
            is QuizAction.ChooseNumberOfQuestions -> setNumberOfQuestions(action.number)

            is QuizAction.StopAsking -> stopAsking()
            is QuizAction.ShowAlertDialog -> showAlertDialog(action.dialogState)
            is QuizAction.CloseAlertDialog -> closeAlertDialog()

//            is QuizAction.AddOneCorrect -> addOneCorrect()
//            is QuizAction.AddOneWrong -> addOneWrong()
//            is QuizAction.SubtractOneCorrect -> subtractOneCorrect()
//            is QuizAction.SubtractOneWrong -> subtractOneWrong()
//            is QuizAction.MarkForReview -> markForReview()

            else -> {}
        }
    }

    private fun closeAlertDialog() {
        _dialogState.value = DialogState()
    }

    private fun showAlertDialog(newDialogState: DialogState) {

        _dialogState.update { it.copy(
            isAlertDialogShown = true,
            dialogText = newDialogState.dialogText,
            onConfirmAction = newDialogState.onConfirmAction
        )
        }
    }

    private fun stopAsking() {
        kanjiQuestionDataSource.makeUnavailable(state.value.questionGlobalId!!.toLong())
    }

    private fun setNumberOfQuestions(number: Int?) {
        _state.update { it.copy(chosenNumberOfQuestions = number)}
    }

    private fun inputAnswer(answerString: String) {
        _state.update { it.copy(inputAnswer = answerString) }
    }

    private fun endQuiz() {
        _state.update { it.copy(
            isQuizStarted = false,
            isQuizOver = true,
            inputAnswer = null,
            isAnswerConfirmed = false
        )}
    }


    private fun selectQAs(localQuestionNumber: Int) {

//        timeSinceLastCorrect()
//        originalListNumber = localQAlist[questionNumber-1]
        val qas = localQAlist[localQuestionNumber]
        _state.update {
            it.copy(
                localQuestionNumber = localQuestionNumber,
                question = qas.question,
                correctAnswer = qas.answer,
                target = qas.target,
                questionGlobalId = qas.id.toInt()
            )
        }
    }

    private fun makeLocalQuestionList() {

        localQAlist = loadedQAs
        localQAlist = localQAlist.filter { it.available.toInt() == 1 }

        if (skipAllCorrect) localQAlist =
            localQAlist.filter { !(it.total_correct > 0L && it.total_wrong == 0L) }

        else if (onlyNeverAnswered) localQAlist =
            localQAlist.filter { it.total_correct == 0L && it.total_wrong == 0L}

        Log.i("DEBUG", "*******************************")
        Log.i("DEBUG", localQAlist.size.toString())
        Log.i("DEBUG", "*******************************")

        if (_state.value.chosenNumberOfQuestions != null) {
            if (_state.value.chosenNumberOfQuestions!! <= localQAlist.size) {
                _state.update { it.copy(actualNumberOfQuestions = state.value.chosenNumberOfQuestions)}
                localQAlist = localQAlist.shuffled().take(state.value.chosenNumberOfQuestions!!)
            } else {
                _state.update { it.copy(actualNumberOfQuestions = localQAlist.size)}
            }
        }
    }


    private fun confirmAnswer() {
        if (_state.value.inputAnswer != null && !state.value.isAnswerConfirmed) {
            _state.update { it.copy(isAnswerConfirmed = true) }

            if (state.value.inputAnswer == state.value.correctAnswer) {
                _state.update { it.copy(isAnswerCorrect = true)}

//                addOneCorrect()
//                updateCorrectStreak()
//                updateLastCorrect()

            } else {
                _state.update { it.copy(isAnswerCorrect = false)}

//                addOneWrong()
//                resetCorrectStreak()

            }
        }
    }


    private fun previousQuestion() {

        if (state.value.localQuestionNumber != null) {

            if (state.value.localQuestionNumber!! > 0) {
                _state.update { it.copy(
                    localQuestionNumber = state.value.localQuestionNumber!! - 1
                )}

                selectQAs(state.value.localQuestionNumber!!)
                _state.update {
                    it.copy(
                        selectedAnswer = null,
                        isAnswerConfirmed = false,
                        isLastQuestion = false
                    )
                }

                if (state.value.localQuestionNumber == 0) _state.update { it.copy(isFirstQuestion = true)}

            }
        }
    }


    private fun nextQuestion() {

        if (state.value.localQuestionNumber != null) {

            if (state.value.localQuestionNumber!! < localQAlist.size - 1) {
                _state.update { it.copy(
                    localQuestionNumber = state.value.localQuestionNumber!! + 1
                )
                }

                selectQAs(state.value.localQuestionNumber!!)

                _state.update {
                    it.copy(
                        selectedAnswer = null,
                        isAnswerConfirmed = false,
                        isFirstQuestion = false
                    )
                }

                if (state.value.localQuestionNumber == localQAlist.size - 1) _state.update { it.copy(isLastQuestion = true) }

            }
        }
    }


        private fun loadSelectedQuestionGroup() {
        if (!_state.value.questionGroupId.isNullOrBlank()) {
            loadedQAs = kanjiQuestionDataSource.getAllKankenEntriesByKyu(
                _state.value.questionGroupId!!.toLong()
            )
        }
    }


    private fun startQuiz() {
        loadSelectedQuestionGroup()
        makeLocalQuestionList()
        selectQAs(0)
        _state.update {
            it.copy(
                isQuizOver = false,
                isQuizStarted = true,
                isFirstQuestion = true,
                isLastQuestion = false
            )
        }

    }

    private fun setQuestionGroup(questionGroupId: String) {
        _state.update { it.copy(questionGroupId = questionGroupId)}
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun timeSinceLastCorrect() {

        val lastCorrectDate = localQAlist[originalListNumber].last_correct_date

        if (lastCorrectDate != null) {

            val currentTime = LocalDateTime.now()
            val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
            val lastCorrectDateFormatted = LocalDateTime.parse(lastCorrectDate, formatter)
//            val timeDiff = ChronoUnit.MINUTES.between(lastCorrectDateFormatted, currentTime)

        }

    }


    ////////////////////////////////////////////////////////////////////////////////////////////////

//    private fun markForReview() {
//        kanjiQuestionDataSource.markForReview(state.questionGlobalId!!.toLong())
//    }
//
//    private fun addOneCorrect() {
//        val newNCorrect = localQAlist[originalListNumber].total_correct + 1
//        kanjiQuestionDataSource.updateTotalCorrectNumber(state.questionGlobalId!!.toLong(), newNCorrect)
//    }
//
//    private fun addOneWrong() {
//        val newNWrong = localQAlist[originalListNumber].total_wrong + 1
//        kanjiQuestionDataSource.updateTotalWrongNumber(state.questionGlobalId!!.toLong(), newNWrong)
//    }
//
//    private fun subtractOneCorrect() {
//        val newNCorrect = localQAlist[originalListNumber].total_correct - 1
//        kanjiQuestionDataSource.updateTotalCorrectNumber(state.questionGlobalId!!.toLong(), newNCorrect)
//    }
//
//    private fun subtractOneWrong() {
//        val newNWrong = localQAlist[originalListNumber].total_wrong - 1
//        kanjiQuestionDataSource.updateTotalWrongNumber(state.questionGlobalId!!.toLong(), newNWrong)
//    }
//
//

//
//    private fun updateCorrectStreak() {
//        val newCorrectStreak = localQAlist[originalListNumber].correct_streak + 1
//        kanjiQuestionDataSource.updateCorrectStreak(
//            state.questionGlobalId!!.toLong(),
//            newCorrectStreak
//        )
//    }
//
//    private fun resetCorrectStreak() {
//        kanjiQuestionDataSource.updateCorrectStreak(state.questionGlobalId!!.toLong(), 0)
//    }
//
//    private fun updateLastCorrect() {
//        kanjiQuestionDataSource.updateLastCorrectDate(
//            state.questionGlobalId!!.toLong(),
//            LocalDateTime.now().toString()
//        )
//    }




}