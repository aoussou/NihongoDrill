package com.talisol.kankenkakitori

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.talisol.kanjirecognizercompose.viewModels.KanjiRecognitionVM
import com.talisol.kankenkakitori.quizUtils.QuizAction
import com.talisol.kankenkakitori.ui.screens.KankenQuizScreen
import com.talisol.kankenkakitori.ui.screens.SelectKyuScreen
import com.talisol.kankenkakitori.ui.theme.KankenKakitoriTheme
import com.talisol.kankenkakitori.viewModels.DialogVM
import com.talisol.kankenkakitori.viewModels.DrawingVM
import com.talisol.kankenkakitori.viewModels.QuestionListSelectionVM
import com.talisol.kankenkakitori.viewModels.QuizVM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KankenKakitoriTheme {
                // A surface container using the 'background' color from the theme

                val drawingVM = viewModel<DrawingVM>()
                val drawingState by drawingVM.drawingState.collectAsState()
                val currentPath by drawingVM.currentPath.collectAsState()
                val recognizerVM = viewModel<KanjiRecognitionVM>()

                val questionListSelectionVM = viewModel<QuestionListSelectionVM>()
                val localQuizState by questionListSelectionVM.quizSelectionState.collectAsState()

                val dialogVM = viewModel<DialogVM>()
                val dialogState by dialogVM.dialogState.collectAsState()

                val quizVM = viewModel<QuizVM>()
                val quizState by quizVM.quizState.collectAsState()




                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {

                    if (
                        localQuizState.chosenNumberOfQuestions == null ||
                        localQuizState.groupChosen == null
                    ) {
                        Text(text = "SELECT LEVEL")

                        SelectKyuScreen(
                            groupsList = questionListSelectionVM.groupsList,
                            onAction = questionListSelectionVM::onAction
                        )

                    } else {

                        if (!quizState.isQuizStarted) {
                            Button(onClick = {
                                questionListSelectionVM.onAction(QuizAction.LoadSelectedGroupQuestions)
                                questionListSelectionVM.onAction(QuizAction.MakeLocalQuizQuestionList)
                                quizVM.onAction(QuizAction.LoadQuestionList(questionListSelectionVM.localQAlist.value))
                                quizVM.onAction(QuizAction.StartQuiz)


                            }) {
                                Text(text = "Start quiz, ${localQuizState.chosenNumberOfQuestions} questions of ${localQuizState.groupChosen}")
                            }
                        } else {

                            KankenQuizScreen(
                                state = quizState,
                                onAction = quizVM::onAction,
                                dialogState = dialogState,
                                currentPath = currentPath,
                                drawingState = drawingState,
                                drawingAction = drawingVM::onAction,
                            kanjiRecognizer = recognizerVM::predictKanji
                            )


                        }





//                        KanjiRecognitionScreen(
//                            currentPath,
//                            drawingState,
//                            drawingVM::onAction,
//                            recognizerVM::predictKanji
//                        )

                    }


                }

            }


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