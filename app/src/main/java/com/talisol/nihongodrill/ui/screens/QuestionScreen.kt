package com.talisol.nihongodrill.ui.screens

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.talisol.nihongodrill.actions.QuizAction
import com.talisol.nihongodrill.actions.TrackingAction
import com.talisol.nihongodrill.ui.screens.questionScreens.TaigiScreen
import com.talisol.nihongodrill.ui.states.QuizState


@Composable
fun QuestionScreen(
    quizState: QuizState,
    onQuizAction: (QuizAction) -> Unit,
    onTrackingAction: (TrackingAction) -> Unit,
    modifier: Modifier = Modifier,
) {

        when (quizState.questionFormat){
            "type" -> KakiScreen(quizState)
            "kaki" -> KakiScreen(quizState)
            "kousei" -> KouseiScreen(quizState,onQuizAction,onTrackingAction)
            "shikibetsu" -> ShikibetsuScreen(quizState,onQuizAction)
            "taigi" -> TaigiScreen(quizState,onQuizAction)
            "goji" -> GojiScreen(quizState,onQuizAction)
            "yoji" -> KakiScreen(quizState)
            "douon" -> DouonScreen(quizState,onQuizAction)
            "busyu" -> BusyuScreen(quizState,onQuizAction,onTrackingAction)
            "bunpou" -> JLPTScreen(quizState,onQuizAction,onTrackingAction)
            "okuri" -> KakiScreen(quizState)
            "mcq" -> JLPTScreen(quizState,onQuizAction,onTrackingAction)
            else -> Log.i("DEBUG",quizState.questionFormat.toString())
        }





}
