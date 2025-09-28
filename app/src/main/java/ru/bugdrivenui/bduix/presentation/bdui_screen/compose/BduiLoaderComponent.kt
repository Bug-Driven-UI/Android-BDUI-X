package ru.bugdrivenui.bduix.presentation.bdui_screen.compose

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import ru.bugdrivenui.bduix.presentation.bdui_screen.mapper.toComposeColor
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiColor
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiComponentUi

@Composable
fun BduiLoaderComponent(
    modifier: Modifier,
    component: BduiComponentUi.Loader,
) {
    val transition = rememberInfiniteTransition(label = "loader_rot")
    val angle by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
        ),
        label = "loader_angle"
    )

    val color = (component.color ?: BduiColor("#99FFFFFF")).toComposeColor()
    val sweep = 360f * 0.75f
    val strokeDp = component.strokeDp.dp
    val density = LocalDensity.current

    Box(
        modifier = modifier.drawBehind {
            val strokePx = with(density) { strokeDp.toPx() }
            val sizeMin = size.minDimension
            val sw = strokePx.coerceAtMost(sizeMin / 3f)
            val inset = sw / 2f
            val arcSize = Size(size.width - sw, size.height - sw)
            rotate(angle) {
                drawArc(
                    color = color,
                    startAngle = 0f,
                    sweepAngle = sweep,
                    useCenter = false,
                    topLeft = Offset(inset, inset),
                    size = arcSize,
                    style = Stroke(width = sw, cap = StrokeCap.Round)
                )
            }
        }
    )
}