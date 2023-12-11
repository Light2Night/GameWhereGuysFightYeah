package ui.composable.shaders

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Fill

fun DrawScope.drawStar(
    center: Offset,
    size: Float,
    color: Color,
    alpha: Float = 1.0f,
    style: DrawStyle = Fill,
    colorFilter: ColorFilter? = null,
    blendMode: BlendMode = DrawScope.DefaultBlendMode
) {
    val starPath = Path().apply {
        reset()
        // Top left arc
        arcTo(
            rect = Rect(
                left = center.x + -(size / 2),
                top = center.y + -(size / 2),
                right = center.x + (size / 2),
                bottom = center.y + (size / 2)
            ),
            startAngleDegrees = 90.0f,
            sweepAngleDegrees = -90.0f,
            forceMoveTo = false
        )
        // Top right arc
        arcTo(
            rect = Rect(
                left = center.x + (size - (size / 2)),
                top = center.y + -(size / 2),
                right = center.x + (size + (size / 2)),
                bottom = center.y + (size / 2)
            ),
            startAngleDegrees = 180.0f,
            sweepAngleDegrees = -90.0f,
            forceMoveTo = false
        )
        // Bottom right arc
        arcTo(
            rect = Rect(
                left = center.x + (size - (size / 2)),
                top = center.y + (size - (size / 2)),
                right = center.x + (size + (size / 2)),
                bottom = center.y + (size + (size / 2))
            ),
            startAngleDegrees = 270.0f,
            sweepAngleDegrees = -90.0f,
            forceMoveTo = false
        )
        // Bottom left arc
        arcTo(
            rect = Rect(
                left = center.x + -(size / 2),
                top = center.y + (size - (size / 2)),
                right = center.x + (size / 2),
                bottom = center.y + (size + (size / 2))
            ),
            startAngleDegrees = 0.0f,
            sweepAngleDegrees = -90.0f,
            forceMoveTo = false
        )
        close()
    }

    drawPath(
        path = starPath,
        color = color,
        alpha = alpha,
        style = style,
        colorFilter = colorFilter,
        blendMode = blendMode,
    )
}