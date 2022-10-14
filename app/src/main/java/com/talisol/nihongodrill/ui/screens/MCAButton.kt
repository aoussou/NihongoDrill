package com.talisol.nihongodrill.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MCAButton(
    answer: String,
    answerText: String,
    onClick: () -> Unit,
    fontSize: TextUnit = 24.sp,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    textColor: Color = Color.Black
) {


    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
//
            .padding(5.dp)
            .clickable {
                onClick()
            }
            .then(
                if (isSelected) Modifier.border(BorderStroke(2.dp, Color.Black)) else modifier
            )

        ,

        ) {

        Text(
            text = "$answer. $answerText",

            fontWeight = if (!isSelected) FontWeight.Medium else FontWeight.ExtraBold,
            fontSize = if (!isSelected) fontSize else (1.5*fontSize.value).sp,
            color = textColor
        )

    }


}