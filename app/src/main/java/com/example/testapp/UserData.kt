package com.example.testapp
import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.temporal.ChronoUnit

@RequiresApi(Build.VERSION_CODES.O)
class UserData() {

    var appointmentList = EndeavorList(EndeavorType.APPOINTMENT)
    var taskList = EndeavorList(EndeavorType.TASK)
    var habitList = EndeavorList(EndeavorType.HABIT)

    var deletedEndeavors = EndeavorList(EndeavorType.APPOINTMENT)
    private fun cleanupAppointmentList() {
        appointmentList.contents.forEach {
            if (it.markedForDeletion) { deletedEndeavors.contents.add(it) }
        }
        appointmentList.contents = appointmentList.contents.filter { !it.markedForDeletion } as MutableList<Endeavor>
    }
    private fun cleanupTaskList() {
        taskList.contents.forEach {
            if (it.markedForDeletion) { deletedEndeavors.contents.add(it) }
        }
        taskList.contents = taskList.contents.filter { !it.markedForDeletion } as MutableList<Endeavor>
    }
    private fun cleanupHabitList() {
        habitList.contents.forEach {
            if (it.markedForDeletion) { deletedEndeavors.contents.add(it) }
        }
        habitList.contents = habitList.contents.filter { !it.markedForDeletion } as MutableList<Endeavor>
    }


    fun updateLists() {
        cleanupHabitList()
        cleanupTaskList()
        cleanupAppointmentList()
    }
}

// A wrapper class that can be passed by reference to composables
// Makes editing these lists easier
class EndeavorList (val type : EndeavorType) {
    var contents : MutableList<Endeavor> = mutableListOf()
}