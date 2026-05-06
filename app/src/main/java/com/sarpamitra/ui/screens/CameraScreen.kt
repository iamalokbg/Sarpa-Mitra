package com.sarpamitra.ui.screens

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.hardware.Sensor
import android.hardware.SensorManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Composable
fun CameraXScreen(
    title: String,
    overlayColor: Color,
    onPhotoCaptured: (String) -> Unit,
    onSkip: () -> Unit,
    onValidatePhoto: (suspend (String) -> Boolean)? = null
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val scope = rememberCoroutineScope()

    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED
        )
    }
    var statusMessage by remember { mutableStateOf("") }
    var imageCapture: ImageCapture? by remember { mutableStateOf(null) }
    var camera: Camera? by remember { mutableStateOf(null) }
    var isCapturing by remember { mutableStateOf(false) }
    var isValidating by remember { mutableStateOf(false) }
    var showNotWoundDialog by remember { mutableStateOf(false) }
    var pendingPhotoPath by remember { mutableStateOf<String?>(null) }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted -> hasCameraPermission = granted }

    // Shared validation logic
    fun validateAndProceed(path: String) {
        if (onValidatePhoto != null) {
            isValidating = true
            statusMessage = "🔍 Gemma 4 checking photo..."
            scope.launch(Dispatchers.IO) {
                val isValid = onValidatePhoto(path)
                kotlinx.coroutines.withContext(Dispatchers.Main) {
                    isCapturing = false
                    isValidating = false
                    statusMessage = ""
                    if (isValid) {
                        onPhotoCaptured(path)
                    } else {
                        pendingPhotoPath = path
                        showNotWoundDialog = true
                    }
                }
            }
        } else {
            onPhotoCaptured(path)
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            statusMessage = "Loading from gallery..."
            val path = copyUriToFile(context, it)
            if (path != null) {
                validateAndProceed(path)
            } else {
                statusMessage = "Could not load image. Try camera instead."
            }
        }
    }

    LaunchedEffect(Unit) {
        if (!hasCameraPermission) {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    val lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
    var isLowLight by remember { mutableStateOf(false) }

    DisposableEffect(Unit) {
        val listener = object : android.hardware.SensorEventListener {
            override fun onSensorChanged(event: android.hardware.SensorEvent) {
                isLowLight = event.values[0] < 10f
            }
            override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
        }
        lightSensor?.let {
            sensorManager.registerListener(listener, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
        onDispose { sensorManager.unregisterListener(listener) }
    }

    LaunchedEffect(isLowLight, camera) {
        camera?.cameraControl?.enableTorch(isLowLight)
    }

    // Not a wound photo dialog
    if (showNotWoundDialog) {
        AlertDialog(
            onDismissRequest = {
                showNotWoundDialog = false
                pendingPhotoPath = null
                isCapturing = false
                statusMessage = ""
            },
            title = {
                Text(
                    text = "⚠️ May Not Be a Wound Photo",
                    color = AmberColor,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = "This photo doesn't appear to show a snakebite wound or bite site.\n\nIn an emergency, you can use it anyway — or retake a clearer photo of the bite.",
                    color = WhiteColor
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showNotWoundDialog = false
                        pendingPhotoPath?.let { onPhotoCaptured(it) }
                        pendingPhotoPath = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = AmberColor)
                ) { Text("USE ANYWAY") }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showNotWoundDialog = false
                        pendingPhotoPath = null
                        isCapturing = false
                        statusMessage = "Please photograph the bite site"
                    }
                ) { Text("RETAKE", color = GrayColor) }
            },
            containerColor = CardColor
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BgColor)
    ) {
        if (!hasCameraPermission) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "Camera permission required", color = WhiteColor, fontSize = 18.sp)
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { permissionLauncher.launch(Manifest.permission.CAMERA) }) {
                    Text("Grant Permission")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { galleryLauncher.launch("image/*") },
                    colors = ButtonDefaults.buttonColors(containerColor = CardColor)
                ) {
                    Text("🖼️ Choose from Gallery instead", color = WhiteColor)
                }
                Spacer(modifier = Modifier.height(8.dp))
                TextButton(onClick = onSkip) {
                    Text("Skip — use voice only", color = GrayColor)
                }
            }
        } else {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = title,
                    color = WhiteColor,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                if (isLowLight) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "⚡ Low light — flashlight active", color = AmberColor, fontSize = 13.sp)
                }

                if (isValidating) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator(
                            color = GreenColor,
                            modifier = Modifier.size(14.dp),
                            strokeWidth = 2.dp
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = "Gemma 4 checking photo...", color = GreenColor, fontSize = 13.sp)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Camera Preview
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(320.dp)
                        .padding(horizontal = 16.dp)
                        .border(3.dp, overlayColor, RoundedCornerShape(16.dp))
                ) {
                    AndroidView(
                        factory = { ctx ->
                            val previewView = PreviewView(ctx)
                            val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)
                            cameraProviderFuture.addListener({
                                val cameraProvider = cameraProviderFuture.get()
                                val preview = Preview.Builder().build().also {
                                    it.setSurfaceProvider(previewView.surfaceProvider)
                                }
                                val capture = ImageCapture.Builder()
                                    .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                                    .build()
                                imageCapture = capture
                                try {
                                    cameraProvider.unbindAll()
                                    camera = cameraProvider.bindToLifecycle(
                                        lifecycleOwner,
                                        CameraSelector.DEFAULT_BACK_CAMERA,
                                        preview,
                                        capture
                                    )
                                } catch (e: Exception) {
                                    statusMessage = "Camera error: ${e.message}"
                                }
                            }, ContextCompat.getMainExecutor(ctx))
                            previewView
                        },
                        modifier = Modifier.fillMaxSize()
                    )
                }

                if (statusMessage.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = statusMessage,
                        color = AmberColor,
                        fontSize = 13.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                // CAPTURE button
                Button(
                    onClick = {
                        if (!isCapturing && !isValidating) {
                            isCapturing = true
                            statusMessage = "Capturing..."
                            val executor: ExecutorService = Executors.newSingleThreadExecutor()
                            val file = File(context.filesDir, "photo_${System.currentTimeMillis()}.jpg")
                            val outputOptions = ImageCapture.OutputFileOptions.Builder(file).build()
                            imageCapture?.takePicture(
                                outputOptions,
                                executor,
                                object : ImageCapture.OnImageSavedCallback {
                                    override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                                        val bitmap = BitmapFactory.decodeFile(file.absolutePath)
                                        val variance = laplacianVariance(bitmap)
                                        android.os.Handler(android.os.Looper.getMainLooper()).post {
                                            if (variance < 100f) {
                                                statusMessage = "Photo blurry. Hold steady and retry."
                                                isCapturing = false
                                                file.delete()
                                            } else {
                                                validateAndProceed(file.absolutePath)
                                            }
                                        }
                                    }
                                    override fun onError(exception: ImageCaptureException) {
                                        android.os.Handler(android.os.Looper.getMainLooper()).post {
                                            statusMessage = "Capture failed: ${exception.message}"
                                            isCapturing = false
                                        }
                                    }
                                }
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .height(64.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = overlayColor),
                    shape = RoundedCornerShape(16.dp),
                    enabled = !isCapturing && !isValidating
                ) {
                    Text(
                        text = when {
                            isValidating -> "🔍 Checking..."
                            isCapturing -> "Capturing..."
                            else -> "📷  CAPTURE WOUND PHOTO"
                        },
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = { if (!isCapturing && !isValidating) galleryLauncher.launch("image/*") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = CardColor),
                    shape = RoundedCornerShape(16.dp),
                    enabled = !isCapturing && !isValidating
                ) {
                    Text(
                        text = "🖼️  CHOOSE FROM GALLERY",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = WhiteColor
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                TextButton(
                    onClick = { if (!isCapturing && !isValidating) onSkip() }
                ) {
                    Text(text = "Skip camera → describe symptoms only", color = GrayColor)
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

fun copyUriToFile(context: Context, uri: Uri): String? {
    return try {
        val inputStream = context.contentResolver.openInputStream(uri) ?: return null
        val file = File(context.filesDir, "gallery_${System.currentTimeMillis()}.jpg")
        file.outputStream().use { output -> inputStream.copyTo(output) }
        inputStream.close()
        file.absolutePath
    } catch (e: Exception) {
        null
    }
}

fun laplacianVariance(bitmap: Bitmap): Float {
    val scaled = Bitmap.createScaledBitmap(bitmap, 200, 200, true)
    val width = scaled.width
    val height = scaled.height
    var sum = 0.0
    var sumSq = 0.0
    var count = 0
    for (y in 1 until height - 1) {
        for (x in 1 until width - 1) {
            val center = scaled.getPixel(x, y).toLuminance()
            val top = scaled.getPixel(x, y - 1).toLuminance()
            val bottom = scaled.getPixel(x, y + 1).toLuminance()
            val left = scaled.getPixel(x - 1, y).toLuminance()
            val right = scaled.getPixel(x + 1, y).toLuminance()
            val laplacian = (4 * center - top - bottom - left - right).toDouble()
            sum += laplacian
            sumSq += laplacian * laplacian
            count++
        }
    }
    scaled.recycle()
    if (count == 0) return 0f
    val mean = sum / count
    return ((sumSq / count) - (mean * mean)).toFloat()
}

fun Int.toLuminance(): Int {
    val r = (this shr 16) and 0xFF
    val g = (this shr 8) and 0xFF
    val b = this and 0xFF
    return (0.299 * r + 0.587 * g + 0.114 * b).toInt()
}