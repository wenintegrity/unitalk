package com.unitalk.ui.result

import com.unitalk.core.App
import com.unitalk.ui.BaseQueriesPresenterImpl

class ResultPresenterImpl(private val view: ResultView) : BaseQueriesPresenterImpl(), ResultPresenter<ResultView> {

    override fun refreshResults() {
        val results = App.getInstance().sharedManager.results
        view.onResultsUpdated(results)
    }
}
