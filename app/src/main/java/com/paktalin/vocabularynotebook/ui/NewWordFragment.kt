package com.paktalin.vocabularynotebook.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.google.firebase.firestore.FirebaseFirestore
import com.paktalin.vocabularynotebook.R
import kotlinx.android.synthetic.main.fragment_new_word.*

class NewWordFragment : Fragment() {

    private var wordEmpty: Boolean = true
    set(value) { field = value; updateButtons() }

    private var translationEmpty: Boolean = true
    set(value) { field = value; updateButtons() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_new_word, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        etWord.addTextChangedListener(textWatcher {
            wordEmpty = etWord.text.isEmpty() })

        etTranslation.addTextChangedListener(textWatcher {
            translationEmpty = etTranslation.text.isEmpty() })

        btnClear.setOnClickListener {
            etWord.text.clear()
            etTranslation.text.clear()
        }
        activity!!.findViewById<ImageButton>(R.id.btnAddWord).setOnClickListener { addWord() }
    }

    private fun textWatcher(setEmpty: () -> Unit): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) { }
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) { }
            override fun afterTextChanged(editable: Editable) { setEmpty() }
        }
    }

    private fun updateButtons() {
        if (!wordEmpty || !translationEmpty)
            showClearButton()
        if (!wordEmpty && !translationEmpty)
            showAddWordButton()
        if (wordEmpty || translationEmpty)
            hideAddWordButton()
        if (wordEmpty && translationEmpty)
            hideClearButton()
    }

    private fun showAddWordButton() {
        Log.d(TAG, "showAddWordButton")
        activity!!.findViewById<ImageButton>(R.id.btnAddWord).visibility = View.VISIBLE
    }

    private fun hideAddWordButton() {
        Log.d(TAG, "hideAddWordButton")
        activity!!.findViewById<ImageButton>(R.id.btnAddWord).visibility = View.GONE
    }

    private fun hideClearButton() {
        Log.d(TAG, "hideClearButton")
        btnClear.visibility = View.GONE
    }

    private fun showClearButton() {
        Log.d(TAG, "showClearButton")
        btnClear.visibility = View.VISIBLE
    }

    private fun addWord() {
        //todo get word data from edit texts and save it
        val word = etWord.text.toString()
        val translation = etTranslation.text.toString()

        Log.d(TAG, "vocabularyId: ${(activity as MainActivity).vocabularyId})")

        FirebaseFirestore.getInstance().collection("vocabularies").document()
    }

    companion object { private val TAG = "VN/" + NewWordFragment::class.java.simpleName }
}