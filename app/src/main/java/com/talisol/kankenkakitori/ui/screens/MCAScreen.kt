package com.talisol.kankenkakitori.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.talisol.kankenkakitori.actions.QuizAction
import com.talisol.kankenkakitori.actions.TrackingAction
import com.talisol.kankenkakitori.ui.states.QuizState

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
        .fillMaxWidth(1.0F)
        .fillMaxHeight(1.0F)
        .then(modifier)
    ,
    verticalArrangement = Arrangement.SpaceEvenly
    ) {
        for (a in answerTextList.indices) {
            val buttonAnswer = answersList[a]
            val isShowCorrectAnswer = quizState.isAnswerConfirmed
                    && buttonAnswer == quizState.correctAnswer
            MCAButton(
                buttonAnswer,
                answerTextList[a],
                {
                    if (!quizState.isAnswerConfirmed) {
                        onQuizAction(QuizAction.InputAnswer(buttonAnswer))
                        onQuizAction(QuizAction.ConfirmAnswer(onTrackingAction))
                    }

                },
                textColor =
                if (isShowCorrectAnswer) {Color.Green}
                else if (
                    quizState.isAnswerConfirmed
                    && buttonAnswer == quizState.inputAnswer
                ) {Color.Red}
                else {Color.Black},
                isSelected = (buttonAnswer == quizState.inputAnswer) || isShowCorrectAnswer
            )
        }
    }
}
