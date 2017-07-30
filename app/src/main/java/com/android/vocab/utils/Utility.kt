@file:Suppress("unused")

package com.android.vocab.utils

import android.app.Activity
import android.view.inputmethod.InputMethodManager


fun hideSoftKeyboard(activity: Activity) {
    val inputMethodManager = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(activity.currentFocus!!.windowToken, 0)
}


fun convertToDp(density: Float, pix: Int): Int {
    return (density * pix + 0.5F).toInt()
}


fun convertToPixel(density: Float, dp: Int): Int {
    return ((dp - 0.5) / density).toInt()
}