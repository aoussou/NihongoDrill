package com.talisol.kankenkakitori.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.talisol.kankenkakitori.actions.QuizSettingAction
import com.talisol.kankenkakitori.data.KanjiQuestionDataSource
import com.talisol.kankenkakitori.ui.states.QuizSelectionState
import dagger.hilt.android.lifecycle.HiltViewModel
import databases.kanji.SelectKakitoriQuestions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class QuizSettingVM @Inject constructor(
    private val kanjiQuestionDataSource: KanjiQuestionDataSource,
) : ViewModel() {


    private val skipAllCorrect = false
    private val onlyNeverAnswered = false
    private val onlyMarked = true

    val quizTypesList = listOf("kaki","goji")

    val groupsList: List<String> = kanjiQuestionDataSource.getKankenKyuList()

    private val _quizSelectionState = MutableStateFlow(QuizSelectionState())
    val quizSelectionState = _quizSelectionState.asStateFlow()

    private val _localQAlist = MutableStateFlow(listOf<SelectKakitoriQuestions>())

    val localQAlist = _localQAlist.asStateFlow()


    fun onAction(action: QuizSettingAction) {

        when (action) {
            is QuizSettingAction.LoadSelectedGroupQuestions -> loadSelectedQuestionGroup()
            is QuizSettingAction.SelectQuestionLevel -> setQuestionLevel(action.questionGroupID)
            is QuizSettingAction.ChooseNumberOfQuestions -> setNumberOfQuestions(action.number)
            is QuizSettingAction.ChooseQuestionType -> setQuestionType(action.type)
            is QuizSettingAction.MakeLocalQuizQuestionList -> makeLocalQuestionList()
        }
    }

    private fun makeLocalQuestionList() {

        _localQAlist.value = _localQAlist.value.filter {it.available.toInt() == 1 }

        if (skipAllCorrect) _localQAlist.value =
            _localQAlist.value.filter { !(it.total_correct > 0L && it.total_wrong == 0L) }
        if (onlyNeverAnswered) _localQAlist.value =
            _localQAlist.value.filter { it.total_correct == 0L && it.total_wrong == 0L }
        if (onlyMarked) _localQAlist.value =
            _localQAlist.value.filter { it.marked_for_review == 1L}

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
            _localQAlist.value = kanjiQuestionDataSource.selectKakitoriQuestions(
                _quizSelectionState.value.groupChosen!!,
                _quizSelectionState.value.typeChosen!!
            )
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

    private fun setQuestionType(group: String?) {
        _quizSelectionState.update { it.copy(typeChosen = group) }
        Log.i("DEBUG","Selected level $group")
    }


}


