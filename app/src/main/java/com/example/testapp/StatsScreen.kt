package com.example.testapp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun StatsScreen() {
    Box(
        modifier= Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Coming soon!")
    }
}

@Composable
@Preview(showBackground = true)
fun StatsScreenPreview() {
    StatsScreen()
}