package com.talisol.nihongodrill.viewModels

import androidx.lifecycle.ViewModel
import com.talisol.nihongodrill.actions.QuizAction
import com.talisol.nihongodrill.actions.QuizSettingAction
import com.talisol.nihongodrill.data.ManagerDataSource
import com.talisol.nihongodrill.quizUtils.Question
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CommunicatorVM @Inject constructor(
) : ViewModel() {


    private fun setQuizQuestions(onQuizAction: (QuizAction) -> Unit, questionList: List<Question>) {




    }


}


