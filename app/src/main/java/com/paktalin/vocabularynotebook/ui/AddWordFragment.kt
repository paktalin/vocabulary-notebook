package com.paktalin.vocabularynotebook.ui

import android.util.Log
import android.widget.Toast
import com.paktalin.vocabularynotebook.appsetup.ConfiguredFirestore
import com.paktalin.vocabularynotebook.firestoreitems.WordItem

class AddWordFragment : WordFragment() {

    override fun saveToFirestore(wordPojo: WordItem.Pojo, vocabularyId: String) {
        mainActivity.showProgressBar()
        ConfiguredFirestore.instance
                .collection(VOCABULARIES).document(vocabularyId)
                .collection(WORDS).add(wordPojo)
                .addOnSuccessListener {
                    Log.i(TAG, "Successfully added a new word")
                    clearFields()
                    mainActivity.hideProgressBar()
                    val wordItem = WordItem(wordPojo, it.id, vocabularyId)
                    updateRecycleView(wordItem) }
                .addOnFailureListener {
                    Log.w(TAG, "addNewWordToDb:failure", it.fillInStackTrace())
                    Toast.makeText(mainActivity, "Couldn't add the word", Toast.LENGTH_SHORT).show()}
    }

    override fun updateRecycleView(wordItem: WordItem) {
        mainActivity.vocabularyFragment.addWordItem(wordItem)
    }

    companion object { private val TAG = "VN/" + AddWordFragment::class.java.simpleName }
}