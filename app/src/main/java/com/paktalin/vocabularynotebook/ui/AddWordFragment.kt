package com.paktalin.vocabularynotebook.ui

import android.util.Log
import android.view.View
import com.paktalin.vocabularynotebook.R
import com.paktalin.vocabularynotebook.appsetup.ConfiguredFirestore
import com.paktalin.vocabularynotebook.firestoreitems.Vocabulary.Companion.VOCABULARIES
import com.paktalin.vocabularynotebook.firestoreitems.Vocabulary.Companion.WORDS
import com.paktalin.vocabularynotebook.firestoreitems.WordItem
import com.paktalin.vocabularynotebook.shortToast
import kotlinx.android.synthetic.main.fragment_new_word.*

class AddWordFragment : WordFragment() {

    override fun saveToFirestore(word:String, translation:String, vocabularyId: String) {
        val wordPojo = WordItem.Pojo(word, translation, null)
        ConfiguredFirestore.instance
                .collection(VOCABULARIES).document(vocabularyId)
                .collection(WORDS).add(wordPojo)
                .addOnSuccessListener {
                    Log.i(TAG, "Successfully added a new word")
                    clearFields()
                    mainActivity.removeProgressBar()
                    val wordItem = WordItem(wordPojo, it.id, vocabularyId)
                    updateRecycleView(wordItem) }
                .addOnFailureListener {
                    Log.w(TAG, "addNewWordToDb:failure", it.fillInStackTrace())
                    shortToast(mainActivity, getString(R.string.toast_new_word_fail))
                }
    }

    override fun updateRecycleView(wordItem: WordItem) {
        mainActivity.vocabularyFragment.addWord(wordItem)
    }


    override fun updateButtons() {
        super.updateButtons()
        if (!wordEmpty || !translationEmpty) showClearButton()
        if (wordEmpty && translationEmpty) hideClearButton()
    }

    private fun hideClearButton() { btnClear.visibility = View.INVISIBLE }

    private fun showClearButton() { btnClear.visibility = View.VISIBLE }

    companion object { private val TAG = "VN/" + AddWordFragment::class.java.simpleName }
}