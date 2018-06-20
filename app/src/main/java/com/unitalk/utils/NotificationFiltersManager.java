package com.unitalk.utils;

import android.app.NotificationManager;
import android.support.annotation.NonNull;

import com.unitalk.ui.callback.OnNotificationSettingsCallback;

public class NotificationFiltersManager {
    private NotificationManager notificationManager;
    private OnNotificationSettingsCallback onNotificationSettingsCallback;

    public NotificationFiltersManager(@NonNull final OnNotificationSettingsCallback onNotificationSettingsCallback) {
        this.onNotificationSettingsCallback = onNotificationSettingsCallback;
        createNotificationManager();
    }

    private void createNotificationManager() {
        this.notificationManager = onNotificationSettingsCallback.createNotificationManager();
    }

    public boolean isNotificationPolicyAccessGranted() {
        return notificationManager.isNotificationPolicyAccessGranted();
    }
}
