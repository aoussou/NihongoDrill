package com.talisol.kankenkakitori.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.talisol.kankenkakitori.actions.QuizAction
import com.talisol.kankenkakitori.actions.TrackingAction
import com.talisol.kankenkakitori.ui.states.QuizState


@Composable
fun QuestionScreen(
    quizState: QuizState,
    onQuizAction: (QuizAction) -> Unit,
    onTrackingAction: (TrackingAction) -> Unit,
    modifier: Modifier = Modifier,
) {

        when (quizState.questionType){
            "yomi" -> KakiScreen(quizState)
            "kaki" -> KakiScreen(quizState)
            "kousei" -> KouseiScreen(quizState,onQuizAction,onTrackingAction)
            "shikibetsu" -> ShikibetsuScreen(quizState,onQuizAction)
            "taigi" -> TaigiScreen(quizState,onQuizAction)
            "goji" -> GojiScreen(quizState,onQuizAction)
            "yoji" -> KakiScreen(quizState)
            "douon" -> DouonScreen(quizState,onQuizAction)
            "busyu" -> KouseiScreen(quizState,onQuizAction,onTrackingAction)
            "okuri" -> KakiScreen(quizState)
        }





}
