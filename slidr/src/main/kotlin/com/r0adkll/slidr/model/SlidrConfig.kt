package com.r0adkll.slidr.model

import android.graphics.Color
import androidx.annotation.ColorInt

data class SlidrConfig(
    @ColorInt val colorPrimary: Int? = null,
    @ColorInt val colorSecondary: Int? = null,
    val touchSize: Float = -1f,
    val sensitivity: Float = 1f,
    @ColorInt val scrimColor: Int = Color.BLACK,
    val scrimStartAlpha: Float = 0.8f,
    val scrimEndAlpha: Float = 0f,
    val velocityThreshold: Float = 5f,
    val distanceThreshold: Float = 0.25f,
    val edgeOnly: Boolean = false,
    private val edgeSize: Float = 0.18f,
    val position: SlidrPosition = SlidrPosition.Left,
    val listener: SlidrListener = slidrListener(),
) {

    fun getEdgeSize(size: Float): Float = edgeSize * size
}
