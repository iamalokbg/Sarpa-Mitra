package com.sarpamitra.benchmark

import android.util.Log

object BenchmarkLogger {

    private const val TAG = "SARPA_BENCHMARK"
    private val results = mutableListOf<BenchmarkResult>()

    data class BenchmarkResult(
        val run: Int,
        val modelLoadMs: Long,
        val inferenceMs: Long,
        val peakRamMb: Long,
        val totalMs: Long
    )

    fun measureModelLoad(block: () -> Unit): Long {
        val start = System.currentTimeMillis()
        block()
        val elapsed = System.currentTimeMillis() - start
        Log.d(TAG, "MODEL_LOAD: ${elapsed}ms")
        return elapsed
    }

    fun measureInference(run: Int, modelLoadMs: Long, block: () -> Unit): BenchmarkResult {
        val runtime = Runtime.getRuntime()
        val ramBefore = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024

        val start = System.currentTimeMillis()
        block()
        val inferenceMs = System.currentTimeMillis() - start

        val ramAfter = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024
        val peakRam = maxOf(ramBefore, ramAfter)

        val result = BenchmarkResult(
            run = run,
            modelLoadMs = modelLoadMs,
            inferenceMs = inferenceMs,
            peakRamMb = peakRam,
            totalMs = modelLoadMs + inferenceMs
        )

        results.add(result)
        Log.d(TAG, "RUN_$run: inference=${inferenceMs}ms, ram=${peakRam}MB")
        return result
    }

    fun getSummary(): String {
        if (results.isEmpty()) return "No benchmark data"
        val avgInference = results.map { it.inferenceMs }.average().toLong()
        val maxRam = results.maxOf { it.peakRamMb }
        val minInference = results.minOf { it.inferenceMs }
        val maxInference = results.maxOf { it.inferenceMs }

        return """
SARPA-MITRA BENCHMARKS
Device: Xiaomi (4GB RAM)
Model: gemma-2b-it-cpu-int8.bin
Mode: Airplane mode (offline)
Runs: ${results.size}

Model load: ${results.first().modelLoadMs}ms
Avg inference: ${avgInference}ms
Min inference: ${minInference}ms  
Max inference: ${maxInference}ms
Peak RAM: ${maxRam}MB
        """.trimIndent()
    }

    fun clear() = results.clear()
}