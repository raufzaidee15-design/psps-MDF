package com.example.mawai

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mawai.model.PenState
import com.example.mawai.model.MilkingData

@Composable
fun ReportScreen() {
    val penEntries: List<PenState> = DataRepository.penStates
        .sortedWith(compareByDescending<PenState> { it.date }.thenBy { it.barn })

    val milkEntries: List<MilkingData> = DataRepository.milkingData
        .sortedWith(compareByDescending<MilkingData> { it.date }.thenBy { it.barn })

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Pen State Report", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(12.dp))

        if (penEntries.isEmpty()) {
            Text("No Pen State data yet.")
        } else {
            LazyColumn {
                items(penEntries) { entry ->
                    ReportItemPen(entry)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text("Milking Report", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(12.dp))

        if (milkEntries.isEmpty()) {
            Text("No Milking data yet.")
        } else {
            LazyColumn {
                items(milkEntries) { entry ->
                    ReportItemMilk(entry)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
fun ReportItemPen(entry: PenState) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text("Date: ${entry.date}")
            Text("Barn: ${entry.barn}")
            Text("A: ${"%.2f".format(entry.percentA)}%")
            Text("B: ${"%.2f".format(entry.percentB)}%")
            Text("A+B: ${"%.2f".format(entry.percentAB)}%")
        }
    }
}

@Composable
fun ReportItemMilk(entry: MilkingData) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text("Date: ${entry.date}")
            Text("Barn: ${entry.barn}")
            Text("Total Milk: ${entry.totalMilk} liters")
        }
    }
}
