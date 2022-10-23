package com.talisol.nihongodrill.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.talisol.nihongodrill.actions.QuizSettingAction
import com.talisol.nihongodrill.data.ManagerDataSource
import com.talisol.nihongodrill.quizUtils.Question
import com.talisol.nihongodrill.quizUtils.converDBquestionList
import com.talisol.nihongodrill.ui.states.QuizSelectionState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class QuizSelectionVM @Inject constructor(
    private val managerDataSource: ManagerDataSource,
) : ViewModel() {






}


