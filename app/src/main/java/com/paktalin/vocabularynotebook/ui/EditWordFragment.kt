package com.paktalin.vocabularynotebook.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.paktalin.vocabularynotebook.R
import com.paktalin.vocabularynotebook.firestoreitems.WordItem
import com.paktalin.vocabularynotebook.appsetup.ConfiguredFirestore
import kotlinx.android.synthetic.main.fragment_new_word.*

class EditWordFragment : WordFragment() {
    private lateinit var wordItem: WordItem

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mainActivity = activity as MainActivity
        hidePreviousViews(container)
        wordItem = arguments!!["wordItem"] as WordItem
        return inflater.inflate(R.layout.fragment_new_word, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setWordItemData()
        setFocusOnWord()
    }

    private fun setWordItemData() {
        word.setText(wordItem.pojo!!.word)
        translation.setText(wordItem.pojo!!.translation)
    }

    private fun setFocusOnWord() {
        word.requestFocus()
        val imm = mainActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm!!.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    private fun hidePreviousViews(container: ViewGroup?) {
        if (container != null) {
            container.findViewById<ImageView>(R.id.line).visibility = View.GONE
            container.findViewById<TextView>(R.id.word).visibility = View.GONE
            container.findViewById<TextView>(R.id.translation).visibility = View.GONE
        }
    }

    override fun saveToFirestore(wordPojo: WordItem.Pojo, vocabularyId: String) {
        mainActivity.showProgressBar()
        ConfiguredFirestore.instance
                .collection(vocabularies).document(vocabularyId)
                .collection(words).document(wordItem.id).set(wordPojo)
                .addOnSuccessListener {
                    Log.i(TAG, "Successfully updated the word")
                    hideSubmitButton()
                    mainActivity.hideProgressBar()
                    wordItem.pojo = wordPojo
                    updateRecycleView(wordItem) }
                .addOnFailureListener {
                    Log.w(TAG, "updateExistingWord:failure", it.fillInStackTrace())
                    Toast.makeText(mainActivity, "Couldn't update the word", Toast.LENGTH_SHORT).show()}    }

    override fun updateRecycleView(wordItem: WordItem) {
        mainActivity.vocabularyFragment.updateWordItem(wordItem)
    }

    companion object { private val TAG = "VN/" + EditWordFragment::class.java.simpleName }
}