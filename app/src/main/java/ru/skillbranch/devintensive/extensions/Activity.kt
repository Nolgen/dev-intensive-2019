package ru.skillbranch.devintensive.extensions

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.view.View
import android.view.inputmethod.InputMethodManager

fun Activity.hideKeyboard() {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    val token = findViewById<View>(android.R.id.content).windowToken
    imm.hideSoftInputFromWindow(token,0)
}

fun Activity.isKeyboardClosed():Boolean {
    return isKeyboardOpen().not()
}

fun Activity.isKeyboardOpen(): Boolean {
    val rootView = findViewById<View>(android.R.id.content)

    val visibleBounds = Rect()
    rootView.getWindowVisibleDisplayFrame(visibleBounds)

    val appHeight = rootView.height            //высота всего приложения
    val visibleHeight = visibleBounds.height() //высота видимой части приложения

    // True если скрыто более 15% приложения
    return (appHeight-visibleHeight) > (appHeight * 0.15)

}
