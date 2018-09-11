package com.paktalin.vocabularynotebook.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.paktalin.vocabularynotebook.appsetup.ConfiguredFirestore
import com.paktalin.vocabularynotebook.R
import com.paktalin.vocabularynotebook.WordItem

class AddWordFragment : WordFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_new_word, container, false)
    }

    override fun saveToFirestore(wordPojo: WordItem.Pojo, vocabularyId: String) {
        ConfiguredFirestore.instance
                .collection(VOCABULARIES).document(vocabularyId)
                .collection(WORDS).add(wordPojo)
                .addOnSuccessListener {
                    Log.i(TAG, "Successfully added a new word")
                    clearFields()
                    val wordItem = WordItem(wordPojo, it.id, vocabularyId)
                    updateRecycleView(wordItem) }
                .addOnFailureListener {
                    Log.w(TAG, "addNewWordToDb:failure", it.fillInStackTrace())
                    Toast.makeText(activity, "Couldn't add the word", Toast.LENGTH_SHORT).show()}
    }

    override fun updateRecycleView(wordItem: WordItem) {
        val vocabularyFragment = activity!!
                .supportFragmentManager.findFragmentById(R.id.fragment_vocabulary) as VocabularyFragment
        vocabularyFragment.addWordItem(wordItem)
    }

    companion object { private val TAG = "VN/" + AddWordFragment::class.java.simpleName }
}