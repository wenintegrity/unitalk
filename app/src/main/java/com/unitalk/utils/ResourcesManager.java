package com.unitalk.utils;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

public class ResourcesManager {
    private static volatile ResourcesManager repository;
    private static Resources resources;

    public static void with(@NonNull final Context context) throws SecurityException {
        if (repository == null) {
            synchronized (ResourcesManager.class) {
                if (repository == null) {
                    repository = new ResourcesManager(context);
                }
            }
        }
    }

    private ResourcesManager(@NonNull final Context context) {
        resources = context.getResources();
    }

    public static String getString(@StringRes final int stringID) {
        return resources.getString(stringID);
    }

    public static int getColor(@ColorRes final int colorID) {
        return resources.getColor(colorID, null);
    }

    public static Drawable getDrawable(@DrawableRes final int drawableID) {
        return resources.getDrawable(drawableID, null);
    }
}