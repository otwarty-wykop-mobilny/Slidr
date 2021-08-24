package com.r0adkll.slidr.util;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.WindowManager;

import androidx.annotation.NonNull;

public class Utils {

    public static int getNavigationBarSize(@NonNull Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point appUsableSize = getAppUsableScreenSize(windowManager);
        Point realScreenSize = getRealScreenSize(windowManager);

        // navigation bar on the right
        if (appUsableSize.x < realScreenSize.x && windowManager.getDefaultDisplay().getRotation() == Surface.ROTATION_270) {
            Log.v("Test", Integer.toString(realScreenSize.x - appUsableSize.x));
            return realScreenSize.x - appUsableSize.x;
        }

        // navigation bar is not present
        return 0;
    }

    @NonNull
    public static Point getAppUsableScreenSize(@NonNull WindowManager windowManager) {
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size;
    }

    @NonNull
    public static Point getRealScreenSize(@NonNull WindowManager windowManager) {
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();

        display.getRealSize(size);

        return size;
    }
}
