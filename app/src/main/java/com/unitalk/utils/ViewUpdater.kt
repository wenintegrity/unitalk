package com.unitalk.utils
import android.view.View

fun showViews(vararg views: View) {
    for (view in views) {
        view.visibility = View.VISIBLE
    }
}

fun goneViews(vararg views: View) {
    for (view in views) {
        view.visibility = View.GONE
    }
}

fun hideViews(vararg views: View) {
    for (view in views) {
        view.visibility = View.INVISIBLE
    }
}