package com.paktalin.vocabularynotebook

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.paktalin.vocabularynotebook.ui.fragments.ProgressFragment

val progressFragment: Fragment = ProgressFragment()

fun addFragment(fragmentManager: FragmentManager, fragment: Fragment, containerId: Int, arguments: Bundle?) {
    fragment.arguments = arguments
    fragmentManager.beginTransaction().add(containerId, fragment).commitAllowingStateLoss()
}

fun removeFragment(fragmentManager: FragmentManager, fragment: Fragment) {
    fragmentManager.beginTransaction().remove(fragment).commitAllowingStateLoss()
}

fun addProgressBar(fragmentManager: FragmentManager, containerId: Int) {
    addFragment(fragmentManager, progressFragment, containerId, null)
}

fun removeProgressBar(fragmentManager: FragmentManager) {
    removeFragment(fragmentManager, progressFragment)
}

fun fieldsNotEmpty(text1: String, text2: String, toastMessage: String, context: Context): Boolean {
    if (TextUtils.isEmpty(text1) || TextUtils.isEmpty(text2)) {
        shortToast(context, toastMessage)
        return false
    }
    return true
}

fun shortToast(context: Context, text: String) {
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
}

fun show(view: View) {
    view.visibility = View.VISIBLE
}

fun hide(view: View) {
    view.visibility = View.INVISIBLE
}