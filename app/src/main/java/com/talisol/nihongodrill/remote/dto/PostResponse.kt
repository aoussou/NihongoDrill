package com.talisol.nihongodrill.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class PostResponse(
    val kanji: String,
    val words: String
)