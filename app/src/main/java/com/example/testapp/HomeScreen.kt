package com.example.testapp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier= Modifier.fillMaxSize()
    ) {
        Button(
            onClick= {
                navController.navigate(route= Screen.Stats.route)
            }
        ) {
            Text("Stats")
        }

        Button(
            onClick= {
                navController.navigate(route= Screen.Lists.route)
            }
        ) {
            Text("Lists")
        }

        Button(
            onClick= {
                navController.navigate(route= Screen.Days.route)
            }
        ) {
            Text("Day")
        }
    }
}

@Composable
@Preview(showBackground = true)
fun HomeScreenPreview() {
    HomeScreen(navController= rememberNavController())
}