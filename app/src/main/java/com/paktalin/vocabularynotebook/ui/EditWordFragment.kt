package com.paktalin.vocabularynotebook.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.paktalin.vocabularynotebook.firestoreitems.WordItem
import com.paktalin.vocabularynotebook.appsetup.ConfiguredFirestore
import kotlinx.android.synthetic.main.fragment_new_word.*
import kotlinx.android.synthetic.main.notebook_sheet.*
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
                    mainActivity.hideProgressBar()
                    wordItem.pojo = wordPojo
                    updateRecycleView(wordItem)
                    stop()
                }
                .addOnFailureListener {
                    Log.w(TAG, "updateExistingWord:failure", it.fillInStackTrace())
                    Toast.makeText(mainActivity, "Couldn't update the word", Toast.LENGTH_SHORT).show()
                    stop()
                }
    }

    private fun stop() {
        // set onClickListener from AddWordFragment
        mainActivity.btnSubmit.setOnClickListener { (mainActivity.fragmentNewWord as AddWordFragment).submitWord() }
        mainActivity.removeFragment(this)
    }

    override fun updateRecycleView(wordItem: WordItem) {
        mainActivity.vocabularyFragment.updateWord(wordItem)
    }

    companion object {
        private val TAG = "VN/" + EditWordFragment::class.java.simpleName
    }
}