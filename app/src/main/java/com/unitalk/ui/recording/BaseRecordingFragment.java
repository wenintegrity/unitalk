package com.unitalk.ui.recording;

import android.Manifest;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.unitalk.R;
import com.unitalk.core.App;
import com.unitalk.ui.BaseFragment;
import com.unitalk.ui.callback.OnNotificationSettingsCallback;
import com.unitalk.ui.callback.OnShowMessageCallback;
import com.unitalk.ui.lang.settings_model.LangMessageEvent;
import com.unitalk.utils.LocaleHelper;
import com.unitalk.utils.NotificationFiltersManager;
import com.unitalk.utils.ViewUpdaterKt;
import com.unitalk.utils.customview.CircleProgressBar;
import com.unitalk.utils.customview.NotificationSettingsDialog;
import com.unitalk.utils.customview.VisualizerView;

import butterknife.BindView;
import butterknife.OnClick;

import static android.content.Context.AUDIO_SERVICE;
import static android.content.Context.NOTIFICATION_SERVICE;

public abstract class BaseRecordingFragment<T extends BaseRecordingView> extends BaseFragment implements BaseRecordingView, OnShowMessageCallback, OnNotificationSettingsCallback {
    private static final String TEXT_EMPTY_FIELD = "";
    private static final int REQUEST_PERMISSIONS_CODE = 1;
    private static final int CONCENTRATING_COMPLETE_TIME = 4;
    protected static final int STEPS_NUMBER = 3;
    private NotificationFiltersManager notificationFiltersManager;
    private AudioManager audioManager;
    private boolean isFirstSample;
    private int defSoundValue;
    private int ringerMode;

    protected BaseRecordingPresenter<T> presenter;
    @BindView(R.id.tvTitle)
    protected TextView tvTitle;
    @BindView(R.id.clSamplingCircle)
    protected ConstraintLayout clSamplingCircle;
    @BindView(R.id.tvHintAndError)
    protected TextView tvHintAndError;
    @BindView(R.id.ivVoiceRecording)
    protected ImageView ivVoiceRecording;
    @BindView(R.id.tvRecordingHint)
    protected TextView tvRecordingHint;
    @BindView(R.id.tvBipCounter)
    protected TextView tvBipCounter;
    private VisualizerView visualizerView;
    @BindView(R.id.pbCircle)
    protected CircleProgressBar pbCircle;
    @BindView(R.id.pbProcessing)
    protected ProgressBar pbProcessing;

