package com.kenny.bookreview.ui.animate

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically

fun linearSlideInVertical() =
    slideInVertically(
        // Enters by sliding down from offset -fullHeight to 0.
        initialOffsetY = { fullHeight -> fullHeight },
        animationSpec = tween(durationMillis = 150, easing = LinearOutSlowInEasing)
    )