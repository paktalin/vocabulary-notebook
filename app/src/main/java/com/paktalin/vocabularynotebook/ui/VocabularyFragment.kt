package com.paktalin.vocabularynotebook.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.paktalin.vocabularynotebook.*
import com.paktalin.vocabularynotebook.appsetup.ConfiguredFirestore
import com.paktalin.vocabularynotebook.firestoreitems.Vocabulary
import com.paktalin.vocabularynotebook.firestoreitems.WordItem
import kotlinx.android.synthetic.main.fragment_vocabulary.*

class VocabularyFragment : Fragment() {
    companion object {
        private val TAG = "VN/" + VocabularyFragment::class.simpleName
        private const val VOCABULARIES = "vocabularies"
        private const val WORDS = "words"
    }

    private val db = ConfiguredFirestore.instance

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_vocabulary, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setEmptyAdapter()
        retrieveWordsData(arguments!!["vocabularyId"] as String)
    }

    override fun onDestroy() {
        super.onDestroy()
        FirebaseAuth.getInstance().signOut()
    }

    private fun setEmptyAdapter() {
        val emptyList: MutableList<WordItem> = mutableListOf()
        recyclerView.adapter = VocabularyAdapter(Vocabulary(emptyList), activity!!)
        val mLayoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = mLayoutManager
    }

    private fun retrieveWordsData(vocabularyId: String) {
        db.collection(VOCABULARIES).document(vocabularyId).collection(WORDS)
                .orderBy("time", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener {
                    if (it.documents.size != 0)
                        setVocabularyAdapter(it.documents, vocabularyId)
                else {
                        Log.i(TAG, "There are no documents in collection \"WORDS\"")
                        (activity as MainActivity).showToastNoWords()
                    }}
    }

    private fun setVocabularyAdapter(documents: MutableList<DocumentSnapshot>, vocabularyId: String) {
        val wordItems: MutableList<WordItem> = mutableListOf()

        for (ref in documents) {
            val word = ref["word"].toString()
            val translation = ref["translation"].toString()
            val time = ref["time"] as Timestamp
            wordItems.add(WordItem(word, translation, time.toDate(), ref.id, vocabularyId))
        }

        val vocabulary = Vocabulary(wordItems)
        val adapter = VocabularyAdapter(vocabulary, activity!!)
        recyclerView.adapter = adapter
    }

    fun addWordItem(newWordItem: WordItem) {
        (recyclerView.adapter as VocabularyAdapter).addWordItem(newWordItem)
    }

    fun updateWordItem(updatedWordItem: WordItem) {
        (recyclerView.adapter as VocabularyAdapter).updateWordItem(updatedWordItem)
    }
}