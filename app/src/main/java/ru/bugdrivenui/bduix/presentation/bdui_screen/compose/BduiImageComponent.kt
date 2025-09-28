package ru.bugdrivenui.bduix.presentation.bdui_screen.compose

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ru.bugdrivenui.bduix.presentation.bdui_screen.mapper.applyHeightSize
import ru.bugdrivenui.bduix.presentation.bdui_screen.mapper.applyWidthSize
import ru.bugdrivenui.bduix.presentation.bdui_screen.mapper.asComposeShape
import ru.bugdrivenui.bduix.presentation.bdui_screen.mapper.toComposeColor
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.*

@Composable
fun BduiImageComponent(
    modifier: Modifier = Modifier,
    component: BduiComponentUi.Image,
    contentScale: ContentScale = ContentScale.Crop,
    enableFade: Boolean = true,
) {
    val context = LocalContext.current
    val shape = component.shape.asComposeShape()
    val url = component.imageUrl.ifBlank { "https://placekitten.com/800/400" }

    val request = remember(url) {
        ImageRequest.Builder(context)
            .data(url)
            .crossfade(true)
            .build()
    }

    var loaded by remember { mutableStateOf(false) }
    val alpha by animateFloatAsState(
        targetValue = if (!enableFade || loaded) 1f else 0f,
        animationSpec = tween(if (enableFade) 350 else 0),
        label = "fade"
    )

    Box(modifier) {

        Box(
            Modifier
                .applyWidthSize(component.width)
                .applyHeightSize(component.height)
                .clip(shape)
        ) {
            AsyncImage(
                model = request,
                contentDescription = null,
                contentScale = contentScale,
                onSuccess = { loaded = true },
                onError = { loaded = true },
                onLoading = { if (!loaded) loaded = false },
                modifier = Modifier
                    .matchParentSize()
                    .alpha(alpha)
            )

            component.border?.let { b ->
                if (b.thickness > 0) {
                    Canvas(Modifier.matchParentSize()) {
                        val strokeWidth = b.thickness.dp.toPx()
                        val inset = strokeWidth / 2f
                        val innerSize = Size(
                            width = size.width - strokeWidth,
                            height = size.height - strokeWidth
                        )
                        val outline = shape.createOutline(innerSize, layoutDirection, this)
                        val path: Path = when (outline) {
                            is Outline.Rectangle -> Path().apply { addRect(outline.rect) }
                            is Outline.Rounded -> Path().apply { addRoundRect(outline.roundRect) }
                            is Outline.Generic -> outline.path
                        }
                        translate(inset, inset) {
                            drawPath(
                                path = path,
                                color = b.color.toComposeColor(),
                                style = Stroke(width = strokeWidth)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BduiImage_Preview() {
    val component = BduiComponentUi.Image(
        id = "img_no_bg",
        hash = "hash_no_bg",
        interactions = null,
        paddings = null,
        margins = BduiComponentInsetsUi(12, 12, 12, 12),
        width = BduiComponentSize.Fixed(220),
        height = BduiComponentSize.Fixed(140),
        backgroundColor = null,
        border = BduiBorder(
            color = BduiColor("#965EEB"),
            thickness = 2
        ),
        shape = BduiShape.RoundedCorners(20, 20, 20, 20),
        imageUrl = "https://i.ytimg.com/vi/ArbiGwbF90A/maxresdefault.jpg?sqp=-oaymwEmCIAKENAF8quKqQMa8AEB-AH-CYAC0AWKAgwIABABGGUgUyhHMA8=&rs=AOn4CLDRBiDu5SDbBkAWmCnYDOp371Q7lw",
    )
    BduiImageComponent(component = component)
}



