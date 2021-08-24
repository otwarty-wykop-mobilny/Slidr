package com.r0adkll.slidr.model;

/**
 * This listener interface is for receiving events from the sliding panel such as state changes
 * and slide progress
 */
public interface SlidrListener {

    /**
     * This is called when the {@link androidx.customview.widget.ViewDragHelper} calls it's
     * state change callback.
     *
     * @param state the {@link androidx.customview.widget.ViewDragHelper} state
     * @see androidx.customview.widget.ViewDragHelper#STATE_IDLE
     * @see androidx.customview.widget.ViewDragHelper#STATE_DRAGGING
     * @see androidx.customview.widget.ViewDragHelper#STATE_SETTLING
     */
    void onSlideStateChanged(int state);

    void onSlideChange(float percent);

    void onSlideOpened();

    void onSlideClosed();
}
