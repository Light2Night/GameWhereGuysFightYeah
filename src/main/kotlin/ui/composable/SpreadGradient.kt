package ui.composable

/*import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.geometry.isUnspecified
import androidx.compose.ui.graphics.*

class SpreadGradient(
    private val colors: List<Color>,
    private val stops: List<Float>? = null,
    private val radius: Float,
    private val tileMode: TileMode = TileMode.Clamp,
) : ShaderBrush() {

    override val intrinsicSize: Size
        get() = if (radius.isFinite()) Size(radius * 2, radius * 2) else Size.Unspecified

    override fun createShader(size: Size): Shader {
        val centerX: Float
        val centerY: Float
        if (center.isUnspecified) {
            val drawCenter = size.center
            centerX = drawCenter.x
            centerY = drawCenter.y
        } else {
            centerX = if (center.x == Float.POSITIVE_INFINITY) size.width else center.x
            centerY = if (center.y == Float.POSITIVE_INFINITY) size.height else center.y
        }

        return RadialGradientShader(
            colors = colors,
            colorStops = stops,
            center = Offset(centerX, centerY),
            radius = if (radius == Float.POSITIVE_INFINITY) size.minDimension / 2 else radius,
            tileMode = tileMode
        )
    }
}*/