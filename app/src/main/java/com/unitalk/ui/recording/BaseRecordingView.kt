package com.unitalk.ui.recording

interface BaseRecordingView {
    fun onVoiceRecorded()
    fun onVisualizeAmplitude(amplitude: Int)
    fun onProcessingFinished()
    fun onMoveToNextScreen()
    fun onConcentrating(count: Long)
    fun onSampleRecorded(step: Int)
    fun onShowRecordingErrorHint()
    fun onUpdateProgress(ms: Int)
    fun onRestartRecording()
}