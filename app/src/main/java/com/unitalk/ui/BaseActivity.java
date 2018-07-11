package com.unitalk.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.unitalk.R;
import com.unitalk.core.App;
import com.unitalk.ui.callback.OnScreenNavigationCallback;
import com.unitalk.ui.callback.OnShowMessageCallback;
import com.unitalk.utils.LocaleHelper;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity implements OnScreenNavigationCallback, OnShowMessageCallback {
    private static long backPressedMillis;

    protected abstract int provideLayout();
    protected abstract View getRootView();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }

    @Override
    public void onBackPressed() {
        if (backPressedMillis + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
        } else {
            final View view = getRootView();
            final Snackbar snackbar = Snackbar.make(view, LocaleHelper.getResources(this).getString(R.string.toast_exit_helper), Snackbar.LENGTH_LONG);
            snackbar.show();
        }
        backPressedMillis = System.currentTimeMillis();
    }

    protected void init() {
        setContentView(provideLayout());
        ButterKnife.bind(this);
    }

    protected void showBottomBar() {
        getWindow().clearFlags(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    protected boolean getFirstLogin() {
        return App.getInstance().getSharedManager().getFirstLoginState();
    }

    @Override
    public void moveToScreenWithoutBack(@NonNull final Class screenClassToMove) {
        moveToScreen(screenClassToMove);
        finish();
    }

    @Override
    public void moveToScreen(@NonNull final Class screenClassToMove) {
        final Intent intent = new Intent(this, screenClassToMove);
        startActivity(intent);
    }

    @Override
    public void startFragmentTransaction(@NonNull final Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .commit();
    }

    @Override
    public void startFragmentTransactionFromRightToLeft(@NonNull final Fragment fragment) {
        startFragmentCustomTransaction(fragment, R.animator.enter_from_right, R.animator.exit_to_left,
                R.animator.enter_from_left, R.animator.exit_to_right);
    }

    @Override
    public void startFragmentCustomTransaction(@NonNull final Fragment fragment, final int animEnter,
                                               final int animExit, final int enterFromStack,
                                               final int exitFromStack) {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(
                        animEnter, animExit,
                        enterFromStack, exitFromStack)
                .replace(R.id.frame_layout, fragment)
                .commit();
    }

    @Override
    public void showMessage(@StringRes final int messageID) {
        Toast.makeText(LocaleHelper.onAttach(this), messageID, Toast.LENGTH_SHORT).show();
    }
}
