package com.example.mawai

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mawai.model.PenState

@Composable
fun ReportScreen() {
    val entries: List<PenState> = DataRepository.penStates
        .sortedWith(compareByDescending<PenState> { it.date }.thenBy { it.barn })

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Pen State Report", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(16.dp))

        if (entries.isEmpty()) {
            Text("No data yet.")
        } else {
            LazyColumn {
                items(entries) { entry ->
                    ReportItem(entry)
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

@Composable
fun ReportItem(entry: PenState) {
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
