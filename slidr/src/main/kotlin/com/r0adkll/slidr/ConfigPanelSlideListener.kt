package com.r0adkll.slidr

import android.animation.ArgbEvaluator
import android.app.Activity
import com.r0adkll.slidr.model.SlidrConfig
import com.r0adkll.slidr.widget.SliderPanel

internal class ConfigPanelSlideListener(
    private val activity: Activity,
    private val config: SlidrConfig,
) : SliderPanel.OnPanelSlideListener {

    private val evaluator = ArgbEvaluator()

    override fun onStateChanged(state: Int) {
        config.listener.onSlideStateChanged(state)
    }

    override fun onClosed() {
        config.listener.onSlideClosed()
        activity.finish()
        activity.overridePendingTransition(0, 0)
    }

    override fun onOpened() {
        config.listener.onSlideOpened()
    }

    override fun onSlideChange(percent: Float) {
        if (config.colorPrimary != null && config.colorSecondary != null) {
            val newColor = evaluator.evaluate(percent, config.colorPrimary, config.colorSecondary) as Int
            activity.window.statusBarColor = newColor
        }
        config.listener.onSlideChange(percent)
    }
}
