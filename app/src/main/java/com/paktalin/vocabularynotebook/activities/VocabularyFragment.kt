package com.paktalin.vocabularynotebook.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.paktalin.vocabularynotebook.R
import kotlinx.android.synthetic.main.fragment_vocabulary.*

class VocabularyFragment : Fragment() {

    private lateinit var userDocument: DocumentReference
    private val db = FirebaseFirestore.getInstance()
    private lateinit var vocabularyId: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_vocabulary, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        fabAddWord.setOnClickListener( { addWord() } )
        extractUserDocument()
        printUserData()
    }

    override fun onDestroy() {
        super.onDestroy()
        FirebaseAuth.getInstance().signOut()
    }

    private fun extractUserDocument() {
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        Log.d(TAG, "retrieved userId: $userId")
        userDocument = db.collection("users").document(userId)
    }

    private fun setVocabularyId(task: DocumentSnapshot) {
        //todo if only one vocabulary exists, open it
        val vocabularies: List<DocumentReference> = task.get("vocabularies") as List<DocumentReference>
        vocabularyId = vocabularies[0].id
    }

    @SuppressLint("SetTextI18n")
    private fun printUserData() {
        userDocument.get().addOnSuccessListener { task ->
            val email = task.get("email").toString()
            tvUserData.text = email

            setVocabularyId(task)
            retrieveVocabularyData()
        }
    }

    private fun retrieveVocabularyData() {
        //todo if only one vocabulary exists, open it
        db.collection("vocabularies").document(vocabularyId).get().addOnSuccessListener { task ->
            val vocabTitle = task.get("title").toString()
            tvUserData.append("\n\nvocabularies:\n$vocabTitle")

        }
    }

    private fun addWord() {
        val addWordIntent = Intent(activity, AddWordActivity::class.java)
        addWordIntent.putExtra("vocabularyId", vocabularyId)
        startActivity(addWordIntent)
    }

    companion object { private val TAG = "VN/" + VocabularyFragment::class.simpleName }
}