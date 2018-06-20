package com.unitalk.ui.recording

import com.unitalk.ui.BaseQueriesPresenter

interface BaseRecordingPresenter<RecordingView> : BaseQueriesPresenter {
    fun record()
    fun sendData()
}
