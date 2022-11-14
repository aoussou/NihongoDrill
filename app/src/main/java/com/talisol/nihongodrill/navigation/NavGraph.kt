package com.talisol.nihongodrill.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Path
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.talisol.nihongodrill.actions.*
import com.talisol.nihongodrill.drawingUtils.DrawingState
import com.talisol.nihongodrill.quizUtils.Question
import com.talisol.nihongodrill.ui.screens.LevelSectionScreen
import com.talisol.nihongodrill.ui.screens.QuizScreen
import com.talisol.nihongodrill.ui.screens.questionScreens.HomeScreen
import com.talisol.nihongodrill.ui.states.PopupState
import com.talisol.nihongodrill.ui.states.QuizSelectionState
import com.talisol.nihongodrill.ui.states.QuizState
import kotlinx.coroutines.flow.StateFlow

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    onQuizSettingAction: (QuizSettingAction) -> Unit,
    quizSelectionState: QuizSelectionState,
    quizState: QuizState,
    popupState: PopupState,
    onQuizAction: (QuizAction) -> Unit,
    currentPath: StateFlow<Path>,
    drawingState: StateFlow<DrawingState>,
    onDrawingAction: (DrawingAction) -> Unit,
    onKanjiRecAction: (KanjiRecAction) -> Unit,
    predictedKanji: String?,
    otherGuessesList: List<String>?,
    onTrackingAction: (TrackingAction) -> Unit,
    onPopupAction: (PopupAction) -> Unit,
    localQAlist: List<Question>,
    getQuizQuestionList: ()-> List<Question>,
    getExplanation: (String) -> String
) {

    NavHost(navController = navController, startDestination = ScreenRoute.HomeScreen.route) {

        composable(route = ScreenRoute.HomeScreen.route) {
            HomeScreen(navController = navController, onQuizSettingAction = onQuizSettingAction)
        }

        composable(route = ScreenRoute.QuestionsSelection.route) {
            LevelSectionScreen(
                navController = navController,
                quizSelectionState = quizSelectionState,
                onQuizSettingAction = onQuizSettingAction,
                localQAlist = localQAlist,
                onQuizAction = onQuizAction,
                getQuizQuestionList = getQuizQuestionList
            )
        }

        composable(route = ScreenRoute.QuizScreen.route) {
            QuizScreen(
                quizState = quizState,
                onQuizAction = onQuizAction,
                popupState = popupState,
                currentPath = currentPath,
                drawingState = drawingState,
                onDrawingAction = onDrawingAction,
                onKanjiRecAction = onKanjiRecAction,
                predictedKanji = predictedKanji,
                otherGuessesList = otherGuessesList,
                onTrackingAction = onTrackingAction,
                onPopupAction = onPopupAction,
                getExplanation = getExplanation
            )
        }

    }

}