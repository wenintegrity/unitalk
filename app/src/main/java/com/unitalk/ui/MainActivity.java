package com.unitalk.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.unitalk.R;
import com.unitalk.core.App;
import com.unitalk.enums.CardsState;
import com.unitalk.enums.ScreenType;
import com.unitalk.ui.callback.OnMainActivityCallback;
import com.unitalk.ui.harmony.HarmonyCheckingCardFragmentImpl;
import com.unitalk.ui.harmony.HarmonyOneCardFragment;
import com.unitalk.ui.home.HomeActivity;
import com.unitalk.ui.recording.sampling.VoiceSamplingFragment;
import com.unitalk.ui.settings.SettingsFragment;
import com.unitalk.ui.videos.VideosFragment;

import java.util.List;

public class MainActivity extends BaseActivity implements OnMainActivityCallback {
    private AHBottomNavigation mBottomNavigation;
    private String screenMovingFrom;

    @Override
    protected int provideLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected View getRootView() {
        return findViewById(R.id.rlVoiceRecording);
    }

    @Override
    protected void init() {
        super.init();
        initBottomNavigationBar();
        App.getInstance().getSharedManager().setCardsState(CardsState.FOUR.name());
        initScreen();
    }

    private void initBottomNavigationBar() {
        mBottomNavigation = findViewById(R.id.bottom_navigation);
        mBottomNavigation.setAccentColor(ContextCompat.getColor(this, R.color.bottomNavigationAccentColor));

        // Create items
        final AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.label_voice_sampling, R.drawable.ic_record_voice_selector, R.color.colorGray);
        final AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.solving_trouble_conflict, R.drawable.ic_daily_mood_selector, R.color.colorGray);
        final AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.videos, R.drawable.ic_videos_selector, R.color.colorGray);
        final AHBottomNavigationItem item4 = new AHBottomNavigationItem(R.string.settings, R.drawable.ic_settings_selector, R.color.colorGray);

        // Add items
        mBottomNavigation.addItem(item1);
        mBottomNavigation.addItem(item2);
        mBottomNavigation.addItem(item3);
        mBottomNavigation.addItem(item4);

        mBottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        mBottomNavigation.setOnTabSelectedListener((position, wasSelected) -> onBottomNavigationItemClick(position));
    }

    private void initScreen() {
        final Bundle bundle = getIntent().getExtras();
        ScreenType screenType = ScreenType.DAILY;
        if (bundle != null) {
            screenMovingFrom = bundle.getString(HomeActivity.EXTRA_SCREEN_MOVING_FROM);
            final String screenTypeName = bundle.getString(HomeActivity.EXTRA_SCREEN_TYPE);
            if (screenTypeName != null) {
                screenType = ScreenType.valueOf(bundle.getString(HomeActivity.EXTRA_SCREEN_TYPE));
            }
            selectCurrentItem(screenType.getValue());
        }
    }

    private void selectCurrentItem(final int position) {
        mBottomNavigation.setCurrentItem(position);
        onBottomNavigationItemClick(position);
    }

    private boolean onBottomNavigationItemClick(final int position) {
        if (mBottomNavigation.getCurrentItem() == position && !screenMovingFrom.equals(HomeActivity.HOME_SCREEN_LABEL)) {
            return true;
        }
        Fragment selectedFragment = null;
        switch (position) {
            case 0:
                selectedFragment = VoiceSamplingFragment.newInstance();
                App.getInstance().getSharedManager().setCardsState(CardsState.NONE.name());
                break;
            case 1:
                selectedFragment = VoiceSamplingFragment.newInstance();
                App.getInstance().getSharedManager().setIsIntroVideo(true);
                App.getInstance().getSharedManager().setCardsState(CardsState.FOUR.name());
                break;
            case 2:
                App.getInstance().getSharedManager().setIsVideoScreen(true);
                selectedFragment = VideosFragment.newInstance();
                break;
            case 3:
                selectedFragment = SettingsFragment.newInstance();
                // FIXME: 5/21/18 why ?
                showBottomBar();
                break;
        }
        startFragmentTransaction(selectedFragment);

        return true;
    }

    @Override
    public void startVideoFragment() {
        startFragmentTransactionFromRightToLeft(VideosFragment.newInstance());
    }

    @Override
    public void startHarmonyOneCardFragment(@NonNull final String moodCardName) {
        startFragmentTransactionFromRightToLeft(HarmonyOneCardFragment.newInstance(moodCardName));
    }

    @Override
    public void startHarmonyCheckingCardFragment(@NonNull final List<String> moodCardList) {
        startFragmentTransactionFromRightToLeft(HarmonyCheckingCardFragmentImpl.newInstance(moodCardList));
    }
}
