package com.unitalk.ui.auth.main

import android.content.Context
import android.content.Intent
import com.facebook.AccessToken
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.api.GoogleApiClient
import com.unitalk.core.App
import com.unitalk.core.DeviceIdChangeManager
import com.unitalk.ui.BasePresenterImpl
import com.unitalk.ui.callback.OnFacebookAuthCallback
import com.unitalk.ui.callback.OnShowMessageCallback
import com.unitalk.utils.FacebookAuthManager

class AuthMainPresenterImpl(private val view: AuthMainView, private val onShowMessageCallback: OnShowMessageCallback) : BasePresenterImpl(), AuthMainPresenter<AuthMainView>, OnFacebookAuthCallback {
    private val REQUEST_CODE_GOOGLE_SIGNIN = 7
    private var facebookAuthManager: FacebookAuthManager? = null

    override fun createFacebookAuth() {
        facebookAuthManager = FacebookAuthManager(this, onShowMessageCallback)
    }

    /*override fun checkCurrentGoogleAcc(googleApiClient: GoogleApiClient) {

        val opr = Auth.GoogleSignInApi.silentSignIn(googleApiClient)
        if (opr.isDone) {
            val result = opr.get()
            handleSignInResult(result)
        } else {
            opr.setResultCallback { googleSignInResult -> handleSignInResult(googleSignInResult) }
        }
    }*/

    override fun checkCurrentGoogleAcc(googleApiClient: GoogleSignInAccount) {
        addMailInUniqueId(googleApiClient)
        view.onAuthSuccessful()
    }

    override fun handleGoogleResult(requestCode: Int, data: Intent) {
        if (requestCode == REQUEST_CODE_GOOGLE_SIGNIN) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            handleSignInResult(result)
        }
    }

    override fun goToFacebookAuthResult(requestCode: Int, resultCode: Int, data: Intent) {
        facebookAuthManager!!.onFacebookAuthResult(requestCode, resultCode, data)
    }

    override fun onFacebookAuthResult() {
        view.onAuthSuccessful()
    }

    private fun handleSignInResult(result: GoogleSignInResult) {
        if (result.isSuccess) {
            val account = result.signInAccount
            addMailInUniqueId(account)
            view.onAuthSuccessful()
        }
    }

    private fun addMailInUniqueId(acct: GoogleSignInAccount?) {
        val userEmail = acct?.email
        if (userEmail != null) {
            DeviceIdChangeManager.setEmailOrUniqueId(userEmail)
        }
    }

    override fun checkCurrentUser(context: Context) {
        /*if (!App.getInstance().sharedManager.firstLoginState) {
            if (!App.getInstance().sharedManager.uniqueID.isEmpty()) {
                view.onAuthSuccessful()
            }
        }*/

        val account = GoogleSignIn.getLastSignedInAccount(context)
        if (account != null) {
            addMailInUniqueId(account)
            view.onAuthSuccessful()
        }else {
            val accessToken = AccessToken.getCurrentAccessToken()
            if (accessToken != null && !accessToken.isExpired) run {
                view.onAuthSuccessful()
            }
        }
    }
}