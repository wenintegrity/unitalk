package com.unitalk.ui.recording.auth;

import android.support.annotation.NonNull;
import android.util.Log;

import com.unitalk.R;
import com.unitalk.network.NetworkManager;
import com.unitalk.ui.callback.OnShowMessageCallback;
import com.unitalk.ui.recording.BaseRecordingPresenterImpl;
import com.unitalk.ui.recording.BaseRecordingView;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VoiceAuthPresenterImpl extends BaseRecordingPresenterImpl {
    private static final int INITIAL_STEP = 0;
    private static final int STATUS_CODE_400 = 400;

    private final Callback<Void> onSendAuthDataCallback = new Callback<Void>() {
        private static final String LOG_TAG = "sendAuthDataCallback";

        @Override
        public void onResponse(@NonNull final Call<Void> call, @NonNull final Response<Void> response) {
            Log.d("==", String.valueOf(response.body()));
            view.onProcessingFinished();
            if (response.isSuccessful()) {
                Log.d(LOG_TAG, "sendData successful");
                view.onMoveToNextScreen();
            } else {
                Log.d(LOG_TAG, "Code: " + response.code());
                onRequestFailed(response.code());
            }
        }

        @Override
        public void onFailure(@NonNull final Call<Void> call, @NonNull final Throwable t) {
            view.onProcessingFinished();
            Log.d(LOG_TAG, "sendData failure");
            onShowMessageCallback.showMessage(R.string.no_internet);
            view.onRestartRecording();
        }
    };

    public VoiceAuthPresenterImpl(@NonNull final BaseRecordingView view, @NonNull final OnShowMessageCallback onShowMessageCallback) {
        super(view, onShowMessageCallback);
    }

    @Override
    public void sendData() {
        super.sendData();
        final Call<Void> call = NetworkManager.getInstance().getNetworkApi().sendAuthUserData(userData);
        call.enqueue(onSendAuthDataCallback);
    }

    @Override
    public void onFinishSampling() {
        updateStep(sampleNumber++);
    }

    private void updateStep(final int step) {
        final Disposable disposable = Observable.just(INITIAL_STEP)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> view.onSampleRecorded(step));
        compositeDisposable.add(disposable);
    }

    private void onRequestFailed(final int responseCode) {
        switch (responseCode) {
            case STATUS_CODE_400:
                onShowMessageCallback.showMessage(R.string.user_exists);
                break;
            default:
                onShowMessageCallback.showMessage(R.string.no_response);
                break;
        }
        view.onRestartRecording();
    }
}










