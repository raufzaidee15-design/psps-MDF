package com.example.mawai

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.mawai.model.PenState
import com.example.mawai.model.MilkingData
import java.io.File
import java.io.FileOutputStream

@Composable
fun ReportScreen() {
    val context = LocalContext.current
    val penEntries: List<PenState> = DataRepository.penStates
        .sortedWith(compareByDescending<PenState> { it.date }.thenBy { it.barn })

    val milkEntries: List<MilkingData> = DataRepository.milkingData
        .sortedWith(compareByDescending<MilkingData> { it.date }.thenBy { it.barn })

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Button(
            onClick = { generatePdfAndShare(context, penEntries, milkEntries) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Export & Share PDF Report")
        }

        Spacer(modifier = Modifier.height(16.dp))

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

fun generatePdfAndShare(context: Context, penEntries: List<PenState>, milkEntries: List<MilkingData>) {
    try {
        val pdfFile = File(context.cacheDir, "Report.pdf")
        val outputStream = FileOutputStream(pdfFile)

        val header = "Pen State Report Mawai Dairy Farm\n\n"
        outputStream.write(header.toByteArray())

        outputStream.write("=== Pen State ===\n".toByteArray())
        penEntries.forEach {
            val line = "Date: ${it.date}, Barn: ${it.barn}, A: ${"%.2f".format(it.percentA)}%, " +
                    "B: ${"%.2f".format(it.percentB)}%, A+B: ${"%.2f".format(it.percentAB)}%\n"
            outputStream.write(line.toByteArray())
        }

        outputStream.write("\n=== Milking Data ===\n".toByteArray())
        milkEntries.forEach {
            val line = "Date: ${it.date}, Barn: ${it.barn}, Milk: ${it.totalMilk} liters\n"
            outputStream.write(line.toByteArray())
        }

        outputStream.close()

        val uri: Uri = androidx.core.content.FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            pdfFile
        )

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "application/pdf"
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        context.startActivity(Intent.createChooser(intent, "Share Report PDF"))

    } catch (e: Exception) {
        e.printStackTrace()
    }
}
