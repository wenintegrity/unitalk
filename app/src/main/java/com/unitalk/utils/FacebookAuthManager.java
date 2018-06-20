package com.unitalk.utils;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.unitalk.R;
import com.unitalk.core.DeviceIdChangeManager;
import com.unitalk.ui.callback.OnFacebookAuthCallback;
import com.unitalk.ui.callback.OnShowMessageCallback;

import org.json.JSONObject;

public class FacebookAuthManager {
    private CallbackManager callbackManager;
    private OnFacebookAuthCallback onFacebookAuthCallback;
    private OnShowMessageCallback onShowMessageCallback;

    public FacebookAuthManager(@NonNull final OnFacebookAuthCallback onFacebookAuthCallback, @NonNull final OnShowMessageCallback onShowMessageCallback) {
        this.onFacebookAuthCallback = onFacebookAuthCallback;
        this.onShowMessageCallback = onShowMessageCallback;
        createFacebookAuth();
    }

    private void createFacebookAuth() {
        createCallback();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(final LoginResult loginResult) {
                        onFacebookLoginSuccess(loginResult);
                        onShowMessageCallback.showMessage(R.string.welcome);
                    }

                    @Override
                    public void onCancel() {
                        onShowMessageCallback.showMessage(R.string.fb_auth_error);
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        onShowMessageCallback.showMessage(R.string.fb_remote_error);
                    }
                });
    }

    private void onFacebookLoginSuccess(@NonNull final LoginResult loginResult) {
        onFacebookAuthCallback.onFacebookAuthResult();
        createUserDataRequest(loginResult);
    }

    private void createCallback() {
        callbackManager = CallbackManager.Factory.create();
    }

    private void createUserDataRequest(@NonNull final LoginResult loginResult) {
        Log.d("TOKEN", String.valueOf(loginResult.getAccessToken().getToken()));
        final GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), this::saveUserEmail);
        final Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,gender, birthday");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void saveUserEmail(final JSONObject json, final GraphResponse response) {
        if (response.getError() != null) {
            Log.v("FaceBook Response :", "ERROR");
        } else {
            Log.v("FaceBook Response :", "Success");
            final String jsonResult = String.valueOf(json);
            Log.v("FaceBook Response :", "JSON Result" + jsonResult);
            final String fbUserEmail = json.optString("email");
            addEmailToPref(fbUserEmail);
        }
        Log.v("FaceBook Response :", response.toString());
    }

    private void addEmailToPref(final String fbUserEmail) {
        DeviceIdChangeManager.Companion.setEmailOrUniqueId(fbUserEmail);
    }

    public void onFacebookAuthResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
