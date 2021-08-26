package com.r0adkll.slidr

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import com.r0adkll.slidr.model.SlidrConfig
import com.r0adkll.slidr.model.SlidrInterface
import com.r0adkll.slidr.widget.SliderPanel

/**
 * Attach a slider mechanism to a fragment view replacing an internal view
 *
 * @receiver the view within a fragment to replace
 * @param config    the slider configuration to attach with
 * @return a [com.r0adkll.slidr.model.SlidrInterface] that allows
 * the user to lock/unlock the sliding mechanism for whatever purpose.
 */
fun View.replaceSlidr(config: SlidrConfig): SlidrInterface {
    val parent = parent as ViewGroup
    val params = layoutParams
    parent.removeView(this)

    // Setup the slider panel and attach it
    val panel = SliderPanel(context, this, config)
    panel.id = R.id.slidable_panel
    this.id = R.id.slidable_content
    panel.addView(this)
    parent.addView(panel, 0, params)

    // Set the panel slide listener for when it becomes closed or opened
    panel.setOnPanelSlideListener(FragmentPanelSlideListener(this, config))

    // Return the lock interface
    return panel.defaultInterface
}

/**
 * Attach a slider mechanism to an activity based on the passed [com.r0adkll.slidr.model.SlidrConfig]
 *
 * @param activity the activity to attach the slider to
 * @param config   the slider configuration to make
 * @return a [com.r0adkll.slidr.model.SlidrInterface] that allows
 * the user to lock/unlock the sliding mechanism for whatever purpose.
 */
fun Activity.attachSlidr(config: SlidrConfig = SlidrConfig()): SlidrInterface {
    // Hijack the decorView
    val decorView = window.decorView as ViewGroup
    val oldScreen = decorView.getChildAt(0)
    decorView.removeViewAt(0)

    // Setup the slider panel and attach it to the decor
    val panel = SliderPanel(this, oldScreen, config)
    panel.id = R.id.slidable_panel
    oldScreen.id = R.id.slidable_content
    panel.addView(oldScreen)
    decorView.addView(panel, 0)

    panel.setOnPanelSlideListener(ConfigPanelSlideListener(this, config))

    return panel.defaultInterface
}
