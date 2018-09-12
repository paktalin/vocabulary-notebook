package com.paktalin.vocabularynotebook.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageButton
import com.paktalin.vocabularynotebook.R
import com.paktalin.vocabularynotebook.WordItem
import kotlinx.android.synthetic.main.fragment_new_word.*

abstract class WordFragment : Fragment() {
    protected val vocabularies = "vocabularies"
    protected val words = "words"
    protected lateinit var mainActivity: MainActivity

    private var wordEmpty: Boolean = true
        set(value) { field = value; updateButtons() }

    private var translationEmpty: Boolean = true
        set(value) { field = value; updateButtons() }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        word.addTextChangedListener(textWatcher {
            wordEmpty = word.text.isEmpty() })

        translation.addTextChangedListener(textWatcher {
            translationEmpty = translation.text.isEmpty() })

        btnClear.setOnClickListener { clearFields() }
        activity!!.findViewById<ImageButton>(R.id.btnAddWord).setOnClickListener { submitWord() }
    }


    private fun updateButtons() {
        if (!wordEmpty || !translationEmpty)
            showClearButton()
        if (!wordEmpty && !translationEmpty)
            showSubmitButton()
        if (wordEmpty || translationEmpty)
            hideSubmitButton()
        if (wordEmpty && translationEmpty)
            hideClearButton()
    }

    private fun textWatcher(setEmpty: () -> Unit): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) { }
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) { }
            override fun afterTextChanged(editable: Editable) { setEmpty() }
        }
    }

    private fun showSubmitButton() {
        activity!!.findViewById<FrameLayout>(R.id.btnSubmitLayout).visibility = View.VISIBLE }

    protected fun hideSubmitButton() {
        activity!!.findViewById<FrameLayout>(R.id.btnSubmitLayout).visibility = View.GONE }

    private fun hideClearButton() { btnClear.visibility = View.INVISIBLE }

    private fun showClearButton() { btnClear.visibility = View.VISIBLE }

    private fun submitWord() {
        (activity as MainActivity).hideKeyboardNotFromActivity(activity as MainActivity)

        val word = word.text.toString()
        val translation = translation.text.toString()
        val vocabularyId = (activity as MainActivity).vocabularyId
        val wordPojo = WordItem.Pojo(word, translation)

        saveToFirestore(wordPojo, vocabularyId)
    }

    protected fun clearFields() {
        word.text.clear()
        translation.text.clear()
    }

    protected abstract fun saveToFirestore(wordPojo:WordItem.Pojo, vocabularyId:String)
    protected abstract fun updateRecycleView(wordItem: WordItem)
}