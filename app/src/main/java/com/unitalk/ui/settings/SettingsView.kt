package com.unitalk.ui.settings

import com.unitalk.ui.BaseActivity
import com.unitalk.ui.callback.OnShowMessageCallback

interface SettingsView : OnShowMessageCallback {
    fun onVoiceLevelChanged()
    fun goToActivity(obj:Class<out BaseActivity>)
}
