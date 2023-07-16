package com.example.testapp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

open class EndeavorBlock(
    endeavor : Endeavor,
    private var startTime : DateTime.Time
) {
    private val _type = endeavor.type
    private val _title = endeavor.title
    private var _duration = endeavor.duration
    private var _notes = endeavor.notes


    val title
        get() = _title

    var duration
        get() = _duration
        set(value) { _duration = value }

    var notes
        get() = _notes
        set(value) { _notes = value }

}

@Composable
fun TimeBlock(
    endeavorBlock : EndeavorBlock
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height((endeavorBlock.duration!!.inMinutes * 6).dp)
            .clip(shape = RoundedCornerShape(if (endeavorBlock.duration!!.inMinutes * 6 > 20) 10.dp else 0.dp))
    ) {

    }
}