package com.unitalk.ui.settings

import com.unitalk.ui.callback.OnShowMessageCallback

interface SettingsView : OnShowMessageCallback {
    fun onVoiceLevelChanged()
}
