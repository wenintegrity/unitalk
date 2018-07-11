package com.unitalk.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.unitalk.BuildConfig;
import com.unitalk.R;
import com.unitalk.core.App;
import com.unitalk.enums.ScreenType;
import com.unitalk.ui.BaseActivity;
import com.unitalk.ui.MainActivity;
import com.unitalk.utils.ViewUpdaterKt;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeActivity extends BaseActivity implements HomeView {
    private AHBottomNavigation mBottomNavigation;
    public static final String HOME_SCREEN_LABEL = "home";
    public static final String EXTRA_SCREEN_TYPE = "SCREEN_TYPE";
    public static final String EXTRA_SCREEN_MOVING_FROM = "SCREEN_MOVING_FROM";

    @BindView(R.id.tvRecordingHint)
    TextView tvRecordingHint;
    @BindView(R.id.tvVoiceSamplingLabel)
    TextView tvVoiceSamplingLabel;

    @Override
    protected int provideLayout() {
        return R.layout.activity_home;
    }

    @Override
    protected View getRootView() {
        return findViewById(R.id.home);
    }

    @Override
    protected void init() {
        super.init();
        App.getInstance().getSharedManager().setCurrentVideoUrl(BuildConfig.VIDEO_1);
        initBottomNavigationBar();
        ViewUpdaterKt.goneViews(tvRecordingHint);
        ViewUpdaterKt.showViews(tvVoiceSamplingLabel);
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

    private boolean onBottomNavigationItemClick(final int position) {
        moveToNextScreen(ScreenType.values()[position]);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getInstance().getSharedManager().setFirstLoginState(false);
    }

    @OnClick({R.id.ivVoiceRecording, R.id.llCheckYourDailyHarmony, R.id.ivSettings})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivVoiceRecording:
                moveToNextScreen(ScreenType.RECORDING);
                break;
            case R.id.llCheckYourDailyHarmony:
                moveToNextScreen(ScreenType.DAILY);
                break;
            case R.id.ivSettings:
                moveToNextScreen(ScreenType.SETTINGS);
                break;
        }
    }

    private void moveToNextScreen(@NonNull final ScreenType screenType) {
        // FIXME: 4/6/18 replace with base activity method
        final Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(EXTRA_SCREEN_MOVING_FROM, HOME_SCREEN_LABEL);
        intent.putExtra(EXTRA_SCREEN_TYPE, screenType.name());
        startActivity(intent);
        finish();
    }
}
