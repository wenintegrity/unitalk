package com.unitalk.ui.callback

interface OnMainActivityCallback {
    fun startVideoFragment()
    fun startHarmonyOneCardFragment(moodCardName: String)
    fun startHarmonyCheckingCardFragment(moodCardList: List<String>)
}
