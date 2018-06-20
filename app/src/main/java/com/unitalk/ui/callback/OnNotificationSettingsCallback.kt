package com.unitalk.ui.callback

import android.app.NotificationManager

interface OnNotificationSettingsCallback {
    fun createNotificationManager(): NotificationManager
    fun showNotificationSettingsDialog()
}
