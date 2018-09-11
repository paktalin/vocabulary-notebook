package com.paktalin.vocabularynotebook.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.paktalin.vocabularynotebook.R
import com.paktalin.vocabularynotebook.WordItem
import kotlinx.android.synthetic.main.activity_word_info.*

class WordInfoFragment : Fragment() {

    private lateinit var wordItem:WordItem

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        wordItem = arguments!!["wordItem"] as WordItem
        return inflater.inflate(R.layout.activity_word_info, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setData()
    }

    private fun setData() {
        etWord.text = wordItem.pojo!!.word
        etTranslation.text = wordItem.pojo!!.translation
    }

}