package com.unitalk.ui.splash;

import android.view.View;

import com.unitalk.R;
import com.unitalk.ui.BaseActivity;
import com.unitalk.ui.auth.main.AuthMainActivity;
import com.unitalk.ui.introduction.greeting.GreetingActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.disposables.Disposable;

public class SplashActivity extends BaseActivity {
    private static final int SPLASH_DELAY = 1;
    private Disposable splashTimerDisposable;

    @Override
    protected int provideLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected View getRootView() {
        return findViewById(R.id.home);
    }

    @Override
    protected void onStart() {
        super.onStart();
        splashTimerDisposable = Completable.timer(SPLASH_DELAY, TimeUnit.SECONDS).doOnComplete(this::toNextScreen).subscribe();
    }

    @Override
    protected void onStop() {
        super.onStop();
        splashTimerDisposable.dispose();
    }

    public void toNextScreen() {
        if (getFirstLogin()) {
            moveToScreenWithoutBack(GreetingActivity.class);
        } else {
            moveToScreenWithoutBack(AuthMainActivity.class);
        }
    }
}
