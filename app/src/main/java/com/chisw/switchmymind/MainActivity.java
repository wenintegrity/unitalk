package com.chisw.switchmymind;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.chisw.switchmymind.network.NetworkManager;
import com.chisw.switchmymind.network.model.InputData;
import com.chisw.switchmymind.network.model.Result;
import com.chisw.switchmymind.ui.result.ResultActivity;
import com.chisw.switchmymind.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by user on 23.01.18.
 */

public class MainActivity extends Activity {
    private static final String LOG_TAG = "network";
    private static final int SAMPLE_RATE = 8018;
    private static final int DATA_SIZE = 2048;
    private static final int DATA_COUNT = 3;

    private AudioRecord audioRecord;
    private boolean isReading = false;
    private int minBufferSize;
    private MediaPlayer mp;
    private int count;
    private List<List<Byte>> lists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopMedia();
    }

    public void recordStart(View v) {
        count = 0;
        lists = new ArrayList<>(3);
        for (int i = 0; i < 3; i++) {
            lists.add(new ArrayList<Byte>());
        }

        final InputData inputData = new InputData();
        for (int i = 0; i < DATA_COUNT; i++) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mp.start();
                    audioRecord.startRecording();
                    isReading = true;

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            if (audioRecord == null) {
                                return;
                            }
                            final float[] buffer = new float[DATA_SIZE];
                            while (isReading) {
                                audioRecord.read(buffer, 0, DATA_SIZE, AudioRecord.READ_BLOCKING);
                            }

                            final ArrayList<Float> currentList = new ArrayList<>();
                            for (int i = 0; i < buffer.length; i++) {
                                currentList.add(buffer[i]);
                            }
//
//                            lists.get(count++).addAll(currentList);
                            inputData.setData_1(currentList);
                            inputData.setData_2(currentList);
                            inputData.setData_3(currentList);
                        }
                    }).start();
                }
            }, i * 5000);
        }

        for (int i = 1; i < DATA_COUNT + 1; i++) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    stopRecord();
                }
            }, i * 5500);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                stopRecord();

//                inputData.setData_1(lists.get(0));
//                inputData.setData_2(lists.get(1));
//                inputData.setData_3(lists.get(2));

                final Call<Result> call = NetworkManager.getInstance().getNetrowkAPI().sendInputData(inputData);
                call.enqueue(new SendDataCallback());
            }
        }, 18000);
    }

    private void stopRecord() {
        if (audioRecord != null) {
            isReading = false;
            audioRecord.stop();
        }
    }

    private void stopMedia() {
        stopRecord();
        if (audioRecord != null) {
            audioRecord.release();
        }

        if (mp != null) {
            mp.stop();
            mp.release();
        }
    }

    private void init() {
        mp = MediaPlayer.create(MainActivity.this, R.raw.mix1);
        createAudioRecorder();
    }

    private void createAudioRecorder() {
        final int channelConfig = AudioFormat.CHANNEL_IN_MONO;
        final int audioFormat = AudioFormat.ENCODING_PCM_FLOAT;
        minBufferSize = AudioRecord.getMinBufferSize(SAMPLE_RATE, channelConfig, audioFormat);
        audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, SAMPLE_RATE, channelConfig, audioFormat, minBufferSize); // maybe data_size here
    }

    private final class SendDataCallback implements Callback<Result> {
        @Override
        public void onResponse(@NonNull final Call<Result> call, @NonNull final Response<Result> response) {
            if (response.isSuccessful()) {
                Log.d(LOG_TAG, "sendData success");
                final Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                intent.putExtra(Constants.EXTRA_RESULT, response.body().getData());
                startActivity(intent);
            } else {
                Log.d(LOG_TAG, "sendData unsuccess");
            }
        }

        @Override
        public void onFailure(@NonNull final Call<Result> call, @NonNull final Throwable t) {
            Log.d(LOG_TAG, "sendData failure");
            Toast.makeText(MainActivity.this, R.string.no_internet, Toast.LENGTH_LONG).show();
        }
    }

    private final class GetResultCallback implements Callback<Object> {
        public void onResponse(@NonNull final Call<Object> call, @NonNull final Response<Object> response) {
            if (response.isSuccessful()) {
                Log.d(LOG_TAG, "sendData success");
                Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                intent.putExtra("result", String.valueOf(response.body()));
                startActivity(intent);
            } else {
                Log.d(LOG_TAG, "sendData unsuccess");
            }
        }

        @Override
        public void onFailure(@NonNull final Call<Object> call2, @NonNull final Throwable t) {
            Log.d(LOG_TAG, "sendData failure");
            Toast.makeText(MainActivity.this, R.string.no_internet, Toast.LENGTH_LONG).show();
        }
    }
}