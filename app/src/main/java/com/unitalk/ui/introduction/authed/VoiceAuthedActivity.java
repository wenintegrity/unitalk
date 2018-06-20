package com.unitalk.ui.introduction.authed;

import android.os.Handler;
import android.view.View;

import com.unitalk.R;
import com.unitalk.ui.BaseActivity;
import com.unitalk.ui.home.HomeActivity;


public class VoiceAuthedActivity extends BaseActivity {
    private static final int NEXT_ACTIVITY_DELAY = 2000;
    private Runnable mNextScreenRunnable = this::onNextScreen;
    private Handler mHandler = new Handler();

    @Override
    protected int provideLayout() {
        return R.layout.activity_voice_authed;
    }

    @Override
    protected View getRootView() {
        return findViewById(R.id.home);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // FIXME: 4/6/18 remove ?
        init();
    }

    @Override
    protected void init() {
        super.init();
        mHandler.postDelayed(mNextScreenRunnable, NEXT_ACTIVITY_DELAY);
    }

    public void onNextScreen() {
        moveToScreenWithoutBack(HomeActivity.class);
    }

    @Override
    protected void onStop() {
        mHandler.removeCallbacks(mNextScreenRunnable);
        super.onStop();
    }
}