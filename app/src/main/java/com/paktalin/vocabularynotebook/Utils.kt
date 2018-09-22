package com.paktalin.vocabularynotebook

import android.content.Context
import android.text.TextUtils
import android.widget.Toast

class Utils {
    companion object {

        fun fieldsNotEmpty(text1: String, text2: String, toastMessage: String, context: Context): Boolean {
            if (TextUtils.isEmpty(text1) || TextUtils.isEmpty(text2)) {
                Utils.shortToast(context, toastMessage)
                return false
            }
            return true
        }

        fun shortToast(context: Context, text: String) {
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
        }
    }
}