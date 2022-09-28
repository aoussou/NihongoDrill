package com.talisol.kankenkakitori

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.talisol.kankenkakitori.quizUtils.DialogState
import com.talisol.kankenkakitori.quizUtils.QuizAction
import com.talisol.kankenkakitori.ui.screens.QuizAlertDialog
import com.talisol.kankenkakitori.ui.screens.SelectKyuScreen
import com.talisol.kankenkakitori.ui.theme.KankenKakitoriTheme
import com.talisol.kankenkakitori.viewModels.DialogVM
import com.talisol.kankenkakitori.viewModels.QuestionListSelectionVM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KankenKakitoriTheme {
                // A surface container using the 'background' color from the theme

                val questionListSelectionVM = viewModel<QuestionListSelectionVM>()
                val localQuizState = questionListSelectionVM.quizSelectionState.collectAsState()

                val dialogVM = viewModel<DialogVM>()
                val dialogState = dialogVM.dialogState.collectAsState()

                val startQuiz = DialogState(
                    dialogText =
                    "Start quiz, ${localQuizState.value.actualNumberOfQuestions} questions of" +
                            " level ${localQuizState.value.groupChosen}",
                    onConfirmAction = {
                        dialogVM.onAction(QuizAction.StopAsking)
                        questionListSelectionVM.onAction(QuizAction.CloseAlertDialog)
                    }
                )

                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {

                    if (
                        localQuizState.value.chosenNumberOfQuestions == null ||
                        localQuizState.value.groupChosen == null
                    ) {
                        Text(text = "SELECT LEVEL")

                        SelectKyuScreen(
                            groupsList = questionListSelectionVM.groupsList,
                            onAction = questionListSelectionVM::onAction
                        )

                    } else {

                        if (dialogState.value.isAlertDialogShown) {
                            QuizAlertDialog(dialogState = startQuiz, onAction = dialogVM::onAction)
                        }





                    }



                    }

                }


//                val drawingVM = viewModel<DrawingVM>()
//                val drawingState by drawingVM.drawingState.collectAsState()
//                val currentPath by drawingVM.currentPath.collectAsState()
//                val recognizerVM = viewModel<KanjiRecognitionVM>()

//                KanjiRecognitionScreen(
//                    currentPath,
//                    drawingState,
//                    drawingVM::onAction,
//                    recognizerVM::predictKanji
//                )

            }
        }

}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    KankenKakitoriTheme {
        Greeting("Android")
    }
}