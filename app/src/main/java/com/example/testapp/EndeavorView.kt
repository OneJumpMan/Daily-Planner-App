package com.example.testapp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EndeavorView(
    endeavor : Endeavor,
    exitEndeavorView : () -> Unit,
    updateUserData : () -> Unit
) {
    // view modes
    var newMode by remember { mutableStateOf(endeavor.title == null) }
    var editMode by remember { mutableStateOf(false) }

    // Values that every endeavor has.
    // Cannot be null. If the endeavor has these as null,
    // they are initialized with default values here.
    var title by remember { mutableStateOf(if (endeavor.title == null) "" else endeavor.title!!) }
    var notes by remember { mutableStateOf(if (endeavor.notes == null) "" else endeavor.notes!!) }
    var duration by remember { mutableStateOf( if (endeavor.duration == null) DateTime.Duration(0) else endeavor.duration!! ) }

    // Task-specific values
    // Can be null
    var completed by remember { mutableStateOf(endeavor.completed) }
    var scheduledDate by remember { mutableStateOf(endeavor.scheduledDate) }
    var scheduledTime by remember { mutableStateOf(endeavor.scheduledTime) }

    // appointment-specific values
    // Can be null
    var periodic by remember { mutableStateOf(endeavor.periodic) }
    var period by remember { mutableStateOf(endeavor.period) }


    val durationHours = rememberPickerState()
    val durationMinutes = rememberPickerState()

    val dateMonth = rememberPickerState()
    val dateDay = rememberPickerState()

    val timeHour = rememberPickerState()
    val timeMinute = rememberPickerState()

    Column(
        modifier = Modifier
            .fillMaxWidth(0.7f)
            .fillMaxHeight(0.5f)
            .background(color = Color.White)
            .clickable {}, // if the EndeavorView isn't clickable, then any touch triggers the onClick function of the background box. I don't know why or how to fix it
    ) {

        ///////////////////////
        ///* ENDEAVOR INFO *///
        ///////////////////////
        Column(modifier = Modifier.fillMaxWidth().fillMaxHeight(0.9f)) {

            // title
            Row(
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                if (newMode || editMode) {
                    TextField(
                        modifier = Modifier.fillMaxWidth(if (endeavor.type == EndeavorType.TASK) 0.85f else 1.0f),
                        value = title,
                        onValueChange = { title = it }
                    )
                } else {
                    Text(
                        modifier = Modifier.fillMaxWidth(if (endeavor.type == EndeavorType.TASK) 0.85f else 1.0f),
                        text = title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }

                // checkbox for tasks
                // Apart from the save button, this is the only element of
                // the EndeavorView that can modify the endeavor object itself
                if (endeavor.type == EndeavorType.TASK) {
                    Checkbox(
                        checked = completed!!,
                        onCheckedChange = {
                            completed = it
                            endeavor.completed = it
                        }
                    )
                }
            }

            // duration
            Row(
                modifier = Modifier.fillMaxWidth().height(50.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text( "Expected duration: ")

                if (newMode || editMode) {
                    TimePicker(
                        hourState = durationHours,
                        minuteState = durationMinutes,
                        hourStartIndex = duration.hours,
                        minuteStartIndex = duration.minutes
                    )


                } else {
                    if (endeavor.duration != null) {
                        Text(
                            text= endeavor.duration!!.asString,
                            fontWeight = FontWeight.Bold
                        )
                    } else {
                        Text(
                            text= "Enter event duration",
                            fontStyle = FontStyle.Italic,
                            fontWeight = FontWeight.Light
                        )
                    }
                }
            }






            // scheduled date and time
            if (endeavor.type == EndeavorType.TASK) {
                Column(
                    modifier = Modifier.fillMaxWidth().height(100.dp)
                ) {

                    // switches to nullify scheduled date and time
                    if (editMode || newMode) {
                        Row(
                            modifier = Modifier.fillMaxWidth().fillMaxHeight(0.4f)
                        ){
                            // switches whether scheduled date is null
                            Switch(
                                modifier = Modifier.scale(0.7f),
                                checked = scheduledDate != null,
                                onCheckedChange = {
                                    scheduledDate =
                                        if (scheduledDate == null) { DateTime.Date(2023, 1) }
                                        else  { null }

                                    if (scheduledDate == null) { scheduledTime = null }
                                }
                            )
                            // switches whether scheduled time is null
                            // automatically null if scheduled date is null
                            Switch(
                                modifier = Modifier.scale(0.7f),
                                checked = scheduledDate != null && scheduledTime != null, // a little built-in redundancy here
                                onCheckedChange = {
                                    scheduledTime =
                                        if (scheduledDate == null) { null }
                                        else {
                                            if (scheduledTime == null) { DateTime.Time(0) }
                                            else { null }
                                        }
                                }
                            )

                        }
                    }

                    // scheduled date and time
                    Row(
                        modifier = Modifier.fillMaxWidth().fillMaxHeight(),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        if (newMode || editMode) {

                            if (scheduledDate == null) {
                                Text("no date selected")
                            }
                            else {
                                DatePicker(
                                    monthState = dateMonth,
                                    dayState = dateDay,
                                    monthStartIndex = endeavor.scheduledDate?.month?.number?.minus(1) ?: 0,
                                    dayStartIndex = endeavor.scheduledDate?.dayOfMonth?.minus(1) ?: 0
                                )
                            }

                            Spacer(modifier = Modifier.width(50.dp))

                            if (scheduledTime == null) {
                                Text("no time selected")
                            }
                            else {
                                TimePicker(
                                    hourState = timeHour,
                                    minuteState = timeMinute,
                                    hourStartIndex = endeavor.scheduledTime?.hour ?: 0,
                                    minuteStartIndex = endeavor.scheduledTime?.minute ?: 0
                                )
                            }

                        }

                        else {

                            if (scheduledDate == null) {
                                Text("unscheduled")
                            }

                            else {
                                Text("Scheduled for: ${endeavor.scheduledDate!!.asString} ")
                            }

                            if (scheduledTime != null) {
                                Text("at ${scheduledTime!!.asString}")
                            }
                        }
                    }
                }
            }


            // notes
            if (editMode || newMode) {
                TextField(
                    value = notes,
                    onValueChange = { notes = it },
                )
            }
            else {
                Text(endeavor.notes!!)
            }
        }

        /////////////////
        ///* BUTTONS *///
        /////////////////
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = if (newMode || editMode) Arrangement.Start else Arrangement.End
        ) {
            // edit mode buttons
            if (editMode || newMode) {
                // delete endeavor
                if (!newMode) {
                    IconButton(
                        onClick = {
                            endeavor.markedForDeletion = true
                            exitEndeavorView()
                            updateUserData()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = null
                        )
                    }
                }

                // cancel edits
                Button(
                    onClick = {
                        if (newMode) {
                            endeavor.markedForDeletion = true
                            exitEndeavorView()
                            updateUserData()
                        } else {
                            title =
                                if (endeavor.title == null) "" else endeavor.title!!
                            notes =
                                if (endeavor.notes == null) "" else endeavor.notes!!
                            duration =
                                if (endeavor.duration == null) DateTime.Duration(0) else endeavor.duration!!

                            scheduledDate = endeavor.scheduledDate
                            scheduledTime = endeavor.scheduledTime

                            editMode = false
                        }
                    }
                ) {
                    Text(text = "Cancel")
                }

                // save edits
                Button(
                    onClick = {
                        endeavor.title = title
                        endeavor.notes = notes

                        endeavor.duration =
                            DateTime.Duration(durationHours.selectedIndex * 60 + durationMinutes.selectedIndex)
                        duration =
                            if (endeavor.duration == null) DateTime.Duration(0) else endeavor.duration!!


                        endeavor.scheduledDate =
                            if (scheduledDate == null) { null }
                            else {
                                DateTime.Date(2023, dateMonth.selectedIndex + 1, dateDay.selectedIndex + 1)
                            }
                        scheduledDate = endeavor.scheduledDate

                        endeavor.scheduledTime =
                            if (scheduledDate == null || scheduledTime == null) { null }
                            else {
                                DateTime.Time(timeHour.selectedIndex, timeMinute.selectedIndex)
                            }
                        scheduledTime = endeavor.scheduledTime


                        editMode = false
                        newMode = false

                        updateUserData()
                    }
                ) {
                    Text(text = "Save")
                }
            }
            // non edit mode buttons
            else {
                // enter edit mode
                IconButton(
                    onClick = {
                        editMode = true
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.EditNote,
                        contentDescription = null
                    )
                }
            }
        }
    }
}


@Composable
fun TimePicker(
    hourState : PickerState,
    minuteState : PickerState,
    hourStartIndex : Int,
    minuteStartIndex : Int
) {
    Row(
        modifier = Modifier.wrapContentSize(),
        verticalAlignment = Alignment.CenterVertically
    ){
        // hour
        Picker(
            modifier = Modifier.fillMaxHeight().width(25.dp),
            state = hourState,
            items = (0..23).map { if (it < 10)  "0$it"  else  it.toString() },
            startIndex = hourStartIndex
        )

        Text(
            text = " : ",
            fontSize = 25.sp
        )

        Picker(
            modifier = Modifier.fillMaxHeight().width(25.dp),
            state = minuteState,
            items = (0..59).map { if (it < 10)  "0$it"  else  it.toString() },
            startIndex = minuteStartIndex
        )
    }
}


@Composable
fun DatePicker(
    monthState : PickerState,
    dayState : PickerState,
    monthStartIndex : Int,
    dayStartIndex : Int
) {
    Row(
        modifier = Modifier.wrapContentSize(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Picker(
            modifier = Modifier.width(40.dp).fillMaxHeight(),
            state = monthState,
            items = (0..11).map { Months.values()[it].shortString },
            startIndex = monthStartIndex
        )
        Picker(
            modifier = Modifier.width(25.dp).fillMaxHeight(),
            state = dayState,
            items = (1..31).map { it.toString() },
            startIndex = dayStartIndex
        )
    }
}