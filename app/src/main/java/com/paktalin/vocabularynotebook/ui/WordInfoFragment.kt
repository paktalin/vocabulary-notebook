package com.paktalin.vocabularynotebook.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.paktalin.vocabularynotebook.R

class WordInfoFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d("TTT", "onCreateView")
        return inflater.inflate(R.layout.activity_word_info, container, false)
    }

}