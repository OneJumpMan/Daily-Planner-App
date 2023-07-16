package com.example.testapp

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SetupNavGraph(
    navController: NavHostController,
    userData: UserData
) {
    NavHost(
        navController= navController,
        startDestination= Screen.Home.route
    ) {

        composable(
            route= Screen.Home.route
        ) {
            HomeScreen(navController)
        }

        composable(
            route= Screen.Stats.route
        ) {
            StatsScreen()
        }

        composable(
            route= Screen.Lists.route
        ) {
            ListsScreen(userData)
        }

        composable(
            route= Screen.Days.route
        ) {
            DaysScreen(userData)
        }


    }
}