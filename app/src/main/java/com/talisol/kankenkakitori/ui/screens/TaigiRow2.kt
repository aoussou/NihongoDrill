package com.talisol.kankenkakitori.ui.screens

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TaigiRow2(
    questionLists: List<String>,
    correctAnswersList: List<String>,
    targetsList: List<String>,
) {


    Column(modifier = Modifier.fillMaxWidth()) {


        Column(modifier = Modifier.fillMaxWidth()) {

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                userScrollEnabled = false
            ) {
                itemsIndexed(questionLists) { index, item ->

                    val correctAnswer = correctAnswersList[index]
                    val isSecond = targetsList[index].indexOf(correctAnswer)
                    val target = targetsList[index].replace(correctAnswer, "")
                    Log.i("DEBUG", correctAnswer)
                    Log.i("DEBUG", target)
                    Log.i("DEBUG", isSecond.toString())



                    Row {

                        Box(
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = item[0].toString(),
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Box(
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = item[1].toString(),
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                }
            }

        }

        Column(modifier = Modifier.fillMaxWidth()) {


            LazyRow(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                userScrollEnabled = false
            ) {
                itemsIndexed(questionLists) { index, item ->

                    val correctAnswer = correctAnswersList[index]
                    val isSecond = targetsList[index].indexOf(correctAnswer)
                    val target = targetsList[index].replace(correctAnswer, "")
                    Log.i("DEBUG", correctAnswer)
                    Log.i("DEBUG", target)
                    Log.i("DEBUG", isSecond.toString())


                    Row(
                    ) {

                        if (isSecond == 0) {

                            Box(
                                contentAlignment = Alignment.Center,
                            ) {
                                Text(
                                    text = target,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Box(
                            modifier = Modifier
                                .background(Color.White)
                                .clickable {}
                                .border(BorderStroke(4.dp, Color.Black))
                                .aspectRatio(1f)
                                .fillMaxWidth(.1F)
                            ,
                            contentAlignment = Alignment.Center

                        ) {
                        }

                        if (isSecond == 1) {
                            Box(
                                contentAlignment = Alignment.Center,
                            ) {
                                Text(
                                    text = target,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }


    }
























}