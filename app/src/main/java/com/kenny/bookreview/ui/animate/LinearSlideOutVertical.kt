package com.kenny.bookreview.ui.animate

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideOutVertically

fun linearSlideOutVertical() =
    slideOutVertically(
        // Exits by sliding up from offset 0 to -fullHeight.
        targetOffsetY = { fullHeight -> fullHeight },
        animationSpec = tween(durationMillis = 150, easing = FastOutLinearInEasing)
    )
