package com.unitalk.ui.result

import com.unitalk.ui.BaseQueriesPresenter

interface ResultPresenter<ResultView> : BaseQueriesPresenter {
    fun refreshResults()
}
