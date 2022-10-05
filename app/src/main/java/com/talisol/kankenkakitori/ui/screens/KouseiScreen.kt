package com.talisol.kankenkakitori.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.talisol.kankenkakitori.actions.PopupAction
import com.talisol.kankenkakitori.actions.QuizAction
import com.talisol.kankenkakitori.actions.TrackingAction
import com.talisol.kankenkakitori.quizUtils.makeTargetRed
import com.talisol.kankenkakitori.ui.states.QuizState
import java.lang.reflect.Modifier

@Composable
fun KouseiScreen(
    quizState: QuizState,
    onQuizAction: (QuizAction) -> Unit,
    onTrackingAction: (TrackingAction) -> Unit,
) {

    val listKata = listOf("ア", "イ", "ウ", "エ", "オ")
    val listText = listOf(
        "同じような意味の漢字を重ねたもの",
        "反対または対応の意味を表す字を重ねたもの",
        "上の字が下の字を修飾しているもの",
        "下の字が上の字の目的語・補語になっているもの",
        "上の字が下の字の意味を打ち消しているもの"
    )
    Column() {

        Text(
            text = quizState.question,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        if (!quizState.isAnswerConfirmed) {
            MCAScreen(
                quizState,
                listKata,
                listText,
                onQuizAction,
                onTrackingAction,
            )
        }

    }

}