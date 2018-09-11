package com.paktalin.vocabularynotebook.ui

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import com.paktalin.vocabularynotebook.R
import com.paktalin.vocabularynotebook.WordItem

class EditWordFragment : Fragment() {

    private lateinit var wordItem: WordItem
    private lateinit var etWord: EditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        hidePreviousViews(container)
        wordItem = arguments!!["wordItem"] as WordItem
        return inflater.inflate(R.layout.editable_word_item, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        etWord = view!!.findViewById(R.id.word)
        setWordItemData()
        setFocusOnWord()
    }

    private fun setWordItemData() {
        etWord.setText(wordItem.pojo!!.word)
        view!!.findViewById<EditText>(R.id.translation).setText(wordItem.pojo!!.translation)
    }

    private fun setFocusOnWord() {
        activity!!.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        etWord.requestFocus()
        val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm!!.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    private fun hidePreviousViews(container: ViewGroup?) {
        if (container != null) {
            container.findViewById<TextView>(R.id.word).visibility = View.GONE
            container.findViewById<TextView>(R.id.translation).visibility = View.GONE
        }
    }
}