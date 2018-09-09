package com.paktalin.vocabularynotebook.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.paktalin.vocabularynotebook.R
import com.paktalin.vocabularynotebook.VocabularyAdapter
import com.paktalin.vocabularynotebook.WordItem
import kotlinx.android.synthetic.main.fragment_vocabulary.*

class VocabularyFragment : Fragment() {
    companion object {
        private val TAG = "VN/" + VocabularyFragment::class.simpleName
        private const val VOCABULARIES = "vocabularies"
        private const val WORDS = "words"
    }

    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_vocabulary, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setEmptyAdapter()
    }

    override fun onDestroy() {
        super.onDestroy()
        FirebaseAuth.getInstance().signOut()
    }

    private fun setEmptyAdapter() {
        val emptyList: MutableList<WordItem> = mutableListOf()
        recyclerView.adapter = VocabularyAdapter(emptyList, activity!!)
        val mLayoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = mLayoutManager
    }

    fun retrieveWordsData(vocabularyId: String) {
        db.collection(VOCABULARIES).document(vocabularyId).collection(WORDS).get()
                .addOnSuccessListener { setVocabularyAdapter(it.documents, vocabularyId) }
    }

    private fun setVocabularyAdapter(documents: MutableList<DocumentSnapshot>, vocabularyId: String) {
        val wordItems: MutableList<WordItem> = mutableListOf()

        for (ref in documents) {
            val word = ref.get("word").toString()
            val translation = ref.get("translation").toString()
            wordItems.add(WordItem(word, translation, ref.id, vocabularyId))
        }

        val adapter = VocabularyAdapter(wordItems, activity!!)
        recyclerView.adapter = adapter
    }
}