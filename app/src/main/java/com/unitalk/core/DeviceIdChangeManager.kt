package com.unitalk.core

class DeviceIdChangeManager {
    companion object {
        fun setEmailOrUniqueId(emailOrUniqueId: String) {
            App.getInstance().sharedManager.uniqueID = emailOrUniqueId
        }
    }
}