package com.unitalk.utils;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.unitalk.core.App;

public class GoogleAuthManager {

    private static GoogleAuthManager managerInstance;
    private GoogleSignInClient googleApiClient;
    private GoogleSignInOptions gso;

    private GoogleAuthManager() {
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
    }

    public static GoogleAuthManager getManagerInstance() {
        if (managerInstance != null) {
            return managerInstance;
        }else {
            managerInstance = new GoogleAuthManager();
            return managerInstance;
        }
    }

    public GoogleSignInClient getGoogleApiClient(Activity activity) {

        googleApiClient = GoogleSignIn.getClient(activity, gso);
        return googleApiClient;
    }

    public void signOut() {
        googleApiClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    App.getInstance().getSharedManager().setUniqueID("");
                }
            }
        });
    }
}
