package com.talisol.kankenkakitori.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.talisol.kankenkakitori.actions.QuizAction
import com.talisol.kankenkakitori.actions.TrackingAction
import com.talisol.kankenkakitori.ui.states.QuizState

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
    Column(
        modifier = Modifier
            .fillMaxSize()

    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = quizState.question,
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold
            )
        }



            MCAScreen2(
                quizState,
                listKata,
                listText,
                onQuizAction,
                onTrackingAction,
            )


    }

}