package com.unitalk.ui.recording.sampling;

import android.support.annotation.NonNull;
import android.util.Log;

import com.unitalk.R;
import com.unitalk.core.App;
import com.unitalk.network.NetworkManager;
import com.unitalk.network.model.FirstSamplingResultsData;
import com.unitalk.network.model.HarmonyResultsData;
import com.unitalk.network.model.NextSamplingResultsData;
import com.unitalk.ui.callback.OnShowMessageCallback;
import com.unitalk.ui.recording.BaseRecordingPresenterImpl;

import java.text.DecimalFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VoiceSamplingPresenterImpl extends BaseRecordingPresenterImpl<VoiceSamplingView> {
    private static final int STATUS_CODE_400 = 400;
    private final Callback<FirstSamplingResultsData> onSendFirstSampleDataCallback = new Callback<FirstSamplingResultsData>() {
        private static final String LOG_TAG = "sendFirstSampleCallback";

        @Override
        public void onResponse(@NonNull final Call<FirstSamplingResultsData> call, @NonNull final Response<FirstSamplingResultsData> response) {
            final FirstSamplingResultsData firstSamplingResultsData = response.body();
            if (response.isSuccessful() && firstSamplingResultsData != null) {
                App.getInstance().getSharedManager().setSessionID(firstSamplingResultsData.getSessionID());
                onRequestSuccessful(firstSamplingResultsData.getCalculationID(), getResults(firstSamplingResultsData.getHarmonyResultsData()));
                Log.d(LOG_TAG, "sendData successful");
            } else {
                onRequestFailed(response.code());
                Log.d(LOG_TAG, "Code: " + response.code());
            }
            view.onProcessingFinished();
        }

        @Override
        public void onFailure(@NonNull final Call<FirstSamplingResultsData> call, @NonNull final Throwable t) {
            onServerConnectionFailed();
            Log.d(LOG_TAG, "sendData failure");
        }
    };

    // FIXME: 4/20/18 maybe change repeated code?
    private final Callback<NextSamplingResultsData> onSendNextSampleDataCallback = new Callback<NextSamplingResultsData>() {
        private static final String LOG_TAG = "sendNextSampleCallback";

        @Override
        public void onResponse(@NonNull final Call<NextSamplingResultsData> call, @NonNull final Response<NextSamplingResultsData> response) {
            view.onProcessingFinished();
            final NextSamplingResultsData nextSamplingResultsData = response.body();
            if (response.isSuccessful() && nextSamplingResultsData != null) {
                onRequestSuccessful(nextSamplingResultsData.getCalculationID(), getResults(nextSamplingResultsData.getHarmonyResultsData()));
                Log.d(LOG_TAG, "sendData successful");
            } else {
                onRequestFailed(response.code());
                Log.d(LOG_TAG, "Code: " + response.code());
            }
        }

        @Override
        public void onFailure(@NonNull final Call<NextSamplingResultsData> call, @NonNull final Throwable t) {
            // FIXME: 4/20/18 remove two methods view call
            onServerConnectionFailed();
            Log.d(LOG_TAG, "sendData failure");
        }
    };

    public VoiceSamplingPresenterImpl(@NonNull final VoiceSamplingView view, @NonNull final OnShowMessageCallback onShowMessageCallback) {
        super(view, onShowMessageCallback);
    }

    @Override
    public void sendData() {
        super.sendData();
        final String sessionID = App.getInstance().getSharedManager().getSessionID();
        if (sessionID.isEmpty()) {
            final Call<FirstSamplingResultsData> call = NetworkManager.getInstance().getNetworkApi().sendFirstSampleUserData(userData);
            call.enqueue(onSendFirstSampleDataCallback);
        } else {
            final Call<NextSamplingResultsData> call = NetworkManager.getInstance().getNetworkApi().sendNextSampleUserData(sessionID, userData);
            call.enqueue(onSendNextSampleDataCallback);
        }
    }

    @Override
    public void onFinishSampling() {
        view.onSampleRecorded(sampleNumber++);
    }

    @NonNull
    private String getResults(@NonNull final HarmonyResultsData result) {
        final DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        final StringBuilder results = new StringBuilder();
        results.append(df.format(result.getFirstResult()));
        results.append("%");
        results.append(System.lineSeparator());
        results.append(df.format(result.getSecondResult()));
        results.append("%");
        return results.toString();
    }

    private void onRequestSuccessful(@NonNull final String calculationID, @NonNull final String results) {
        App.getInstance().getSharedManager().setCalculationID(calculationID);
        App.getInstance().getSharedManager().setResults(results);
        view.onMoveToNextScreen();
    }

    private void onRequestFailed(final int responseCode) {
        if (responseCode == STATUS_CODE_400) {
            onShowMessageCallback.showMessage(R.string.user_not_found);
        } else {
            onShowMessageCallback.showMessage(R.string.no_response);
        }
        view.onRestartRecording();
    }

    private void onServerConnectionFailed() {
        view.onProcessingFinished();
        onShowMessageCallback.showMessage(R.string.no_internet);
        view.onRestartRecording();
    }
}










