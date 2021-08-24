package com.r0adkll.slidr.widget;


import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;

import com.r0adkll.slidr.model.SlidrPosition;


final class ScrimRenderer {

    private final View rootView;
    private final View decorView;
    private final Rect dirtyRect;

    ScrimRenderer(@NonNull View rootView, @NonNull View decorView) {
        this.rootView = rootView;
        this.decorView = decorView;
        dirtyRect = new Rect();
    }

    void render(Canvas canvas, SlidrPosition position, Paint paint) {
        switch (position) {
            case Left:
                renderLeft(canvas, paint);
                break;
            case Right:
                renderRight(canvas, paint);
                break;
            case Top:
                renderTop(canvas, paint);
                break;
            case Bottom:
                renderBottom(canvas, paint);
                break;
            case Vertical:
                renderVertical(canvas, paint);
                break;
            case Horizontal:
                renderHorizontal(canvas, paint);
                break;
        }
    }


    Rect getDirtyRect(SlidrPosition position) {
        switch (position) {
            case Left:
                dirtyRect.set(0, 0, decorView.getLeft(), rootView.getMeasuredHeight());
                break;
            case Right:
                dirtyRect.set(decorView.getRight(), 0, rootView.getMeasuredWidth(), rootView.getMeasuredHeight());
                break;
            case Top:
                dirtyRect.set(0, 0, rootView.getMeasuredWidth(), decorView.getTop());
                break;
            case Bottom:
                dirtyRect.set(0, decorView.getBottom(), rootView.getMeasuredWidth(), rootView.getMeasuredHeight());
                break;
            case Vertical:
                if (decorView.getTop() > 0) {
                    dirtyRect.set(0, 0, rootView.getMeasuredWidth(), decorView.getTop());
                } else {
                    dirtyRect.set(0, decorView.getBottom(), rootView.getMeasuredWidth(), rootView.getMeasuredHeight());
                }
                break;
            case Horizontal:
                if (decorView.getLeft() > 0) {
                    dirtyRect.set(0, 0, decorView.getLeft(), rootView.getMeasuredHeight());
                } else {
                    dirtyRect.set(decorView.getRight(), 0, rootView.getMeasuredWidth(), rootView.getMeasuredHeight());
                }
                break;
        }
        return dirtyRect;
    }


    private void renderLeft(Canvas canvas, Paint paint) {
        canvas.drawRect(0, 0, decorView.getLeft(), rootView.getMeasuredHeight(), paint);
    }


    private void renderRight(Canvas canvas, Paint paint) {
        canvas.drawRect(decorView.getRight(), 0, rootView.getMeasuredWidth(), rootView.getMeasuredHeight(), paint);
    }


    private void renderTop(Canvas canvas, Paint paint) {
        canvas.drawRect(0, 0, rootView.getMeasuredWidth(), decorView.getTop(), paint);
    }


    private void renderBottom(Canvas canvas, Paint paint) {
        canvas.drawRect(0, decorView.getBottom(), rootView.getMeasuredWidth(), rootView.getMeasuredHeight(), paint);
    }


    private void renderVertical(Canvas canvas, Paint paint) {
        if (decorView.getTop() > 0) {
            renderTop(canvas, paint);
        } else {
            renderBottom(canvas, paint);
        }
    }


    private void renderHorizontal(Canvas canvas, Paint paint) {
        if (decorView.getLeft() > 0) {
            renderLeft(canvas, paint);
        } else {
            renderRight(canvas, paint);
        }
    }
}
