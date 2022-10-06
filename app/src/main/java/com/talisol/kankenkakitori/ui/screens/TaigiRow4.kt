package com.talisol.kankenkakitori.ui.screens

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
fun TaigiRow4(
    questionLists: List<String>,
    correctAnswersList: List<String>,
    targetsList: List<String>,
) {


    LazyVerticalGrid(
        columns = GridCells.Adaptive(36.dp),
        verticalArrangement = Arrangement.Center,
        horizontalArrangement = Arrangement.SpaceBetween,
        content = {
            items(questionLists.size) { index ->


                val question = questionLists[index]
                val correctAnswer = correctAnswersList[index]
                val isSecond = targetsList[index].indexOf(correctAnswer)
                val target = targetsList[index].replace(correctAnswer, "")




                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {


                    Box(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .border(BorderStroke(4.dp, Color.Black)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = question[0].toString(),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Text(
                        text = question,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Box(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .border(BorderStroke(4.dp, Color.Black)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = question[1].toString(),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                }


            }

        }
    )

}

//                            Box(
//                                modifier = Modifier
//                                    .aspectRatio(1f)
//                                    .border(BorderStroke(4.dp, Color.Black))
//                                ,
//                                contentAlignment = Alignment.Center
//                            ) {

//                            }

//                            Box(
//                                modifier = Modifier
//                                    .aspectRatio(1f)
//                                    .border(BorderStroke(4.dp, Color.Black))
//                                ,
//                                contentAlignment = Alignment.Center
//                            ) {
//                                Text(
//                                    text = question[1].toString(),
//                                    fontSize = 24.sp,
//                                    fontWeight = FontWeight.Bold
//                                )
//                            }




//                        Row(horizontalArrangement = Arrangement.SpaceEvenly) {
//
//                            if (isSecond == 0) {
//
//                                Box(
//                                    modifier = Modifier.aspectRatio(1f),
//                                    contentAlignment = Alignment.Center,
//                                ) {
//                                    Text(
//                                        text = target,
//                                        fontSize = 24.sp,
//                                        fontWeight = FontWeight.Bold
//                                    )
//                                }
//                            }
//
//                            Box(
//                                modifier = Modifier
//                                    .background(Color.White)
//                                    .clickable {}
//                                    .border(BorderStroke(4.dp, Color.Black))
//                                    .aspectRatio(1f)
//                                    .fillMaxWidth(.1F),
//                                contentAlignment = Alignment.Center
//
//                            ) {
//                            }
//
//                            if (isSecond == 1) {
//                                Box(
//                                    modifier = Modifier.aspectRatio(1f),
//                                    contentAlignment = Alignment.Center,
//                                ) {
//                                    Text(
//                                        text = target,
//                                        fontSize = 24.sp,
//                                        fontWeight = FontWeight.Bold
//                                    )
//                                }
//                            }
//                        }
//
//
//                    }

//            }

















