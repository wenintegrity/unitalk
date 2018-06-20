package com.unitalk.ui.recording.auth;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.unitalk.R;
import com.unitalk.ui.introduction.authed.VoiceAuthedActivity;
import com.unitalk.ui.recording.BaseRecordingFragment;
import com.unitalk.utils.customview.StepsView;

import butterknife.BindView;

public class VoiceAuthFragment extends BaseRecordingFragment {
    @BindView(R.id.stepsView)
    StepsView stepsView;

    public static VoiceAuthFragment newInstance() {
        return new VoiceAuthFragment();
    }

    @Override
    protected int provideLayout() {
        return R.layout.fragment_voice_auth;
    }

    @Override
    protected void init() {
        presenter = new VoiceAuthPresenterImpl(this, this);
        for (int i = 0; i < STEPS_NUMBER; i++) {
            stepsView.setStepState(i, false);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvTitle.setText(R.string.label_voice_sampling);
    }

    public void onSampleRecorded(final int step) {
        super.onSampleRecorded(step);
        stepsView.setStepState(step, true);
    }

    @Override
    public void onMoveToNextScreen() {
        final Intent intent = new Intent(getContext(), VoiceAuthedActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void onRestartRecording() {
        super.onRestartRecording();
        restartScreen();
    }

    private void restartScreen() {
        final Intent intent = getActivity().getIntent();
        startActivity(intent);
        getActivity().finish();
    }
}
