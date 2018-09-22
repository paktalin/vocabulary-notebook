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
import com.paktalin.vocabularynotebook.firestoreitems.Vocabulary.Companion.VOCABULARIES
import com.paktalin.vocabularynotebook.firestoreitems.Vocabulary.Companion.WORDS
import com.paktalin.vocabularynotebook.firestoreitems.WordItem
import kotlinx.android.synthetic.main.fragment_vocabulary.*

class VocabularyFragment : Fragment() {
    companion object {
        private val TAG = "VN/" + VocabularyFragment::class.simpleName
    }

    private val db = ConfiguredFirestore.instance
    private lateinit var mainActivity: MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_vocabulary, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mainActivity = activity as MainActivity
        setEmptyAdapter()
        retrieveWordsData(arguments!!["vocabularyId"] as String)
    }

    private fun setEmptyAdapter() {
        val emptyList: MutableList<WordItem> = mutableListOf()
        recyclerView.adapter = VocabularyAdapter(Vocabulary(emptyList), mainActivity)
        val mLayoutManager = LinearLayoutManager(mainActivity)
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
                        Log.i(TAG, "There are no documents in collection \"words\"")
                        mainActivity.showToastNoWords()
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
        val adapter = VocabularyAdapter(vocabulary, mainActivity)
        recyclerView.adapter = adapter
    }

    fun addWord(newWord: WordItem) { (recyclerView.adapter as VocabularyAdapter).addWord(newWord) }

    fun updateWord(updatedWord: WordItem) {
        (recyclerView.adapter as VocabularyAdapter).updateWord(updatedWord) }
}