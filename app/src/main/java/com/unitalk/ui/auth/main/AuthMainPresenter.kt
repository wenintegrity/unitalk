package com.unitalk.ui.auth.main

import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.GoogleApiClient
import com.unitalk.ui.BasePresenter

interface AuthMainPresenter<AuthMainView> : BasePresenter {
    fun createFacebookAuth()
    //fun checkCurrentGoogleAcc(googleApiClient: GoogleApiClient)
    fun checkCurrentGoogleAcc(googleApiClient: GoogleSignInAccount)
    fun handleGoogleResult(requestCode: Int, data: Intent)
    fun goToFacebookAuthResult(requestCode: Int, resultCode: Int, data: Intent)
    fun checkCurrentUser(context: Context)
}