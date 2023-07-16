package com.example.testapp

class Day(private val _date: DateTime.Date) {

    val date
        get() = _date

    var blocks = mutableListOf<EndeavorBlock>()


}