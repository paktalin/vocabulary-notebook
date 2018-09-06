package com.paktalin.vocabularynotebook.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.paktalin.vocabularynotebook.R
import kotlinx.android.synthetic.main.activity_user.*

class UserActivity : AppCompatActivity() {

    private lateinit var userDocument: DocumentReference
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

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

    @SuppressLint("SetTextI18n")
    private fun printUserData() {
        userDocument.get().addOnSuccessListener { task ->
            val email = task.get("email").toString()
            tvUserData.text = email

            retrieveVocabulary(task)
        }
    }

    private fun retrieveVocabulary(task: DocumentSnapshot) {
        val vocabularies: List<DocumentReference> = task.get("vocabularies") as List<DocumentReference>
        val firstVocab = vocabularies[0].id
        db.collection("vocabularies").document(firstVocab).get().addOnSuccessListener { task ->
            val vocabTitle = task.get("title").toString()
            tvUserData.append("\n\nvocabularies:\n$vocabTitle")

        }
    }

    companion object {
        private val TAG = "VN/" + UserActivity::class.simpleName
    }
}
