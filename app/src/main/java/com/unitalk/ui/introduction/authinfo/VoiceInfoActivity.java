package com.unitalk.ui.introduction.authinfo;

import android.view.View;
import android.widget.TextView;

import com.unitalk.R;
import com.unitalk.ui.BaseActivity;
import com.unitalk.ui.recording.auth.VoiceAuthActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class VoiceInfoActivity extends BaseActivity {
    @BindView(R.id.btnReadyToStart)
    TextView tvReadyToStart;

    @Override
    protected int provideLayout() {
        return R.layout.activity_voice_info;
    }

    @Override
    protected View getRootView() {
        return findViewById(R.id.home);
    }

    @OnClick(R.id.btnReadyToStart)
    public void moveToNextScreen() {
        moveToScreenWithoutBack(VoiceAuthActivity.class);
    }
}