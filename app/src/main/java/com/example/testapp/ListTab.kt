package com.example.testapp

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ListTab(
    endeavors: EndeavorList,
    selectEndeavor : (Endeavor) -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xFF9898FF)),
            verticalArrangement = Arrangement.spacedBy(6.dp),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            item {
                Row( // top padding
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                        .background(color = Color(0xFF9898FF))
                ) {}
            }

            items(endeavors.contents) { endeavor ->
                Box(
                    modifier = Modifier
                        .clickable {
                            selectEndeavor(endeavor)
                        }
                ) {
                    when (endeavors.type) {
                        EndeavorType.APPOINTMENT -> { HabitCard(endeavor) }
                        EndeavorType.TASK        -> { HabitCard(endeavor) }
                        else                     -> { HabitCard(endeavor) }
                    }
                }
            }

            item {
                Row( // bottom padding
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .background(color = Color(0xFF9898FF))
                ) {}
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .align(Alignment.BottomCenter)
                .background(Brush.verticalGradient(listOf(Color(0x00000000), Color(0xFF9898FF))))
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp, 50.dp)
            ){
                IconButton(
                    modifier = Modifier
                        .size(50.dp, 50.dp)
                        .clip(CircleShape),
                    onClick = {
                        endeavors.contents.add(
                            when (endeavors.type) {
                                EndeavorType.APPOINTMENT -> { Endeavor(EndeavorType.APPOINTMENT) }
                                EndeavorType.TASK        -> { Endeavor(EndeavorType.TASK)        }
                                else                     -> { Endeavor(EndeavorType.HABIT)       }

                            }
                        )
                        selectEndeavor(endeavors.contents.last())
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.AddCircle,
                        contentDescription = null,
                        modifier = Modifier
                            .size(50.dp, 50.dp)
                            .background(color = Color.White)
                        )
                }
            }
        }
    }
}

@Composable
fun HabitCard(habit : Endeavor) {
    Row(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .height(50.dp)
            .background(color = Color(0xFFFFFFFF)),
        horizontalArrangement = Arrangement.Center
    ) {
        habit.title?.let {
            Text(
                modifier = Modifier
                    .weight(0.8f),
                text = it
            )
        }

        habit.duration?.let {
            Text(
                modifier = Modifier.weight(0.2f),
                text = it.asString
            )
        }

    }
}

