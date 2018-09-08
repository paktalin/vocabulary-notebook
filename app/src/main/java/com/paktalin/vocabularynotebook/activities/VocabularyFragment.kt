package com.paktalin.vocabularynotebook.activities

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
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
        private const val USERS = "users"
    }

    private lateinit var userDocument: DocumentReference
    private val db = FirebaseFirestore.getInstance()
    private lateinit var vocabulary: DocumentReference

    //todo move data process to onCreate method and update the views later

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_vocabulary, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setEmptyAdapter()
        fabAddWord.setOnClickListener( { addWord() } )
        retrieveData()
    }

    override fun onDestroy() {
        super.onDestroy()
        FirebaseAuth.getInstance().signOut()
    }

    private fun setEmptyAdapter() {
        val emptyList:MutableList<WordItem> = mutableListOf()
        recyclerView.adapter = VocabularyAdapter(emptyList, activity!!)
        val mLayoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = mLayoutManager
    }

    private fun retrieveData() {
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        userDocument = db.collection(USERS).document(userId)

        userDocument.get().addOnSuccessListener { task ->
            setVocabularyId(task)
            retrieveVocabularyData()
        }
    }

    private fun setVocabularyId(task: DocumentSnapshot) {
        //todo if only one vocabulary exists, open it
        val vocabularies: List<DocumentReference> = task.get("vocabularies") as List<DocumentReference>
        vocabulary = db.collection(VOCABULARIES).document(vocabularies[0].id)
    }

    private fun retrieveVocabularyData() {
        //todo if only one vocabulary exists, open it
        vocabulary.collection(WORDS).get()
                .addOnSuccessListener { setVocabularyAdapter(it.documents) }
    }

    private fun addWord() {
        val addWordIntent = Intent(activity, AddWordActivity::class.java)
        addWordIntent.putExtra("vocabularyId", vocabulary.id)
        startActivity(addWordIntent)
    }

    private fun setVocabularyAdapter(documents: MutableList<DocumentSnapshot>) {
        val wordItems: MutableList<WordItem> = mutableListOf()

        for (ref in documents) {
            val word = ref.get("word").toString()
            val translation = ref.get("translation").toString()
            wordItems.add(WordItem(word, translation, ref.id, vocabulary.id))
        }

        val adapter = VocabularyAdapter(wordItems, activity!!)
        recyclerView.adapter = adapter
    }
}