package com.talisol.kankenkakitori.viewModels

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.talisol.kankenkakitori.data.KanjiQuestionDataSource
import com.talisol.kankenkakitori.quizUtils.TrackingAction
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class ProgressTrackingVM @Inject constructor(
    private val kanjiQuestionDataSource: KanjiQuestionDataSource
) : ViewModel() {

    fun onAction(action: TrackingAction) {
        when (action) {
            is TrackingAction.AddOneCorrect -> addOneCorrect(action.id)
            is TrackingAction.AddOneWrong -> addOneWrong(action.id)
            is TrackingAction.SubtractOneCorrect -> subtractOneCorrect(action.id)
            is TrackingAction.SubtractOneWrong -> subtractOneWrong(action.id)
            is TrackingAction.StopAsking -> stopAsking(action.id)
            is TrackingAction.MarkForReview -> markForReview(action.id)
        }
    }



    private fun addOneCorrect(id:Int) {
        val newTotalCorrect = kanjiQuestionDataSource.getTotalCorrect(id.toLong()) + 1
        kanjiQuestionDataSource.updateTotalCorrectNumber(id.toLong(), newTotalCorrect)
    }

    private fun addOneWrong(id:Int) {
        val newTotalWrong = kanjiQuestionDataSource.getTotalWrong(id.toLong()) + 1
        kanjiQuestionDataSource.updateTotalWrongNumber(id.toLong(), newTotalWrong)
    }

    private fun subtractOneCorrect(id:Int) {
        val newTotalCorrect = kanjiQuestionDataSource.getTotalCorrect(id.toLong()) - 1
        kanjiQuestionDataSource.updateTotalCorrectNumber(id.toLong(), newTotalCorrect)
    }

    private fun subtractOneWrong(id:Int) {
        val newTotalWrong = kanjiQuestionDataSource.getTotalWrong(id.toLong()) - 1
        kanjiQuestionDataSource.updateTotalWrongNumber(id.toLong(), newTotalWrong) }

    private fun updateCorrectStreak(id:Int) {

        val newCorrectStreak = kanjiQuestionDataSource.getCorrectStrek(id.toLong()) + 1
        kanjiQuestionDataSource.updateCorrectStreak(
            id.toLong(),
            newCorrectStreak
        )
    }

    private fun resetCorrectStreak(id:Int) {
        kanjiQuestionDataSource.updateCorrectStreak(id.toLong(), 0)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateLastCorrect(id:Int) {
        kanjiQuestionDataSource.updateLastCorrectDate(
            id.toLong(),
            LocalDateTime.now().toString()
        )
    }

    private fun stopAsking(id:Int) {
        kanjiQuestionDataSource.makeUnavailable(id.toLong())
    }

    private fun markForReview(id:Int) {
        kanjiQuestionDataSource.markForReview(id.toLong())
    }



    //    @RequiresApi(Build.VERSION_CODES.O)
//    private fun timeSinceLastCorrect() {
//
//        val lastCorrectDate = localQAlist[originalListNumber].last_correct_date
//
//        if (lastCorrectDate != null) {
//
//            val currentTime = LocalDateTime.now()
//            val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
//            val lastCorrectDateFormatted = LocalDateTime.parse(lastCorrectDate, formatter)
////            val timeDiff = ChronoUnit.MINUTES.between(lastCorrectDateFormatted, currentTime)
//
//        }
//
//    }


}