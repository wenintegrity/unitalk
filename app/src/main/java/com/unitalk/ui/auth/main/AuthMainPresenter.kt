package com.unitalk.ui.auth.main

import android.content.Intent
import com.google.android.gms.common.api.GoogleApiClient
import com.unitalk.ui.BasePresenter

interface AuthMainPresenter<AuthMainView> : BasePresenter {
    fun createFacebookAuth()
    fun checkCurrentGoogleAcc(googleApiClient: GoogleApiClient)
    fun handleGoogleResult(requestCode: Int, data: Intent)
    fun goToFacebookAuthResult(requestCode: Int, resultCode: Int, data: Intent)
}