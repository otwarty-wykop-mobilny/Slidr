package com.r0adkll.slidr.model

/**
 * This listener interface is for receiving events from the sliding panel such as state changes
 * and slide progress
 */
interface SlidrListener {

    /**
     * This is called when the [androidx.customview.widget.ViewDragHelper] calls it's
     * state change callback.
     *
     * @param state the [androidx.customview.widget.ViewDragHelper] state
     * @see androidx.customview.widget.ViewDragHelper.STATE_IDLE
     *
     * @see androidx.customview.widget.ViewDragHelper.STATE_DRAGGING
     *
     * @see androidx.customview.widget.ViewDragHelper.STATE_SETTLING
     */
    fun onSlideStateChanged(state: Int)

    fun onSlideChange(percent: Float)

    fun onSlideOpened()

    fun onSlideClosed()
}

fun slidrListener(
    onSlideStateChanged: (Int) -> Unit,
    onSlideChange: (Float) -> Unit,
    onSlideOpened: () -> Unit,
    onSlideClosed: () -> Unit,
): SlidrListener = object : SlidrListener {

    override fun onSlideStateChanged(state: Int) = onSlideStateChanged(state)

    override fun onSlideChange(percent: Float) = onSlideChange(percent)

    override fun onSlideOpened() = onSlideOpened()

    override fun onSlideClosed() = onSlideClosed()
}
