package com.paktalin.vocabularynotebook.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.paktalin.vocabularynotebook.R
import kotlinx.android.synthetic.main.fragment_new_word.*

class NewWordFragment : Fragment() {

    private var etWordEmpty = true
    private var etTranslationEmpty = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_new_word, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        etWord.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) { }
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) { }
            override fun afterTextChanged(editable: Editable) {
                if (!etWord.text.isEmpty()) {
                    showCancelButton()
                    etWordEmpty = false
                } else etWordEmpty = true
                if (!etWordEmpty && !etTranslationEmpty)
                    showAddWordButton()
            }
        })
        etTranslation.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) { }
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) { }
            override fun afterTextChanged(editable: Editable) {
                if (!etTranslation.text.isEmpty()) {
                    showCancelButton()
                    etTranslationEmpty = false
                } else etTranslationEmpty = true
                if (!etWordEmpty && !etTranslationEmpty)
                    showAddWordButton()
            }
        })
    }

    private fun showAddWordButton() {
        //todo show add word button
    }

    private fun showCancelButton() {
        Log.d(TAG, "empty word is focused")
        btnClear.setImageResource(R.drawable.ic_cancel_icon)
        btnClear.visibility = View.VISIBLE
        //todo add button click listener
    }

    companion object { private val TAG = "VN/" + NewWordFragment::class.java.simpleName }
}