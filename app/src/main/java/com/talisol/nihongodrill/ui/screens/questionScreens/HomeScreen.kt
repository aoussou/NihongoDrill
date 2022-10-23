package com.talisol.nihongodrill.ui.screens.questionScreens

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun HomeScreen(
    navController: NavController
) {

    Text("SELECT TEST TYPE")

    Button(onClick = { /*TODO*/ }) {
        Text(text = "JLPT")
    }

    Button(onClick = { /*TODO*/ }) {
        Text(text = "漢検")
    }

}