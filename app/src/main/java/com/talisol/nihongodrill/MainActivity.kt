package com.talisol.nihongodrill

//import android.os.Build
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.annotation.RequiresApi
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.navigation.compose.rememberNavController
//import com.talisol.nihongodrill.navigation.SetupNavGraph
//import com.talisol.nihongodrill.ui.theme.NihongoDrillTheme
//import com.talisol.nihongodrill.viewModels.DrawingVM
//import com.talisol.nihongodrill.viewModels.KanjiRecognitionVM
//import com.talisol.nihongodrill.viewModels.PopupVM
//import com.talisol.nihongodrill.viewModels.ProgressTrackingVM
//import com.talisol.nihongodrill.viewModels.QuizSettingVM
//import com.talisol.nihongodrill.viewModels.QuizVM
//import dagger.hilt.android.AndroidEntryPoint
//
//@AndroidEntryPoint
//class MainActivity : ComponentActivity() {
//    @RequiresApi(Build.VERSION_CODES.P)
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//
//            val quizSettingVM = viewModel<QuizSettingVM>()
//            val quizSelectionState by quizSettingVM.quizSelectionState.collectAsState()
//            val localQAlist by quizSettingVM.localQAlist.collectAsState()
//
//            val drawingVM = viewModel<DrawingVM>()
//            val drawingState = drawingVM.drawingState
//            val currentPath = drawingVM.currentPath
//
//            val recognizerVM = viewModel<KanjiRecognitionVM>()
//            val otherGuessesList = recognizerVM.otherGuessesList.collectAsState()
//            val predictedKanji = recognizerVM.predictedKanji.collectAsState()
//
//
//            val popupVM = viewModel<PopupVM>()
//            val popupState by popupVM.popupState.collectAsState()
//
//            val quizVM = viewModel<QuizVM>()
//            val quizState by quizVM.quizState.collectAsState()
//
//            val trackingVM = viewModel<ProgressTrackingVM>()
//
//            NihongoDrillTheme {
//
//
//                val navController = rememberNavController()
//                SetupNavGraph(
//                    navController = navController,
//                    onQuizSettingAction = quizSettingVM::onAction,
//                    quizSelectionState = quizSelectionState,
//                    quizState = quizState,
//                    onQuizAction = quizVM::onAction,
//                    popupState = popupState,
//                    currentPath = currentPath,
//                    drawingState = drawingState,
//                    onDrawingAction = drawingVM::onAction,
//                    onKanjiRecAction = recognizerVM::onAction,
//                    predictedKanji = predictedKanji.value,
//                    otherGuessesList = otherGuessesList.value,
//                    onTrackingAction = trackingVM::onAction,
//                    onPopupAction = popupVM::onAction,
//                    localQAlist = localQAlist,
//                    getQuizQuestionList = quizSettingVM::getQuizQuestionList,
//                    getExplanation = quizVM::getExplanation
//                )
//
//
//            }
//        }
//
//    }
//
//
//}
//
//
//

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {

}