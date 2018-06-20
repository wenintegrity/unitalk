package com.unitalk.utils;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;

import java.util.ArrayList;
import java.util.concurrent.Executors;

public class VoiceRecordManager {
    private ArrayList<Float> voiceDataRecords[];
    private int dataSize;
    private int sampleRate;
    private float[] floatData;
    private float[] sampleData;
    private boolean isRecording;
    private AudioRecord voiceRecorder;

    public VoiceRecordManager(final int recordsCount, final int dataSize, final int sampleRate) {
        voiceDataRecords = new ArrayList[recordsCount];
        this.dataSize = dataSize;
        this.sampleRate = sampleRate;
        createVoiceRecorder();
    }

    private void createVoiceRecorder() {
        createDataCollection();
        final int channelConfig = AudioFormat.CHANNEL_IN_MONO;
        final int audioFormat = AudioFormat.ENCODING_PCM_FLOAT;
        final int minBufferSize = AudioRecord.getMinBufferSize(sampleRate, channelConfig, audioFormat);
        voiceRecorder = new AudioRecord(MediaRecorder.AudioSource.MIC, sampleRate, channelConfig, audioFormat, minBufferSize);
    }

    public void startVoiceRecording() {
        if (voiceRecorder != null && !isRecording) {
            voiceRecorder.startRecording();
            isRecording = true;
            voiceToFloatData();
        }
    }

    private void voiceToFloatData() {
        Executors.newSingleThreadExecutor().execute(() -> {
            if (voiceRecorder == null) {
                return;
            }
            floatData = new float[dataSize];
            final long startTime = System.currentTimeMillis();
            while (isRecording) {
                voiceRecorder.read(floatData, 0, dataSize, AudioRecord.READ_BLOCKING);
            }
            final long endTime = System.currentTimeMillis();
            Log.d("RECORD_TIME", String.valueOf(endTime - startTime));
            setVoiceDataRecord();
        });
    }

    public void stopVoiceRecording() {
        if (voiceRecorder != null && isRecording) {
            voiceRecorder.stop();
            isRecording = false;
        }
    }

    public void releaseVoiceRecorder() {
        if (voiceRecorder != null && !isRecording) {
            voiceRecorder.release();
        }
    }

    private void createDataCollection() {
        for (int i = 0; i < voiceDataRecords.length; i++) {
            voiceDataRecords[i] = new ArrayList<>();
        }
    }

    public float[] getSampleData() {
        return sampleData;
    }

    private void setVoiceDataRecord() {
        sampleData = new float[dataSize];
        for (int i = 0; i < floatData.length; i++) {
            sampleData[i] = floatData[i] * sampleRate;
        }
    }

    public void clearSampleData() {
        sampleData = null;
    }

    public void setData(final int index) {
        for (int i = 0; i < dataSize; i++) {
            voiceDataRecords[index].add(floatData[i] * sampleRate);
        }
    }
}
