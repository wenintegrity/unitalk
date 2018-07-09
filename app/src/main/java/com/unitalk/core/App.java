package com.unitalk.core;

import android.app.Application;
import android.content.Context;
import android.webkit.WebView;

import com.facebook.stetho.Stetho;
import com.unitalk.utils.LocaleHelper;
import com.unitalk.utils.ResourcesManager;
import com.unitalk.utils.StringArrayDataLocalStorage;
import com.unitalk.utils.UniqueIDManagerKt;

public class App extends Application {
    private static App context;
    private SharedManager sharedManager;
    private StringArrayDataLocalStorage stringArrayDataLocalStorage;

    @Override
    public void onCreate() {
        super.onCreate();
        WebView.setWebContentsDebuggingEnabled(true);
        context = (App) getApplicationContext();
        Stetho.initializeWithDefaults(this);
        sharedManager = new SharedManager(this);
        stringArrayDataLocalStorage = StringArrayDataLocalStorage.getInstance();
        ResourcesManager.with(this);
        createDeviceID();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base, "en"));
    }

    public static App getInstance() {
        return context;
    }

    public SharedManager getSharedManager() {
        return sharedManager;
    }

    public StringArrayDataLocalStorage getStringArrayDataLocalStorage() {
        return stringArrayDataLocalStorage;
    }

    private void createDeviceID() {
        String deviceID = sharedManager.getUniqueID();
        if (deviceID == null) {
            deviceID = UniqueIDManagerKt.getUniqueID();
            sharedManager.setUniqueID(deviceID);
        }
    }
}
