package com.paktalin.vocabularynotebook.ui.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.paktalin.vocabularynotebook.R
import com.paktalin.vocabularynotebook.firestoreitems.WordItem
import com.paktalin.vocabularynotebook.ui.activities.MainActivity
import kotlinx.android.synthetic.main.fragment_new_word.*
import kotlinx.android.synthetic.main.notebook_sheet.*

abstract class WordFragment : Fragment() {
    protected lateinit var mainActivity: MainActivity

    internal var wordEmpty: Boolean = true
        set(value) { field = value; updateButtons() }

    internal var translationEmpty: Boolean = true
        set(value) { field = value; updateButtons() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_new_word, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mainActivity = activity as MainActivity
        mainActivity.btnSubmit.setOnClickListener { submitWord() }
        word.addTextChangedListener(textWatcher {
            wordEmpty = word.text.isEmpty() })

        translation.addTextChangedListener(textWatcher {
            translationEmpty = translation.text.isEmpty() })

        btnClear.setOnClickListener { clearFields() }
    }

    open fun updateButtons() {
        if (!wordEmpty && !translationEmpty) showSubmitButton()
        if (wordEmpty || translationEmpty) hideSubmitButton()
    }

    private fun textWatcher(setEmpty: () -> Unit): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) { }
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) { }
            override fun afterTextChanged(editable: Editable) { setEmpty() }
        }
    }

    private fun showSubmitButton() {
        mainActivity.btnSubmitLayout.visibility = View.VISIBLE }

    protected fun hideSubmitButton() {
        mainActivity.btnSubmitLayout.visibility = View.GONE }

    fun submitWord() {
        mainActivity.hideKeyboardNotFromActivity(mainActivity)

        val word = word.text.toString()
        val translation = translation.text.toString()
        val vocabularyId = mainActivity.vocabularyId
        mainActivity.addProgressBar()
        saveToFirestore(word, translation, vocabularyId)
        return
    }

    protected fun clearFields() {
        word.text.clear()
        translation.text.clear()
    }

    protected abstract fun saveToFirestore(word:String, translation:String, vocabularyId:String)
    protected abstract fun updateRecycleView(wordItem: WordItem)
}