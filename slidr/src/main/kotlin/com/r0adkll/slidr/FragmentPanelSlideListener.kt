package com.r0adkll.slidr

import android.view.View
import androidx.fragment.app.FragmentActivity
import com.r0adkll.slidr.model.SlidrConfig
import com.r0adkll.slidr.widget.SliderPanel.OnPanelSlideListener

internal class FragmentPanelSlideListener(
    private val view: View,
    private val config: SlidrConfig,
) : OnPanelSlideListener {

    override fun onStateChanged(state: Int) {
        config.listener?.onSlideStateChanged(state)
    }

    override fun onClosed() {
        config.listener?.onSlideClosed()

        // Ensure that we are attached to a FragmentActivity
        val activity = view.context as? FragmentActivity ?: return
        if (activity.supportFragmentManager.backStackEntryCount == 0) {
            activity.finish()
            activity.overridePendingTransition(0, 0)
        } else {
            activity.supportFragmentManager.popBackStack()
        }
    }

    override fun onOpened() {
        if (config.listener != null) {
            config.listener.onSlideOpened()
        }
    }

    override fun onSlideChange(percent: Float) {
        if (config.listener != null) {
            config.listener.onSlideChange(percent)
        }
    }
}
