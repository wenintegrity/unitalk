package com.unitalk.ui.recording.auth;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.View;

import com.unitalk.R;
import com.unitalk.ui.BaseActivity;

public class VoiceAuthActivity extends BaseActivity {
    @Override
    protected int provideLayout() {
        return R.layout.activity_recording;
    }

    @Override
    protected View getRootView() {
        return findViewById(R.id.rlVoiceRecording);
    }

    @Override
    protected void init() {
        super.init();
        final Fragment voiceAuthFragment = VoiceAuthFragment.newInstance();
        startFragmentTransaction(voiceAuthFragment);
    }

    public void startFragmentTransaction(@NonNull final Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .commit();
    }
}
