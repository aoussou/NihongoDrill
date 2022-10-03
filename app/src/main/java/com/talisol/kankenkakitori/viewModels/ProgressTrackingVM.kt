package com.talisol.kankenkakitori.viewModels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.talisol.kankenkakitori.actions.TrackingAction
import com.talisol.kankenkakitori.data.ProgressDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class ProgressTrackingVM @Inject constructor(
    private val progressDataSource: ProgressDataSource
) : ViewModel() {

    @RequiresApi(Build.VERSION_CODES.O)
    fun onAction(action: TrackingAction) {
        when (action) {
            is TrackingAction.AddOneCorrect -> addOneCorrect(action.id)
            is TrackingAction.AddOneWrong -> addOneWrong(action.id)
            is TrackingAction.SubtractOneCorrect -> subtractOneCorrect(action.id)
            is TrackingAction.SubtractOneWrong -> subtractOneWrong(action.id)
            is TrackingAction.UpdateCorrectStreak -> updateCorrectStreak(action.id)
            is TrackingAction.ResetCorrectStreak -> resetCorrectStreak(action.id)
            is TrackingAction.UpdateLastCorrectTime -> updateLastCorrectTime(action.id)
            is TrackingAction.StopAsking -> stopAsking(action.id)
            is TrackingAction.MarkForReview -> markForReview(action.id)
        }
    }



    private fun addOneCorrect(id:Int) {
        val newTotalCorrect = progressDataSource.getTotalCorrect(id.toLong()) + 1
        progressDataSource.updateTotalCorrectNumber(id.toLong(), newTotalCorrect)
    }

    private fun addOneWrong(id:Int) {
        val newTotalWrong = progressDataSource.getTotalWrong(id.toLong()) + 1
        progressDataSource.updateTotalWrongNumber(id.toLong(), newTotalWrong)
    }

    private fun subtractOneCorrect(id:Int) {
        val newTotalCorrect = progressDataSource.getTotalCorrect(id.toLong()) - 1
        progressDataSource.updateTotalCorrectNumber(id.toLong(), newTotalCorrect)
    }

    private fun subtractOneWrong(id:Int) {
        val newTotalWrong = progressDataSource.getTotalWrong(id.toLong()) - 1
        progressDataSource.updateTotalWrongNumber(id.toLong(), newTotalWrong) }

    private fun updateCorrectStreak(id:Int) {

        val newCorrectStreak = progressDataSource.getCorrectStrek(id.toLong()) + 1
        progressDataSource.updateCorrectStreak(
            id.toLong(),
            newCorrectStreak
        )
    }

    private fun resetCorrectStreak(id:Int) {
        progressDataSource.updateCorrectStreak(id.toLong(), 0)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateLastCorrectTime(id:Int) {
        progressDataSource.updateLastCorrectDate(
            id.toLong(),
            LocalDateTime.now().toString()
        )
    }

    private fun stopAsking(id:Int) {
        progressDataSource.makeUnavailable(id.toLong())
    }

    private fun markForReview(id:Int) {
        progressDataSource.markForReview(id.toLong())
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