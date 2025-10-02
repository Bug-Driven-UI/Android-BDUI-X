package ru.bugdrivenui.bduix.presentation.bdui_screen.compose

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiComponentUi

@Composable
fun BduiImageComponent(
    modifier: Modifier = Modifier,
    component: BduiComponentUi.Image,
    contentScale: ContentScale = ContentScale.Crop,
    enableFade: Boolean = true,
) {
    val context = LocalContext.current
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


    AsyncImage(
        model = request,
        contentDescription = null,
        contentScale = contentScale,
        modifier = modifier.alpha(alpha),
        onSuccess = { loaded = true },
        onError = { loaded = true },
        onLoading = { if (!loaded) loaded = false },
    )
}
