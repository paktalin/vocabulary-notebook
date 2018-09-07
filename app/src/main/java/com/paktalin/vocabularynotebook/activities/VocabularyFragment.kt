package com.paktalin.vocabularynotebook.activities

import android.annotation.SuppressLint
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_vocabulary, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        extractUserDocument()
        printUserData()
    }

    private fun extractUserDocument() {
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        Log.d(TAG, "retrieved userId: $userId")
        userDocument = db.collection("users").document(userId)
    }

    @SuppressLint("SetTextI18n")
    private fun printUserData() {
        userDocument.get().addOnSuccessListener { task ->
            val email = task.get("email").toString()
            tvUserData.text = email

            retrieveVocabulary(task)
        }
    }

    private fun retrieveVocabulary(task: DocumentSnapshot) {
        //todo if only one vocabulary exists, open it
        val vocabularies: List<DocumentReference> = task.get("vocabularies") as List<DocumentReference>
        val firstVocab = vocabularies[0].id
        db.collection("vocabularies").document(firstVocab).get().addOnSuccessListener { task ->
            val vocabTitle = task.get("title").toString()
            tvUserData.append("\n\nvocabularies:\n$vocabTitle")

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        FirebaseAuth.getInstance().signOut()
    }

    companion object {
        private val TAG = "VN/" + VocabularyFragment::class.simpleName
    }
}
