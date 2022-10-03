package com.talisol.kankenkakitori.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.talisol.kankenkakitori.data.KanjiQuestionDataSource
import com.talisol.kankenkakitori.actions.QuizAction
import com.talisol.kankenkakitori.actions.QuizSettingAction
import com.talisol.kankenkakitori.data.ProgressDataSource
import com.talisol.kankenkakitori.ui.states.QuizSelectionState
import dagger.hilt.android.lifecycle.HiltViewModel
import databases.kanji.KakitoriQuestion
import databases.kanji.SelectByKankenKyu
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class QuestionListSelectionVM @Inject constructor(
    private val kanjiQuestionDataSource: KanjiQuestionDataSource,
) : ViewModel() {


    private val skipAllCorrect = true
    private val onlyNeverAnswered = false

    val groupsList: List<String> = kanjiQuestionDataSource.getKankenKyuList()

    private val _quizSelectionState = MutableStateFlow(QuizSelectionState())
    val quizSelectionState = _quizSelectionState.asStateFlow()

    private val _localQAlist = MutableStateFlow(listOf<SelectByKankenKyu>())

    val localQAlist = _localQAlist.asStateFlow()


    fun onAction(action: QuizSettingAction) {

        when (action) {
            is QuizSettingAction.LoadSelectedGroupQuestions -> loadSelectedQuestionGroup()
            is QuizSettingAction.SelectQuestionLevel -> setQuestionLevel(action.questionGroupID)
            is QuizSettingAction.ChooseNumberOfQuestions -> setNumberOfQuestions(action.number)
            is QuizSettingAction.MakeLocalQuizQuestionList -> makeLocalQuestionList()
        }
    }

    private fun makeLocalQuestionList() {

        if (_localQAlist.value != null) {

            _localQAlist.value = _localQAlist.value.filter {it.available.toInt() == 1 }

            if (skipAllCorrect) _localQAlist.value =
                _localQAlist.value.filter { !(it.total_correct > 0L && it.total_wrong == 0L) }
            if (onlyNeverAnswered) _localQAlist.value =
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
            _localQAlist.value = kanjiQuestionDataSource.getAllKankenEntriesByKyu(_quizSelectionState.value.groupChosen!!)
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


}


