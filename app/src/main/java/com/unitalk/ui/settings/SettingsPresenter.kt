package com.unitalk.ui.settings

import com.unitalk.ui.BasePresenter

interface SettingsPresenter<SettingsView> : BasePresenter {
    fun setVoiceLevel(voiceLevel: String)
    fun logOut()
}
