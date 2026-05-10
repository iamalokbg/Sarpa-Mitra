package com.sarpamitra.ui.screens

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sarpamitra.monitoring.SwellingPhoto
import com.sarpamitra.monitoring.SwellingPhotoManager
import java.io.File

@Composable
fun CaseDetailScreen(
    caseId: String,
    severity: String,
    syndrome: String,
    timestamp: String,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val photos = remember { SwellingPhotoManager.getPhotos(context, caseId) }

    val severityColor = when (severity) {
        "CRITICAL" -> RedColor
        "SEVERE" -> Color(0xFFFF6B00)
        "MODERATE" -> AmberColor
        else -> GreenColor
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(BgColor)
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Case Detail",
                    color = WhiteColor,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                TextButton(onClick = onBack) {
                    Text(text = "Back", color = GrayColor)
                }
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = severityColor),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = caseId, color = WhiteColor, fontSize = 13.sp)
                    Text(
                        text = severity,
                        color = WhiteColor,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(text = syndrome, color = WhiteColor, fontSize = 14.sp)
                    Text(text = timestamp, color = WhiteColor.copy(alpha = 0.8f), fontSize = 12.sp)
                }
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = CardColor),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "📷 Swelling Progression",
                            color = WhiteColor,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${photos.size} photos",
                            color = AmberColor,
                            fontSize = 13.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    if (photos.isEmpty()) {
                        Text(
                            text = "No swelling photos captured for this case.",
                            color = GrayColor,
                            fontSize = 13.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    } else {
                        Text(
                            text = "Scroll to see swelling progression →",
                            color = GrayColor,
                            fontSize = 11.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(photos) { photo ->
                                SwellingPhotoCard(photo = photo)
                            }
                        }
                    }
                }
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = CardColor),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "🏥 For the Doctor",
                        color = WhiteColor,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "• Photos show swelling at ${photos.map { it.minutesSinceBite }.joinToString(", ")} minutes post-bite",
                        color = GrayColor,
                        fontSize = 13.sp
                    )
                    Text(
                        text = "• Progressive swelling across joints = Grade 1+ envenomation",
                        color = GrayColor,
                        fontSize = 13.sp
                    )
                    Text(
                        text = "• Compare first vs last photo for spread rate",
                        color = GrayColor,
                        fontSize = 13.sp
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun SwellingPhotoCard(photo: SwellingPhoto) {
    val bitmap = remember(photo.path) {
        try {
            val options = android.graphics.BitmapFactory.Options().apply {
                inSampleSize = 2
            }
            BitmapFactory.decodeFile(photo.path, options)
        } catch (e: Exception) {
            null
        }
    }

    Card(
        modifier = Modifier.width(160.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF3A3A3C)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column {
            if (bitmap != null) {
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = "Swelling at ${photo.minutesSinceBite} minutes",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .background(Color(0xFF2C2C2E)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "📷", fontSize = 32.sp)
                }
            }
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = "${photo.minutesSinceBite} min",
                    color = AmberColor,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = photo.timeLabel,
                    color = GrayColor,
                    fontSize = 11.sp
                )
            }
        }
    }
}