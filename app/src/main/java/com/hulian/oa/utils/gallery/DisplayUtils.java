package com.hulian.oa.utils.gallery;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public final class DisplayUtils {
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }

    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }

    public static int getItemWidth(Context context) {
       // return getScreenWidth(context) - dip2px(context, 50);
        return getScreenWidth(context) - dip2px(context, 20);
    }
    public static int getItemWidth2(Context context) {
       return getScreenWidth(context) - dip2px(context, 50);
    }
//    public static int getItemHeight(Context context) {
//        return (int) (getItemWidth(context) * 0.56);
//    }
    public static int getItemHeight(Context context) {
        return dip2px(context,800);
    }

    /**
     * dip转px
     */
    public static int dip2px(Context context, float dipValue) {
        if (context == null) {
            return (int) dipValue;
        }
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        if (displayMetrics != null) {
            final float scale = displayMetrics.density;
            return (int) (dipValue * scale + 0.5f);
        } else {
            return (int) (dipValue * 3 + 0.5f) /* 使用主流手机的 density */;
        }
    }

    /**
     * px转dip
     */
    public static int px2dip(Context context, float pxValue) {
        if (context == null) {
            return (int) pxValue;
        }
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        if (displayMetrics != null) {
            final float scale = displayMetrics.density;
            return (int) (pxValue / scale + 0.5f);
        } else {
            return (int) (pxValue / 3 + 0.5f) /* 使用主流手机的 density */;
        }
    }
}
