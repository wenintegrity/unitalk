package com.unitalk.ui.callback

import android.support.v4.app.Fragment

interface OnScreenNavigationCallback {
    fun moveToScreenWithoutBack(screenClassToMove: Class<*>)
    fun moveToScreen(screenClassToMove: Class<*>)

    fun startFragmentTransaction(fragment: Fragment)
    fun startFragmentTransactionFromRightToLeft(fragment: Fragment)
    fun startFragmentCustomTransaction(fragment: Fragment,
                                       animEnter: Int, animExit: Int,
                                       enterFromStack: Int, exitFromStack: Int)
}
