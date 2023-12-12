package com.talisol.nihongodrill.ui.screens.questionScreens

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun TaigiBox(
    modifier : Modifier,
    text: String
) {

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )
    }
}