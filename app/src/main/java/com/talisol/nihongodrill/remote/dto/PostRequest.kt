package com.talisol.nihongodrill.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class PostRequest(
    val query: String,
    val language: String,
    val no_english: Boolean
)