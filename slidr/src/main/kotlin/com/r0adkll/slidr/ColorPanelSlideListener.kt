package com.r0adkll.slidr

import android.animation.ArgbEvaluator
import android.app.Activity
import androidx.annotation.ColorInt
import com.r0adkll.slidr.widget.SliderPanel.OnPanelSlideListener

internal open class ColorPanelSlideListener(
    private val activity: Activity,
    @param:ColorInt protected val primaryColor: Int,
    @param:ColorInt protected val secondaryColor: Int
) : OnPanelSlideListener {

    private val evaluator = ArgbEvaluator()

    override fun onStateChanged(state: Int) = Unit

    override fun onClosed() {
        activity.finish()
        activity.overridePendingTransition(0, 0)
    }

    override fun onOpened() = Unit

    override fun onSlideChange(percent: Float) {
        if (areColorsValid()) {
            val newColor = evaluator.evaluate(percent, primaryColor, secondaryColor) as Int
            activity.window.statusBarColor = newColor
        }
    }

    protected fun areColorsValid(): Boolean {
        return primaryColor != -1 && secondaryColor != -1
    }
}
