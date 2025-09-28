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

import android.graphics.pdf.PdfDocument
import android.graphics.Paint
import android.graphics.Typeface

fun generatePdfAndShare(context: Context, penEntries: List<PenState>, milkEntries: List<MilkingData>) {
    try {
        val pdfDocument = PdfDocument()
        val paint = Paint()
        val titlePaint = Paint().apply {
            typeface = Typeface.create(Typeface.DEFAULT_BOLD, Typeface.BOLD)
            textSize = 18f
        }

        val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create() // A4
        val page = pdfDocument.startPage(pageInfo)
        val canvas = page.canvas

        var y = 50

        // Title
        canvas.drawText("Pen State Report Mawai Dairy Farm", 50f, y.toFloat(), titlePaint)
        y += 40

        // === Pen State Table ===
        canvas.drawText("Pen State Data", 50f, y.toFloat(), titlePaint)
        y += 30

        // Table header
        paint.typeface = Typeface.create(Typeface.DEFAULT_BOLD, Typeface.BOLD)
        canvas.drawText("Date", 50f, y.toFloat(), paint)
        canvas.drawText("Barn", 150f, y.toFloat(), paint)
        canvas.drawText("A%", 250f, y.toFloat(), paint)
        canvas.drawText("B%", 330f, y.toFloat(), paint)
        canvas.drawText("A+B%", 410f, y.toFloat(), paint)
        y += 20
        paint.typeface = Typeface.DEFAULT

        penEntries.forEach {
            canvas.drawText(it.date, 50f, y.toFloat(), paint)
            canvas.drawText(it.barn, 150f, y.toFloat(), paint)
            canvas.drawText("%.2f".format(it.percentA), 250f, y.toFloat(), paint)
            canvas.drawText("%.2f".format(it.percentB), 330f, y.toFloat(), paint)
            canvas.drawText("%.2f".format(it.percentAB), 410f, y.toFloat(), paint)
            y += 20
        }

        y += 40

        // === Milking Data Table ===
        canvas.drawText("Milking Data", 50f, y.toFloat(), titlePaint)
        y += 30

        // Table header
        paint.typeface = Typeface.create(Typeface.DEFAULT_BOLD, Typeface.BOLD)
        canvas.drawText("Date", 50f, y.toFloat(), paint)
        canvas.drawText("Barn", 150f, y.toFloat(), paint)
        canvas.drawText("Milk (L)", 250f, y.toFloat(), paint)
        y += 20
        paint.typeface = Typeface.DEFAULT

        milkEntries.forEach {
            canvas.drawText(it.date, 50f, y.toFloat(), paint)
            canvas.drawText(it.barn, 150f, y.toFloat(), paint)
            canvas.drawText("${it.totalMilk}", 250f, y.toFloat(), paint)
            y += 20
        }

        pdfDocument.finishPage(page)

        // Save PDF file
        val pdfFile = File(context.cacheDir, "Report.pdf")
        pdfDocument.writeTo(FileOutputStream(pdfFile))
        pdfDocument.close()

        // Share intent
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
