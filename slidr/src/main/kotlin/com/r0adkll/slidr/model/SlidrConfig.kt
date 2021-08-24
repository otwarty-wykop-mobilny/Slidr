package com.r0adkll.slidr.model

import android.graphics.Color
import androidx.annotation.ColorInt

data class SlidrConfig(
    val colorPrimary: Int = -1,
    val colorSecondary: Int = -1,
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
    val listener: SlidrListener? = null,
) {

    fun getEdgeSize(size: Float): Float = edgeSize * size
}
