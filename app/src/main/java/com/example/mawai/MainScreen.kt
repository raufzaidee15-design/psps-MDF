package com.example.mawai

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun MainScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Mawai Dairy Farm",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Button(
            onClick = { navController.navigate("pen_state") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Key in Pen State")
        }

        Button(
            onClick = { navController.navigate("milking_data") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Key in Milking Data")
        }

        Button(
            onClick = { navController.navigate("report") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Show Report")
        }
    }
}
