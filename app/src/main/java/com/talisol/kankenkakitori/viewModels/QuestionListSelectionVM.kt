package com.talisol.kankenkakitori.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.talisol.kankenkakitori.data.KanjiQuestionDataSource
import com.talisol.kankenkakitori.quizUtils.QuizAction
import com.talisol.kankenkakitori.ui.states.QuizSelectionState
import dagger.hilt.android.lifecycle.HiltViewModel
import databases.kanji.KakitoriQuestion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class QuestionListSelectionVM @Inject constructor(
    private val kanjiQuestionDataSource: KanjiQuestionDataSource
) : ViewModel() {


    private val skipAllCorrect = true
    private val onlyNeverAnswered = false

    val groupsList: List<String> = kanjiQuestionDataSource.getKankenKyuList()

    private val _quizSelectionState = MutableStateFlow(QuizSelectionState())
    val quizSelectionState = _quizSelectionState.asStateFlow()

    private val _localQAlist = MutableStateFlow(listOf<KakitoriQuestion>())

    val localQAlist = _localQAlist.asStateFlow()

    private lateinit var _loadedQAs: List<KakitoriQuestion>

//    private lateinit var _localQAlist: List<KakitoriQuestion>

    fun onAction(action: QuizAction) {

        when (action) {
            is QuizAction.LoadSelectedGroupQuestions -> loadSelectedQuestionGroup()
            is QuizAction.SelectQuestionLevel -> setQuestionLevel(action.questionGroupID)
            is QuizAction.ChooseNumberOfQuestions -> setNumberOfQuestions(action.number)
            is QuizAction.MakeLocalQuizQuestionList -> makeLocalQuestionList()
            is QuizAction.StartQuiz -> startQuiz()
            else -> Error("NO ACTION $action")
        }
    }

    private fun makeLocalQuestionList() {

        if (_loadedQAs.isNotEmpty()) {

            _localQAlist.value = _loadedQAs
            _localQAlist.value = _localQAlist.value.filter { it.available.toInt() == 1 }

            if (skipAllCorrect) _localQAlist.value =
                _localQAlist.value.filter { !(it.total_correct > 0L && it.total_wrong == 0L) }
            else if (onlyNeverAnswered) _localQAlist.value =
                _localQAlist.value.filter { it.total_correct == 0L && it.total_wrong == 0L }

        }

        if (_quizSelectionState.value.chosenNumberOfQuestions!! <= _localQAlist.value.size) {
            _quizSelectionState.update { it.copy(actualNumberOfQuestions = _quizSelectionState.value.chosenNumberOfQuestions) }
            _localQAlist.value =
                _localQAlist.value.shuffled().take(_quizSelectionState.value.chosenNumberOfQuestions!!)
        } else {
            _quizSelectionState.update { it.copy(actualNumberOfQuestions = localQAlist.value.size) }
        }
    }

    private fun loadSelectedQuestionGroup() {
        if (quizSelectionState.value.groupChosen!=null) {
            _loadedQAs = kanjiQuestionDataSource.getAllKankenEntriesByKyu(_quizSelectionState.value.groupChosen!!)
        }
    }

    private fun setNumberOfQuestions(number: Int?) {
        _quizSelectionState.update { it.copy(chosenNumberOfQuestions = number) }
        Log.i("DEBUG","Number of question $number")
    }

    private fun setQuestionLevel(group: String?) {
        _quizSelectionState.update { it.copy(groupChosen = group) }
        Log.i("DEBUG","Selected level $group")
    }

    private fun startQuiz() {
        _quizSelectionState.update { it.copy(isQuizReady = true) }
    }


}


