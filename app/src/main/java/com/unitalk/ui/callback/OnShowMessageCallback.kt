package com.unitalk.ui.callback

import android.support.annotation.StringRes

interface OnShowMessageCallback {
    fun showMessage(@StringRes messageID: Int)
}
