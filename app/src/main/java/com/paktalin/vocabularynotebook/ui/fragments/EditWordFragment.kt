package com.paktalin.vocabularynotebook.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.paktalin.vocabularynotebook.R
import com.paktalin.vocabularynotebook.firestoreitems.WordItem
import com.paktalin.vocabularynotebook.appsetup.ConfiguredFirestore
import com.paktalin.vocabularynotebook.firestoreitems.Vocabulary.Companion.VOCABULARIES
import com.paktalin.vocabularynotebook.firestoreitems.Vocabulary.Companion.WORDS
import com.paktalin.vocabularynotebook.removeFragment
import com.paktalin.vocabularynotebook.shortToast
import com.paktalin.vocabularynotebook.ui.activities.MainActivity
import kotlinx.android.synthetic.main.fragment_editable_word.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.word_item.view.*

class EditWordFragment : WordFragment() {
    private lateinit var wordItem: WordItem

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mainActivity = activity as MainActivity
        hidePreviousViews(container)
        wordItem = arguments!!["wordItem"] as WordItem
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setWordItemData()
        setFocusOnWord()
    }

    private fun setWordItemData() {
        word.setText(wordItem.pojo.word)
        translation.setText(wordItem.pojo.translation)
    }

    private fun setFocusOnWord() {
        word.requestFocus()
        val imm = mainActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm!!.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    private fun hidePreviousViews(container: ViewGroup?) {
        if (container != null) {
            container.line.visibility = View.GONE
            container.word.visibility = View.GONE
            container.translation.visibility = View.GONE
        }
    }

    override fun saveToFirestore(word:String, translation:String, vocabularyId: String) {
        val wordPojo = WordItem.Pojo(word, translation, wordItem.pojo.time)
        ConfiguredFirestore.instance
                .collection(VOCABULARIES).document(vocabularyId)
                .collection(WORDS).document(wordItem.id).set(wordPojo)
                .addOnSuccessListener {
                    Log.i(TAG, "Successfully updated the word")
                    hideSubmitButton()
                    mainActivity.removeProgressBar()
                    wordItem.pojo = wordPojo
                    updateRecycleView(wordItem)
                    stop()
                }
                .addOnFailureListener {
                    Log.w(TAG, "updateExistingWord:failure", it.fillInStackTrace())
                    shortToast(mainActivity, getString(R.string.toast_update_word_failed))
                    stop()
                }
    }

    private fun stop() {
        // set onClickListener from AddWordFragment
        mainActivity.btnSubmit.setOnClickListener { (mainActivity.fragmentAddWord as AddWordFragment).submitWord() }
        removeFragment(mainActivity.supportFragmentManager, this)
    }

    override fun updateRecycleView(wordItem: WordItem) {
        mainActivity.vocabularyFragment.updateWord(wordItem)
    }

    companion object {
        private val TAG = "VN/" + EditWordFragment::class.java.simpleName
    }
}