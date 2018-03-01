package com.r0adkll.slidr.util;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.WindowManager;

import java.lang.reflect.InvocationTargetException;

public class Utils {
    public static int getNavigationBarSize(Context context) {
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

    public static Point getAppUsableScreenSize(WindowManager windowManager) {
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size;
    }

    public static Point getRealScreenSize(WindowManager windowManager) {
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();

        if (Build.VERSION.SDK_INT >= 17) {
            display.getRealSize(size);
        } else if (Build.VERSION.SDK_INT >= 14) {
            try {
                size.x = (Integer)     Display.class.getMethod("getRawWidth").invoke(display);
                size.y = (Integer) Display.class.getMethod("getRawHeight").invoke(display);
            } catch (IllegalAccessException e) {} catch     (InvocationTargetException e) {} catch (NoSuchMethodException e) {}
        }

        return size;
    }
}
