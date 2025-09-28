package com.example.mawai

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ShowReportScreen(viewModel: DairyViewModel) {
    val penReports = viewModel.penReports
    val milkReports = viewModel.milkReports

    // Merge both lists into one and sort by date (newest first)
    val combinedReports = (penReports.map { ReportItem.Pen(it) } +
            milkReports.map { ReportItem.Milk(it) })
        .sortedByDescending { it.date }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Mawai Dairy Farm - Report",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Table Header
        Row(Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
            Text("Date", modifier = Modifier.weight(1f), style = MaterialTheme.typography.labelLarge)
            Text("Barn", modifier = Modifier.weight(1f), style = MaterialTheme.typography.labelLarge)
            Text("Details", modifier = Modifier.weight(2f), style = MaterialTheme.typography.labelLarge)
        }
        Divider(thickness = 2.dp)

        // Table Rows
        combinedReports.forEach { report ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Text(report.date, modifier = Modifier.weight(1f))
                Text(report.barn, modifier = Modifier.weight(1f))
                Text(report.details, modifier = Modifier.weight(2f))
            }
            Divider()
        }
    }
}

// Unified report type
sealed class ReportItem(val date: String, val barn: String, val details: String) {
    class Pen(data: PenReport) : ReportItem(
        data.date,
        data.barn,
        "A: ${data.percentageA}% | B: ${data.percentageB}% | A+B: ${data.percentageAB}%"
    )

    class Milk(data: MilkReport) : ReportItem(
        data.date,
        data.barn,
        "Milk Total: ${data.totalLiters} L"
    )
}
