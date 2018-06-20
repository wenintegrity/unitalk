package com.unitalk.ui.videos

import com.unitalk.core.App
import com.unitalk.enums.CardsState
import com.unitalk.enums.MoodCardsModel
import com.unitalk.ui.BasePresenterImpl

class VideosPresenterImpl(private val view: VideosView) : BasePresenterImpl(), VideosPresenter<VideosView> {

    override fun moveToCardsScreen() {
        val cardState = App.getInstance().sharedManager.cardsState
        if (cardState == CardsState.FOUR.name) {
            view.goToCardsFragment(false)
        } else {
            view.goToCardsFragment(true)
        }
    }

    override fun createSixMoodItemsList(isSixCardsList: Boolean): List<String> {
        val list = mutableListOf<String>()
        list.add(MoodCardsModel.AFRAID_AGGRESSIVE.name)
        list.add(MoodCardsModel.ANGRY_BLAMING.name)
        list.add(MoodCardsModel.SUBMISSIVE_PLEASER.name)
        list.add(MoodCardsModel.CONTROLLING_MANIPULATIVE.name)
        if (isSixCardsList) {
            list.add(MoodCardsModel.I_UNDERSTAND_MYSELF.name)
            list.add(MoodCardsModel.I_HAVE_A_SOLUTION.name)
        }

        return list
    }
}
