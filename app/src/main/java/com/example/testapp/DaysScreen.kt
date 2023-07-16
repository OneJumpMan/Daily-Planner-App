package com.example.testapp

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DaysScreen(userData: UserData) {
    var planMode by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(15.dp)
        ) {
            Button(
                onClick = {}
            ) {
                Text(text = "<-")
            }
            //Text(selectedDay.date.dayOfWeek.asString)
            Button(
                onClick = {}
            ) {
                Text(text = "->")
            }
        }

        DayScroll()
    }
}



@Composable
fun planDayView(day: Day) {

}




