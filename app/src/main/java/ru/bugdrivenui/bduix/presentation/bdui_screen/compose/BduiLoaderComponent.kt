package ru.bugdrivenui.bduix.presentation.bdui_screen.compose

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.dp
import ru.bugdrivenui.bduix.presentation.bdui_screen.mapper.applyHeightSize
import ru.bugdrivenui.bduix.presentation.bdui_screen.mapper.applyWidthSize
import ru.bugdrivenui.bduix.presentation.bdui_screen.mapper.toComposeColor
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiColor
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiComponentUi

@Composable
fun BduiLoaderComponent(
    modifier: Modifier = Modifier,
    component: BduiComponentUi.Loader,
) {
    val transition = rememberInfiniteTransition(label = "loader_rot")
    val angle = transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
        ),
        label = "loader_angle"
    )
    val color = (component.color ?: BduiColor("#99FFFFFF")).toComposeColor()
    val sweep = 360f * 0.75f
    val strokeWidth = component.strokeDp.dp

    Canvas(
        modifier = modifier
            .applyWidthSize(component.width)
            .applyHeightSize(component.height)
    ) {
        val sizeMin = size.minDimension
        val sw = strokeWidth.toPx().coerceAtMost(sizeMin / 3f)
        val inset = sw / 2f
        val drawSize = Size(size.width - sw, size.height - sw)
        rotate(angle.value) {
            drawArc(
                color = color,
                startAngle = 0f,
                sweepAngle = sweep,
                useCenter = false,
                topLeft = androidx.compose.ui.geometry.Offset(inset, inset),
                size = drawSize,
                style = Stroke(width = sw, cap = StrokeCap.Round)
            )
        }
    }
}