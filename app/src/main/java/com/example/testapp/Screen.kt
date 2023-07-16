package com.example.testapp

sealed class Screen(val route: String) {
    object Home: Screen("home")
    object Stats: Screen("stats")
    object Lists: Screen("lists")
    object Days: Screen("days")
}
