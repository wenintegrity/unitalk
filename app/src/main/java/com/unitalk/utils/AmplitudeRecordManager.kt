package com.unitalk.utils

import android.media.MediaRecorder
import android.util.Log
import java.io.IOException

class AmplitudeRecordManager {
    companion object {
        private val TAG = "AmplitudeRecorder"
    }

    private var amplitudeRecorder: MediaRecorder? = null
    private var isRecording: Boolean = false

    val voiceAmplitude: Int
        get() = if (amplitudeRecorder != null && isRecording) {
            amplitudeRecorder!!.maxAmplitude
        } else -Int.MAX_VALUE

    fun createAmplitudeRecorder() {
        isRecording = false
        amplitudeRecorder = MediaRecorder()
        amplitudeRecorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
        amplitudeRecorder?.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
        amplitudeRecorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
        amplitudeRecorder?.setOutputFile("/dev/null")
    }

    fun startAmplitudeRecording() {
        if (amplitudeRecorder != null && !isRecording) {
            try {
                amplitudeRecorder?.prepare()
                amplitudeRecorder?.start()
                isRecording = true
            } catch (e: IOException) {
                Log.d(TAG, e.message)
            }
        }
    }

    fun stopAmplitudeRecorder() {
        if (amplitudeRecorder != null) {
            isRecording = false
        }
    }

    fun releaseAmplitudeRecorder() {
        if (amplitudeRecorder != null) {
            amplitudeRecorder?.release()
        }
    }
}