package com.unitalk.core

import android.webkit.JavascriptInterface
import com.unitalk.ui.callback.OnActionCallback

class WebAppInterface(private val onActionCallback: OnActionCallback) {

    @JavascriptInterface
    fun onPageEnded() {
        onActionCallback.moveToNextScreen()
    }
}
