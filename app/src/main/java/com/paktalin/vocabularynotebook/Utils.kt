package com.paktalin.vocabularynotebook

import android.content.Context
import android.os.Build
import android.text.TextUtils
import android.widget.EditText
import android.widget.Toast

class Utils {
    companion object {
        fun fieldsNotEmpty(text1: String, text2: String, toastMessage: String, context: Context): Boolean {
            if (TextUtils.isEmpty(text1) || TextUtils.isEmpty(text2)) {
                Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
                return false
            }
            return true
        }
    }
}