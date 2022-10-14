package com.talisol.nihongodrill.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.talisol.nihongodrill.actions.QuizAction
import com.talisol.nihongodrill.actions.TrackingAction
import com.talisol.nihongodrill.ui.states.QuizState

@Composable
fun MCAScreen (
    quizState: QuizState,
    answersList: List<String>,
    answerTextList: List<String>,
    onQuizAction: (QuizAction) -> Unit,
    onTrackingAction: (TrackingAction) -> Unit,
    modifier: Modifier = Modifier,
) {


    Column(
    modifier = Modifier
        .then(modifier)
    ,
    verticalArrangement = Arrangement.SpaceEvenly
    ) {
        for (a in answerTextList.indices) {
            val textAnswer = answerTextList[a]
            val buttonAnswer = answersList[a]

            val isShowCorrectAnswer = quizState.isAnswerConfirmed
                    && textAnswer == quizState.correctAnswer
            MCAButton(
                buttonAnswer,
                textAnswer,
                {
                    if (!quizState.isAnswerConfirmed) {
                        onQuizAction(QuizAction.InputAnswer(textAnswer))
                        onQuizAction(QuizAction.ConfirmAnswer(onTrackingAction))
                    }

                },
                textColor =
                if (isShowCorrectAnswer) {Color.Green}
                else if (
                    quizState.isAnswerConfirmed
                    && textAnswer == quizState.inputAnswer
                ) {Color.Red}
                else {Color.Black},
                isSelected = (textAnswer == quizState.inputAnswer) || isShowCorrectAnswer
            )
        }
    }
}
