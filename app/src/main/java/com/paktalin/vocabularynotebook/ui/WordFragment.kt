package com.paktalin.vocabularynotebook.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageButton
import com.paktalin.vocabularynotebook.R
import com.paktalin.vocabularynotebook.firestoreitems.WordItem
import kotlinx.android.synthetic.main.fragment_new_word.*

abstract class WordFragment : Fragment() {

    companion object {
        const val VOCABULARIES = "vocabularies"
        const val WORDS = "words"
    }
    protected lateinit var mainActivity: MainActivity
    open var TAG = WordFragment::class.simpleName

    private var wordEmpty: Boolean = true
        set(value) { field = value; updateButtons() }

    private var translationEmpty: Boolean = true
        set(value) { field = value; updateButtons() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_new_word, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mainActivity = activity as MainActivity
        mainActivity.findViewById<ImageButton>(R.id.btnAddWord).setOnClickListener { submitWord() }
        word.addTextChangedListener(textWatcher {
            wordEmpty = word.text.isEmpty() })

        translation.addTextChangedListener(textWatcher {
            translationEmpty = translation.text.isEmpty() })

        btnClear.setOnClickListener { clearFields() }
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
        mainActivity.findViewById<FrameLayout>(R.id.btnSubmitLayout).visibility = View.VISIBLE }

    protected fun hideSubmitButton() {
        mainActivity.findViewById<FrameLayout>(R.id.btnSubmitLayout).visibility = View.GONE }

    private fun hideClearButton() { btnClear.visibility = View.INVISIBLE }

    private fun showClearButton() { btnClear.visibility = View.VISIBLE }

    fun submitWord() {
        mainActivity.hideKeyboardNotFromActivity(mainActivity)

        val word = word.text.toString()
        val translation = translation.text.toString()
        val vocabularyId = mainActivity.vocabularyId
        val wordPojo = WordItem.Pojo(word, translation, null)

        saveToFirestore(wordPojo, vocabularyId)
        return
    }

    protected fun clearFields() {
        word.text.clear()
        translation.text.clear()
    }

    protected abstract fun saveToFirestore(wordPojo: WordItem.Pojo, vocabularyId:String)
    protected abstract fun updateRecycleView(wordItem: WordItem)
}