    protected abstract void init();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        setDefaultViews();
        presenter.onStart();
        saveCurrentRingerModeParameters();
        if (App.getInstance().getSharedManager().getDialogClosed()) {
            setSilentRingerMode();
            tryRecording();
        }
        App.getInstance().getSharedManager().setDialogClosed(false);
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.onStop();
        setSavedRingerMode();
    }

    @OnClick(R.id.ivVoiceRecording)
    public void record() {
        if (isFirstSample) {
            saveCurrentRingerModeParameters();
        }
        setSilentRingerMode();
        if (notificationFiltersManager.isNotificationPolicyAccessGranted()) {
            tryRecording();
        } else {
            showNotificationSettingsDialog();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSIONS_CODE:
                if (grantResults.length > 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    presenter.record();
                    isFirstSample = false;
                } else {
                    Toast.makeText(getContext(), R.string.no_permissions, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onSampleRecorded(final int step) {
        reinitScreen();
    }

    @Override
    public void onVoiceRecorded() {
        setSavedRingerMode();
        ivVoiceRecording.setClickable(false);
        Toast.makeText(getContext(), getString(R.string.voice_recorded), Toast.LENGTH_LONG).show();
        send();
    }

    @Override
    public void onProcessingFinished() {
        if (pbProcessing != null) {
            pbProcessing.setVisibility(View.GONE);
        }
    }

    @Override
    public void onVisualizeAmplitude(final int amplitude) {
        visualizerView.receive(amplitude);
    }

    @Override
    public void onShowRecordingErrorHint() {
        reinitScreen();
        showErrorHint();
    }

    @Override
    public void onConcentrating(final long count) {
        if (count == CONCENTRATING_COMPLETE_TIME) {
            onConcentratingFinished();
        } else {
            setConcentratingStep(count);
        }
    }

    @Override
    public void onUpdateProgress(final int ms) {
        pbCircle.setProgress(ms);
    }

    @Override
    public void onRestartRecording() {
        Toast.makeText(getContext(), R.string.repeat_sampling, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(@StringRes final int messageID) {
        Toast.makeText(getContext(), messageID, Toast.LENGTH_SHORT).show();
    }

    @NonNull
    @Override
    public NotificationManager createNotificationManager() {
        return (NotificationManager) getActivity().getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    public void showNotificationSettingsDialog() {
        final DialogFragment notificationSettingsDialog = new NotificationSettingsDialog();
        notificationSettingsDialog.show(getActivity().getSupportFragmentManager(), getString(R.string.notification_settings_request));
    }

    protected void onConcentratingFinished() {
        onRecordStarted();
        tvRecordingHint.setText(TEXT_EMPTY_FIELD);
    }

    protected void setConcentratingStep(final long count) {
        ViewUpdaterKt.goneViews(ivVoiceRecording, clSamplingCircle);
        tvRecordingHint.setText(R.string.concentrate);
        ViewUpdaterKt.showViews(tvBipCounter);
        tvBipCounter.setText(String.valueOf(count));
    }

    protected void send() {
        ivVoiceRecording.setClickable(false);
        ViewUpdaterKt.showViews(pbProcessing);
        presenter.sendData();
    }

    private void saveCurrentRingerModeParameters() {
        defSoundValue = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        ringerMode = audioManager.getRingerMode();
    }

    private void setSilentRingerMode() {
        if (notificationFiltersManager.isNotificationPolicyAccessGranted()) {
            audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);
        }
    }


    private void setSavedRingerMode() {
        if (notificationFiltersManager.isNotificationPolicyAccessGranted()) {
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, defSoundValue, 0);
            audioManager.setRingerMode(ringerMode);
        }
    }

    private void tryRecording() {
        ViewUpdaterKt.goneViews(tvHintAndError);
        getPermissions();
    }

    private void showErrorHint() {
        tvHintAndError.setText(R.string.no_hear_you);
        ViewUpdaterKt.showViews(tvHintAndError);
    }

    private void getPermissions() {
        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.ACCESS_FINE_LOCATION},
                REQUEST_PERMISSIONS_CODE);
    }

    private void setDefaultViews() {
        init();
        isFirstSample = true;
        notificationFiltersManager = new NotificationFiltersManager(this);
        audioManager = (AudioManager) getActivity().getSystemService(AUDIO_SERVICE);
        ivVoiceRecording.setClickable(true);
        initVisualizer();
        ViewUpdaterKt.showViews(ivVoiceRecording);
        ViewUpdaterKt.goneViews(tvBipCounter, clSamplingCircle);
        pbCircle.setProgress(0);
        visualizerView.receive(0);
    }

    private void initVisualizer() {
        visualizerView = getView().findViewById(R.id.visualizer);
        final ViewTreeObserver observer = visualizerView.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                visualizerView.setBaseY(visualizerView.getHeight() / 2);
                visualizerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    private void reinitScreen() {
        ViewUpdaterKt.goneViews(clSamplingCircle);
        ViewUpdaterKt.showViews(ivVoiceRecording);
    }

    private void onRecordStarted() {
        ViewUpdaterKt.goneViews(tvBipCounter, ivVoiceRecording);
        ViewUpdaterKt.showViews(clSamplingCircle);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateViews();
    }

    private void updateViews() {
        Context context = LocaleHelper.onAttach(getContext());
        Resources resources = context.getResources();
        tvTitle.setText(resources.getString(R.string.solving_trouble_conflict));
        tvHintAndError.setText(resources.getString(R.string.no_hear_you));
        tvRecordingHint.setText(resources.getString(R.string.concentrate));
    }
}
