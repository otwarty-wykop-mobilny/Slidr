package com.r0adkll.slidr

import android.app.Activity
import com.r0adkll.slidr.model.SlidrConfig

internal class ConfigPanelSlideListener(
    activity: Activity,
    private val config: SlidrConfig
) : ColorPanelSlideListener(activity, config.primaryColor, config.secondaryColor) {

    override fun onStateChanged(state: Int) {
        config.listener?.onSlideStateChanged(state)
    }

    override fun onClosed() {
        config.listener?.onSlideClosed()
        super.onClosed()
    }

    override fun onOpened() {
        config.listener?.onSlideOpened()
    }

    override fun onSlideChange(percent: Float) {
        super.onSlideChange(percent)
        config.listener?.onSlideChange(percent)
    }
}
