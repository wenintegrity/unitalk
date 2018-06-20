package com.unitalk.ui.recording.sampling;

import android.content.Context;
import android.widget.RelativeLayout;

import com.unitalk.R;
import com.unitalk.core.App;
import com.unitalk.enums.CardsState;
import com.unitalk.ui.callback.OnScreenNavigationCallback;
import com.unitalk.ui.callback.OnShowMessageCallback;
import com.unitalk.ui.recording.BaseRecordingFragment;
import com.unitalk.ui.result.ResultFragment;
import com.unitalk.ui.videos.VideosFragment;
import com.unitalk.utils.ViewUpdaterKt;
import com.unitalk.utils.customview.StepsView;

import butterknife.BindView;

public class VoiceSamplingFragment extends BaseRecordingFragment implements VoiceSamplingView, OnShowMessageCallback {
    private static final int[] BACKGROUNDS = {
            R.drawable.ic_background_first,
            R.drawable.ic_background_second,
            R.drawable.ic_background_third,
            R.drawable.ic_background_third };
    @BindView(R.id.rlVoiceRecording)
    protected RelativeLayout rlVoiceRecording;
    @BindView(R.id.stepsView)
    StepsView stepsView;
    private OnScreenNavigationCallback onScreenNavigationCallback;

    public static VoiceSamplingFragment newInstance() {
        return new VoiceSamplingFragment();
    }

    @Override
    protected int provideLayout() {
        return R.layout.fragment_record_voice;
    }

    @Override
    protected void init() {
        if (App.getInstance().getSharedManager().getCardsState().equals(CardsState.NONE.name())) {
            tvTitle.setText(R.string.label_voice_sampling);
        }
        for (int i = 0; i < STEPS_NUMBER; i++) {
            stepsView.setStepState(i, false);
        }
        presenter = new VoiceSamplingPresenterImpl(this, this);
        presenter.sendCardsClickedData();
        showHint();
        rlVoiceRecording.setBackground(getContext().getDrawable(BACKGROUNDS[0]));
    }

    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);
        if (context instanceof OnScreenNavigationCallback) {
            onScreenNavigationCallback = (OnScreenNavigationCallback) context;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        rlVoiceRecording.setBackground(getContext().getDrawable(BACKGROUNDS[0]));
        for (int i = 0; i < STEPS_NUMBER; i++) {
            stepsView.setStepState(i, false);
        }
    }

    @Override
    public void onSampleRecorded(final int step) {
        super.onSampleRecorded(step);
        rlVoiceRecording.setBackground(getContext().getDrawable(BACKGROUNDS[step]));
        showHint();
        stepsView.setStepState(step, true);
    }

    @Override
    public void onMoveToNextScreen() {
        if (onScreenNavigationCallback != null) {
            if (App.getInstance().getSharedManager().getCardsState().equals(CardsState.NONE.name())) {
                onScreenNavigationCallback.startFragmentTransactionFromRightToLeft(ResultFragment.newInstance());
            } else {
                App.getInstance().getSharedManager().setIsVideoScreen(false);
                onScreenNavigationCallback.startFragmentTransactionFromRightToLeft(VideosFragment.newInstance());
            }
        }
    }

    @Override
    public void onRestartRecording() {
        super.onRestartRecording();
        if (onScreenNavigationCallback != null) {
            onScreenNavigationCallback.startFragmentTransactionFromRightToLeft(VoiceSamplingFragment.newInstance());
        }
    }

    private void showHint() {
        tvHintAndError.setText(R.string.think_and_concentrate);
        ViewUpdaterKt.showViews(tvHintAndError);
    }
}
