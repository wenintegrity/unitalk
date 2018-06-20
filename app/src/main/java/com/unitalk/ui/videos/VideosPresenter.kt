package com.unitalk.ui.videos

import com.unitalk.ui.BasePresenter

interface VideosPresenter<VideoView> : BasePresenter {
    fun moveToCardsScreen()
    fun createSixMoodItemsList(isSixCardsList: Boolean): List<String>
}
