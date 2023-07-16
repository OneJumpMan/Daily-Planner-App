package com.example.testapp

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.LayoutScopeMarker
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.ParentDataModifier
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp

@Composable
fun DayScroll() {
    val scrollState = rememberScrollState()

    val timestamps = @Composable {
        repeat(97) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .absolutePadding(
                        left = 5.dp,
                        right = 10.dp,
                        top = 0.dp,
                        bottom = 0.dp
                    ),
                verticalAlignment= Alignment.CenterVertically
            ) {
                Text(DateTime.Time(15 * it).asString)
                Canvas(
                    modifier = Modifier
                        .weight(1f)
                        .height(1.dp)
                ) {
                    drawLine(
                        color = Color.Gray,
                        start = Offset(20f, size.height / 2),
                        end = Offset(size.width, size.height / 2),
                        strokeWidth = if (it % 4 == 0) 3f else 1f
                    )
                }
            }
        }
    }



    Layout(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState),
        contents = listOf(timestamps)
    ) {
            (timestampMeasurables),
            constraints,
        ->

        val timestampPlaceables = timestampMeasurables.map { measurable ->
            val placeable = measurable.measure(constraints)
            placeable
        }


        layout(
            width = constraints.maxWidth,
            height = (6 * 1440) + 80
        ){
            timestampPlaceables.forEachIndexed { index, placeable ->
                placeable.place(0, 10 + index * 90)
            }

        }

    }

}