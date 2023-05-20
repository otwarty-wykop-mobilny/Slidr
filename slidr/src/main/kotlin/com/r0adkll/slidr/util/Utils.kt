package com.r0adkll.slidr.util

import android.content.Context
import android.graphics.Point
import android.os.Build
import android.view.Surface
import android.view.WindowManager

internal fun Context.getNavigationBarSize(): Int {
    val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val (usableWidth, _) = getAppUsableScreenSize(windowManager)
    val (realScreenWidth, _) = getRealScreenSize(windowManager)

    @Suppress("DEPRECATION")
    val rotation = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        display?.rotation
    } else {
        windowManager.defaultDisplay?.rotation
    }
    // navigation bar on the right
    return if (usableWidth < realScreenWidth && rotation == Surface.ROTATION_270) {
        realScreenWidth - usableWidth
    } else {
        0
    }
}

private fun getAppUsableScreenSize(windowManager: WindowManager) = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
    windowManager.currentWindowMetrics.bounds.run { width() to height() }
} else {
    @Suppress("DEPRECATION")
    Point().apply(windowManager.defaultDisplay::getSize).run { x to y }
}

private fun getRealScreenSize(windowManager: WindowManager) = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
    windowManager.currentWindowMetrics.bounds.run { width() to height() }
} else {
    @Suppress("DEPRECATION")
    Point().apply(windowManager.defaultDisplay::getRealSize).run { x to y }
}
