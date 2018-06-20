package com.unitalk.ui.recording;

import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.util.Log;

import com.unitalk.R;
import com.unitalk.core.App;
import com.unitalk.network.model.CurrentLocationData;
import com.unitalk.network.model.UserData;
import com.unitalk.network.model.VoiceData;
import com.unitalk.ui.BaseQueriesPresenterImpl;
import com.unitalk.ui.callback.OnShowMessageCallback;
import com.unitalk.utils.AmplitudeRecordManager;
import com.unitalk.utils.CurrentLocationManager;
import com.unitalk.utils.CurrentTimeManager;
import com.unitalk.utils.VoiceRecordManager;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public abstract class BaseRecordingPresenterImpl<T extends BaseRecordingView> extends BaseQueriesPresenterImpl implements BaseRecordingPresenter<T> {
    // FIXME: 4/20/18 move from this place
    private List<Float> results[];
    private static final int BIP_ID = R.raw.mix3;
    private static final String TAG = "AMPLITUDE";
    private static final int LOCATION_SCALE = 4;
    private static final int TIME_FOR_LISTENING = 2000;
    private static final int SAMPLE_RATE = 8018;
    private static final int DATA_SIZE = 2048;
    private static final int MAX_TIME_TO_REC = 800;
    private static final int AMPLITUDE_RECORDING_INTERVAL = 50;
    private static final int SAMPLES_COUNT = 3;
    private static final int CONCENTRATING_INTERVAL = 1000;
    private static final int TIMER_UPDATE_INTERVAL = 1;

    private final VoiceData voiceData = new VoiceData();
    private MediaPlayer bipPlayer;
    private float voiceLevel;
    private Timer timer;
    private AmplitudeRecordManager amplitudeRecordManager;
    private VoiceRecordManager voiceRecordManager;

    protected final UserData userData = new UserData();
    protected T view;
    protected OnShowMessageCallback onShowMessageCallback;
    protected int sampleNumber;

    protected abstract void onFinishSampling();

    public BaseRecordingPresenterImpl(@NonNull final T view, @NonNull final OnShowMessageCallback onShowMessageCallback) {
        this.view = view;
        this.onShowMessageCallback = onShowMessageCallback;
        results = new ArrayList[SAMPLES_COUNT];
        sampleNumber = 0;
        voiceLevel = App.getInstance().getSharedManager().getVoiceLevel();
        createBipPlayer();
    }

    @Override
    public void onStop() {
        stopBipPlayer();
        stopAmplitudeRecording();
        stopVoiceRecording();
        super.onStop();
    }


    @Override
    public void record() {
        init();
        final Disposable disposable = Completable.create(emitter -> {
            updateView();
            playBip(emitter);
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    // FIXME: 4/20/18 it's 'too short' block of code :)
                    {
                        if (voiceRecordManager.getSampleData() == null) {
                            view.onShowRecordingErrorHint();
                        } else {
                            results[sampleNumber] = new ArrayList<>();
                            for (int i = 0; i < DATA_SIZE; i++) {
                                results[sampleNumber].add(voiceRecordManager.getSampleData()[i]);
                            }
                            onFinishSampling();
                        }

                        if (sampleNumber == SAMPLES_COUNT) {
                            onSampleRecorded();
                        } else {
                            createBipPlayer();
                        }
                    }
                });
        compositeDisposable.add(disposable);
    }

    protected void onSampleRecorded() {
        voiceData.setAllData(results);
        userData.setVoiceData(voiceData);
        view.onVoiceRecorded();
    }

    @Override
    public void sendData() {
        voiceRecordManager.stopVoiceRecording();
        createUserData();
    }

    private void updateView() {
        final Disposable disposable = Observable.intervalRange(0, SAMPLES_COUNT + 1, 0, CONCENTRATING_INTERVAL, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> view.onConcentrating(s + 1));
        compositeDisposable.add(disposable);
    }

    private void startProgressUpdate() {
        final Disposable disposable = Observable.intervalRange(0, TIME_FOR_LISTENING, 0, TIMER_UPDATE_INTERVAL, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(aLong -> view.onUpdateProgress(aLong.intValue()))
                .subscribe();
        compositeDisposable.add(disposable);
    }

    private void createUserData() {
        createEmail();
        createCurrentTime();
        createCurrentLocation();
    }

    private void createEmail() {
        // FIXME: 4/16/18 add email
        final String deviceID = App.getInstance().getSharedManager().getUniqueID();
        userData.setEmail(deviceID);
    }

    private void createCurrentTime() {
        final CurrentTimeManager currentTimeManager = new CurrentTimeManager();
        final String currentTime = currentTimeManager.getCurrentTime(new Date());
        userData.setTime(currentTime);
    }

    private void createCurrentLocation() {
        final CurrentLocationData currentLocationData = new CurrentLocationManager(App.getInstance()).getCurrentLocationData();
        double scaledLatitude = setFormat(currentLocationData.getLatitude(), LOCATION_SCALE);
        double scaledLongitude = setFormat(currentLocationData.getLongitude(), LOCATION_SCALE);
        currentLocationData.setLatitude(scaledLatitude);
        currentLocationData.setLongitude(scaledLongitude);
        userData.setLocation(currentLocationData);
    }

    private double setFormat(final double value, final int scale) {
        return new BigDecimal(value).setScale(scale, RoundingMode.HALF_EVEN).doubleValue();
    }

    protected void init() {
        voiceRecordManager = new VoiceRecordManager(SAMPLES_COUNT, DATA_SIZE, SAMPLE_RATE);
        amplitudeRecordManager = new AmplitudeRecordManager();
    }

    private void createBipPlayer() {
        bipPlayer = MediaPlayer.create(App.getInstance(), BIP_ID);
    }

    private void playBip(@NonNull final CompletableEmitter emitter) {
        bipPlayer.setOnCompletionListener(mp -> {
            resetBipPlayer();
            startProgressUpdate();
            startTimerForAmplitudeListening(emitter);
            amplitudeRecordManager.createAmplitudeRecorder();
            amplitudeRecordManager.startAmplitudeRecording();
            listenAmplitude();
        });
        bipPlayer.start();
    }

    private void startTimerForAmplitudeListening(@NonNull final CompletableEmitter emitter) {
        final Disposable disposable = Completable.timer(TIME_FOR_LISTENING, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .subscribe(() -> {
                    stopAmplitudeRecording();
                    emitter.onComplete();
                });
        compositeDisposable.add(disposable);
    }

    private void listenAmplitude() {
        timer = new Timer();
        timer.schedule(createAmplitudeListenerTimerTask(), 0, AMPLITUDE_RECORDING_INTERVAL);
    }

    @NonNull
    private TimerTask createAmplitudeListenerTimerTask() {
        return new TimerTask() {
            @Override
            public void run() {
                final int amplitude = amplitudeRecordManager.getVoiceAmplitude();
                view.onVisualizeAmplitude(amplitude);
                startVoiceRecordingByAmplitude(amplitude);
            }
        };
    }

    private void startVoiceRecordingByAmplitude(final int amplitude) {
        final double voiceLevel = getVoiceLevelInDecibels(amplitude);
        Log.i(TAG, String.valueOf(voiceLevel));
        if (voiceLevel > this.voiceLevel) {
            stopAmplitudeRecording();
            transformVoice();
        }
    }

    private void transformVoice() {
        final Disposable disposable = Completable.create(emitter -> {
            if (voiceRecordManager != null) {
                voiceRecordManager.startVoiceRecording();
            }
        })
                .subscribeOn(Schedulers.io())
                .subscribe();
        compositeDisposable.add(disposable);
        startTimerForVoiceRecording(MAX_TIME_TO_REC);
    }

    private void stopBipPlayer() {
        if (bipPlayer != null && bipPlayer.isPlaying()) {
            resetBipPlayer();
        }
    }

    private void resetBipPlayer() {
        bipPlayer.stop();
        bipPlayer.reset();
    }

    private void stopAmplitudeRecording() {
        if (amplitudeRecordManager != null) {
            amplitudeRecordManager.stopAmplitudeRecorder();
            amplitudeRecordManager.releaseAmplitudeRecorder();
        }
        if (timer != null) {
            timer.cancel();
        }
    }

    private void stopVoiceRecording() {
        if (voiceRecordManager != null) {
            voiceRecordManager.stopVoiceRecording();
            voiceRecordManager.releaseVoiceRecorder();
        }
    }

    private double getVoiceLevelInDecibels(final int amplitude) {
        return 20 * Math.log10(amplitude / 32768.0f);
    }


    private void startTimerForVoiceRecording(final long recordingTime) {
        final Disposable disposable = Completable.timer(recordingTime, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    voiceRecordManager.clearSampleData();
                    voiceRecordManager.stopVoiceRecording();
                });
        compositeDisposable.add(disposable);
    }
}
