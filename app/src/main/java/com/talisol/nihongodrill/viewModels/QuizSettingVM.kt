package com.talisol.nihongodrill.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.talisol.nihongodrill.actions.QuizSettingAction
import com.talisol.nihongodrill.data.ManagerDataSource
import com.talisol.nihongodrill.quizUtils.Question
import com.talisol.nihongodrill.quizUtils.convertDBquestionList
import com.talisol.nihongodrill.ui.states.QuizSelectionState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class QuizSettingVM @Inject constructor(
    private val managerDataSource: ManagerDataSource,
) : ViewModel() {


    private val skipAllCorrect = true
    private val sortByFewestTries = true
    private val onlyNeverAnswered = false
    private val onlyMarked = false

    val quizTypesList: List<String> = listOf("JLPT", "漢検")

    private val _quizSelectionState = MutableStateFlow(QuizSelectionState())
    val quizSelectionState = _quizSelectionState.asStateFlow()

    private val _localQAlist = MutableStateFlow(listOf<Question>())

    val localQAlist = _localQAlist.asStateFlow()


    fun onAction(action: QuizSettingAction) {

        when (action) {
            is QuizSettingAction.SelectCategory -> setCategory(action.category)
            is QuizSettingAction.SelectQuestionLevel -> setQuestionLevel(action.level)
            is QuizSettingAction.SelectQuestionType -> setQuestionType(action.type)
            is QuizSettingAction.SelectQuestionSource -> setQuestionSource(action.source)
            is QuizSettingAction.SelectQuestionReference -> setQuestionReference(action.reference)
            is QuizSettingAction.LoadSelectedGroupQuestions -> loadSelectedQuestionGroup()
            is QuizSettingAction.ChooseNumberOfQuestions -> setNumberOfQuestions(action.number)
            is QuizSettingAction.MakeLocalQuizQuestionList -> makeLocalQuestionList()
            is QuizSettingAction.ApplyAllQuestionSelectors -> applyAllQuestionSelectors()
        }
    }

    private fun makeLocalQuestionList() {

        _localQAlist.value = _localQAlist.value.filter { it.available.toInt() == 1 }
        _localQAlist.value = _localQAlist.value.sortedWith(
            compareBy<Question> { it.total_correct + it.total_wrong }
                .thenBy { it.correct_streak }
                .thenByDescending { it.total_wrong }
                .thenBy { it.total_correct }
        )

        if (onlyNeverAnswered) _localQAlist.value =
            _localQAlist.value.filter { it.total_correct == 0L && it.total_wrong == 0L }

        if (skipAllCorrect) _localQAlist.value =
            _localQAlist.value.filter { !(it.total_correct > 0L && it.total_wrong == 0L) }


        if (onlyMarked) _localQAlist.value =
            _localQAlist.value.filter { it.marked_for_review == 1L }

        if (_quizSelectionState.value.chosenNumberOfQuestions!! <= _localQAlist.value.size) {
            _quizSelectionState.update { it.copy(actualNumberOfQuestions = _quizSelectionState.value.chosenNumberOfQuestions) }
            _localQAlist.value =
                _localQAlist.value.take(_quizSelectionState.value.chosenNumberOfQuestions!!)
            _localQAlist.value =
                _localQAlist.value.shuffled()

        } else {
            _quizSelectionState.update { it.copy(actualNumberOfQuestions = localQAlist.value.size) }
        }
    }

    private fun loadSelectedQuestionGroup() {
        if (quizSelectionState.value.selectedCategory != null) {
            if (_quizSelectionState.value.selectedCategory == "jlpt") {
                val questionsList = managerDataSource.getAllJLPTQuestions()
                _localQAlist.value = convertDBquestionList(questionsList)
            }else if (_quizSelectionState.value.selectedCategory == "kanken") {
                val questionsList = managerDataSource.getAllKankenQuestions()
                _localQAlist.value = convertDBquestionList(questionsList)
            }


            updateAllSelectionLists()
        }
    }

    private fun setCategory(category: String?) {
        _quizSelectionState.update { it.copy(selectedCategory = category) }
        Log.i("DEBUG", "Selected category $category")
    }

    private fun setNumberOfQuestions(number: Int?) {
        _quizSelectionState.update { it.copy(chosenNumberOfQuestions = number) }
        Log.i("DEBUG", "Number of question $number")
    }

    private fun setQuestionLevel(level: String?) {
        _quizSelectionState.update { it.copy(selectedLevel = level) }
        if (level != null) filterQuestionsByLevel()
        else applyAllQuestionSelectors()
        updateAllSelectionLists()
        Log.i("DEBUG", "Selected level $level")
    }


    private fun filterQuestionsByLevel() {
        _localQAlist.value =
            _localQAlist.value.filter { it.level == _quizSelectionState.value.selectedLevel }
    }

    private fun setQuestionType(type: String?) {
        _quizSelectionState.update { it.copy(selectedQuestionType = type) }
        Log.i("DEBUG", "Selected type $type")
        if (type != null) filterQuestionsByType()
        else applyAllQuestionSelectors()
        Log.i("DEBUG", "#q ${_localQAlist.value.size}")
        updateAllSelectionLists()

    }


    private fun filterQuestionsByType() {
        _localQAlist.value =
            _localQAlist.value.filter { it.question_type == _quizSelectionState.value.selectedQuestionType }
    }


    private fun setQuestionSource(source: String?) {
        _quizSelectionState.update { it.copy(selectedQuestionSource = source) }
        if (source != null) filterQuestionsBySource()
        else applyAllQuestionSelectors()
        updateAllSelectionLists()
        Log.i("DEBUG", "Selected source $source")
    }


    private fun filterQuestionsBySource() {
        _localQAlist.value =
            _localQAlist.value.filter { it.source == _quizSelectionState.value.selectedQuestionSource }
    }


    private fun setQuestionReference(reference: String?) {
        _quizSelectionState.update { it.copy(selectedQuestionRef = reference) }
        if (reference != null) filterQuestionsByReference()
        else applyAllQuestionSelectors()
        updateAllSelectionLists()

        Log.i("DEBUG", "Selected reference $reference")
    }


    private fun filterQuestionsByReference() {
        _localQAlist.value =
            _localQAlist.value.filter { it.reference == _quizSelectionState.value.selectedQuestionRef }
    }

    private fun applyAllQuestionSelectors() {
        loadSelectedQuestionGroup()
        if (_quizSelectionState.value.selectedLevel != null) filterQuestionsByLevel()
        if (_quizSelectionState.value.selectedQuestionType != null) filterQuestionsByType()
        if (_quizSelectionState.value.selectedQuestionSource != null) filterQuestionsBySource()
        if (_quizSelectionState.value.selectedQuestionRef != null) filterQuestionsByReference()
    }

    private fun updateAllSelectionLists() {

        _quizSelectionState.update { it ->
            it.copy(levelList = _localQAlist.value.map { it.level!! }.distinct())
        }

        _quizSelectionState.update { it ->
            it.copy(questionTypeList = _localQAlist.value.map { it.question_type }
                .distinct())
        }

        _quizSelectionState.update { it ->
            it.copy(sourceList = _localQAlist.value.map { it.source!! }
                .distinct())
        }

        _quizSelectionState.update { it ->
            it.copy(referenceList = _localQAlist.value.map { it.reference!! }
                .distinct())
        }
    }

}


