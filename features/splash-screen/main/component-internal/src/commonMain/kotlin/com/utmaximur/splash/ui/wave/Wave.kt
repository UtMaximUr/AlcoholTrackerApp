package com.utmaximur.splash.ui.wave

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope

data class Wave(
    val pointCount: Int = 5,
    val canvasWidth: Float,
    val canvasHeight: Float,
) {
    val points: MutableList<Point> = mutableListOf()

    init {
        val gap: Float = canvasWidth / (pointCount - 1)
        for (i in 0 until pointCount) {
            points.add(Point(index = i, x = gap * i, y = 0f))
        }
    }

    fun update(speed: Float) {
        points.forEach {
            it.update(speed = speed)
        }
    }
}

fun DrawScope.drawWave(
    wave: Wave,
    color: Color
) {
    val path = Path()
    path.reset()

    var prevPoint = wave.points.firstOrNull() ?: Point(0, 0f, 0f)
    prevPoint.apply {
        path.moveTo(x, y)
    }

    wave.points.forEachIndexed { index, point ->
        kotlin.runCatching {
            wave.points[index + 1].apply {
                val x2 = (prevPoint.x + this.x) / 2
                val y2 = (prevPoint.y + this.y) / 2
                path.quadraticTo(point.x, point.y, x2, y2)
                prevPoint = this
            }
        }
    }

    path.lineTo(prevPoint.x, prevPoint.y)
    path.lineTo(wave.canvasWidth, wave.canvasHeight)
    path.lineTo(wave.points.firstOrNull()?.x ?: 0f, wave.canvasHeight)
    path.close()

    drawPath(path = path, color = color)
}