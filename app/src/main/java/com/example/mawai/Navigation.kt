package com.example.mawai

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun Navigation(navController: NavHostController, viewModel: DairyViewModel) {
    NavHost(
        navController = navController,
        startDestination = "main"
    ) {
        // Main menu screen
        composable("main") {
            MainScreen(navController)
        }

        // Pen State input screen
        composable("pen_state") {
            PenStateScreen(viewModel, navController)
        }

        // Milking Data input screen
        composable("milking_data") {
            MilkingDataScreen(viewModel, navController)
        }

        // Combined report screen
        composable("report") {
            ShowReportScreen(viewModel)
        }
    }
}
