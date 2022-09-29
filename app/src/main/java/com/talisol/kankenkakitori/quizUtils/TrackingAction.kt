package com.talisol.kankenkakitori.quizUtils

sealed class TrackingAction{
    data class AddOneCorrect(val id: Int): TrackingAction()
    data class AddOneWrong(val id: Int): TrackingAction()
    data class SubtractOneCorrect(val id: Int): TrackingAction()
    data class SubtractOneWrong(val id: Int): TrackingAction()
    data class StopAsking(val id: Int): TrackingAction()
    data class MarkForReview(val id: Int): TrackingAction()
}
