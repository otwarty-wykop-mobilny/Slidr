package com.r0adkll.slidr.util

import android.content.Context
import android.graphics.Point
import android.view.Surface
import android.view.WindowManager

internal object Utils {

    @JvmStatic
    fun getNavigationBarSize(context: Context): Int {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val appUsableSize = getAppUsableScreenSize(windowManager)
        val realScreenSize = getRealScreenSize(windowManager)

        // navigation bar on the right
        return if (appUsableSize.x < realScreenSize.x && windowManager.defaultDisplay.rotation == Surface.ROTATION_270) {
            realScreenSize.x - appUsableSize.x
        } else {
            0
        }
    }

    private fun getAppUsableScreenSize(windowManager: WindowManager) =
        Point().apply(windowManager.defaultDisplay::getSize)

    private fun getRealScreenSize(windowManager: WindowManager): Point {
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getRealSize(size)
        return size
    }
}
