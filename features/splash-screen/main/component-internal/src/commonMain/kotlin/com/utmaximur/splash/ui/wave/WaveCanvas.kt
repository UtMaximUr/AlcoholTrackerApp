package com.utmaximur.splash.ui.wave

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlin.math.max
import kotlin.math.min

class WaveCanvas {

    private val waveCount: Int = 2
    private var wavePointCount: Int = 5
    private var waveSpeed: Float = 0.15f

    private val waves: MutableList<Wave> = mutableListOf()

    fun setup(width: Float, height: Float) {
        for (i in 0 until waveCount) {
            waves.add(Wave(pointCount = wavePointCount, canvasWidth = width, canvasHeight = height))
        }
    }

    private fun update() {
        waves.toList().forEachIndexed { index, wave ->
            if (wave.pointCount != wavePointCount) {
                waves[index] = Wave(wavePointCount, wave.canvasWidth, wave.canvasHeight)
            }
        }
        waves.forEach {
            it.update(speed = waveSpeed)
        }
    }

    @Composable
    fun Render(
        modifier: Modifier = Modifier,
        wavePointCount: Int,
        waveColors: List<Color>,
        waveSpeed: Float
    ) {

        this.wavePointCount = wavePointCount
        this.waveSpeed = waveSpeed
        val frameState = StepFrame {
            update()
        }

        Canvas(
            modifier = modifier
        ) {
            frameState.value
            waves.forEach { wave ->
                drawWave(wave = wave, color = waveColors.first())
            }
        }
    }

    companion object {
        fun calculateWaveProgress(minProgress: Float = 0.03f, maxProgress: Float = 0.99f, progress: Float): Float {
            return min(maxProgress, max(minProgress, progress))
        }
    }
}