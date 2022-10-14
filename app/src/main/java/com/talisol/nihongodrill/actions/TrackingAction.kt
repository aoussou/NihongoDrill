package com.talisol.nihongodrill.actions

sealed class TrackingAction {
    data class AddOneCorrect(val id: Int) : TrackingAction()
    data class AddOneWrong(val id: Int) : TrackingAction()
    data class SubtractOneCorrect(val id: Int) : TrackingAction()
    data class SubtractOneWrong(val id: Int) : TrackingAction()
    data class UpdateCorrectStreak(val id: Int) : TrackingAction()
    data class ResetCorrectStreak(val id: Int) : TrackingAction()
    data class UpdateLastCorrectTime(val id: Int) : TrackingAction()
    data class StopAsking(val id: Int) : TrackingAction()
    data class MarkForReview(val id: Int) : TrackingAction()
}
